package guru.qa.tests;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static java.util.Arrays.asList;

public class SearchTests extends BaseTest {

    @DisplayName("При поиске на кинопоиске по запросу Во все тяжкие отображается фильм Во все тяжкие")
    @Test
    void checkTitleFilmInSearchResults1() {
        Selenide.open("/");
        $("[name=kp_query]").setValue("Во все тяжкие");
        $("[name=kp_query]").pressEnter();
        $(".most_wanted").shouldHave(text("Во все тяжкие"));
    }

    @DisplayName("При поиске на кинопоиске по запросу Топи фильм текст Топи")
    @Disabled("Ticket-321")
    @Test
    void checkTitleFilmInSearchResults2() {
        Selenide.open("/");
        $("[name=kp_query]").setValue("Топи");
        $("[name=kp_query]").pressEnter();
        $(".most_wanted").shouldHave(text("Топи"));
    }

    @ValueSource(strings = {"Во все тяжкие", "Топи"})
    @ParameterizedTest(name = "При поиске на кинопоиске по запросу {0} в результатах отображается текст {0}")
    void checkTitleFilmInSearchResults3(String testData) {
        Selenide.open("/");
        $("[name=kp_query]").setValue(testData);
        $("[name=kp_query]").pressEnter();
        $(".most_wanted").shouldHave(text(testData));
    }

    @CsvSource(value = {"Во все тяжкие, США", "Топи, Россия"})
    @ParameterizedTest(name = "При поиске на кинопоиске по запросу {0} в результатах отображается страна {1}")
    void checkCountryFilmInSearchResultsByName1(String searchData, String expectedResult) {
        Selenide.open("/");
        $("[name=kp_query]").setValue(searchData);
        $("[name=kp_query]").pressEnter();
        $(".most_wanted").shouldHave(text(expectedResult));
    }

    @CsvFileSource(resources = "/test_data/1.csv")
    @ParameterizedTest(name = "При поиске на кинопоиске по запросу {0} в результатах отображается страна {1}")
    void checkCountryFilmInSearchResultsByName2(String searchData, String expectedResult) {
        Selenide.open("/");
        $("[name=kp_query]").setValue(searchData);
        $("[name=kp_query]").pressEnter();
        $(".most_wanted").shouldHave(text(expectedResult));
    }

    static Stream<Arguments> checkInfoFilmInSearchResultsDataProvider() {
        return Stream.of(
                Arguments.of("Во все тяжкие", asList("Во все тяжкие", "США, реж. Мишель МакЛарен")),
                Arguments.of("Топи", asList("Топи", "Россия, реж. Владимир Мирзоев"))
        );
    }

    @MethodSource(value = "checkInfoFilmInSearchResultsDataProvider")
    @ParameterizedTest(name = "При поиске на кинопоиске по запросу {0} в результатах отображается название, страна и режисер {1}")
    void yaTestVeryComplex(String searchData, List<String> expectedResult) {
        Selenide.open("/");
        $("[name=kp_query]").setValue(searchData);
        $("[name=kp_query]").pressEnter();
        for (String expRes : expectedResult) {
            $(".search_results .most_wanted").shouldHave(
                    text(expRes));
        }
    }

    @EnumSource(Films.class)
    @ParameterizedTest(name = "При поиске на кинопоиске по запросу {0} в результатах отображается текст {0}")
    void enumTest(Films film) {
        Selenide.open("/");
        $("[name=kp_query]").setValue(film.desc);
        $("[name=kp_query]").pressEnter();
        $(".most_wanted").shouldHave(text(film.desc));
    }
}