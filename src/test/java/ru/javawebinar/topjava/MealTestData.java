package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public final static Meal USER_DINNER =
            new Meal(LocalDateTime.of(2019, 10, 19, 20, 30),
                    "Польз. ужин", 500);
    public final static Meal USER_LUNCH =
            new Meal(LocalDateTime.of(2019, 10, 19, 13, 20),
                    "Польз. обед", 800);
    public final static Meal USER_BREAKFAST =
            new Meal(LocalDateTime.of(2019, 10, 19, 9, 0),
                    "Польз. завтрак", 1000);
    public final static Meal USER_LUNCH_1 =
            new Meal(LocalDateTime.of(2019, 10, 17, 13, 20),
                    "Польз. обед 1", 800);
    public final static Meal USER_BREAKFAST_1 =
            new Meal(LocalDateTime.of(2019, 10, 17, 9, 0),
                    "Польз. завтрак 1", 1000);
    public final static Meal ADMIN_DINNER =
            new Meal(LocalDateTime.of(2019, 10, 15, 19, 0),
                    "Админ ужин", 520);
    public final static Meal ADMIN_LUNCH =
            new Meal(LocalDateTime.of(2019, 10, 15, 14, 0),
                    "Админ обед", 420);
    public final static Meal ADMIN_BREAKFAST =
            new Meal(LocalDateTime.of(2019, 10, 15, 13, 0),
                    "Админ завтрак", 700);


    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "id");
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("id").isEqualTo(expected);
    }
}
