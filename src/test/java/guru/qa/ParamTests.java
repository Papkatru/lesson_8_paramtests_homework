package guru.qa;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.byTextCaseInsensitive;
import static com.codeborne.selenide.Selenide.*;

public class ParamTests {

    @BeforeAll
    public static void testBase() {
        Configuration.browserSize = "1920x1080";
    }

    @ValueSource(strings = {"English", "Русский"})
    @ParameterizedTest(name = "Провера локализации для языка {0}")
    void vkLocalizationTest(String localization) {
        open("https://vk.com/");
        $(".footer_lang").$(byText(localization)).click();
        $$(".bnav_a").shouldBe(CollectionCondition.sizeGreaterThan(0));
    }

    @CsvSource(value = {"English, About VK", "Русский, О Вконтакте"})
    @ParameterizedTest(name = "На языке \"{0}\" отображается \"{1}\"")
    void vkLocalizationComplexTest(String localization, String expectedResult) {
        open("https://vk.com/");
        $(".footer_lang").$(byText(localization)).click();
        $(".footer_links").shouldHave(text(expectedResult));
    }

    static Stream<Arguments> vkLocalizationFooterTest() {
        return Stream.of(
                Arguments.of("English", List.of("About VK", "Terms", "Developers")),
                Arguments.of("Русский", List.of("О ВКонтакте", "Правила", "Для бизнеса", "Разработчикам"))
        );
    }

    @MethodSource
    @ParameterizedTest(name = "На языке \"{0}\" отображаются кнопки \"{1}\"")
    void vkLocalizationFooterTest(String localization, List<String> expectedResult) {
        open("https://vk.com/");
        $(".footer_lang").$(byText(localization)).click();
        $$(".footer_links a").filter(visible).shouldHave(texts(expectedResult));
    }

    @EnumSource(Lang.class)
    @ParameterizedTest(name = "На языке \"{0}\" лого отображается")
    void vkLocalizationFooterEnumTest(Lang lang) {
        open("https://vk.com/");
        $(".footer_lang").$(byTextCaseInsensitive(lang.toString())).click();
        $(".TopHomeLink").shouldBe(visible);
    }
}
