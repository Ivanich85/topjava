package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    MealService service;

    @Test
    public void get() {
        Meal meal = service.get(100002, UserTestData.USER_ID);
        MealTestData.assertMatch(meal, MealTestData.USER_BREAKFAST );
    }

    @Test(expected = NotFoundException.class)
    public void getAnotherUserMeal() {
        service.get(100008, UserTestData.USER_ID);
    }

    @Test
    public void create() {
        Meal created = new Meal(LocalDateTime.of(2019, 10, 19, 13, 20),
                "Польз. обед", 800);
        service.create(created, UserTestData.USER_ID);
        MealTestData.assertMatch(service.get(100010, UserTestData.USER_ID), MealTestData.USER_LUNCH);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateDateTimeCreate() throws Exception {
        service.create(new Meal(LocalDateTime.of(2019, 10, 19, 20, 30),
                "Польз. ужин", 500), UserTestData.USER_ID);
    }

    @Test
    public void delete() {
        service.delete(100002, UserTestData.USER_ID);
        service.delete(100005, UserTestData.USER_ID);
        MealTestData.assertMatch(service.getAll(UserTestData.USER_ID),
                Arrays.asList(MealTestData.USER_DINNER, MealTestData.USER_LUNCH, MealTestData.USER_LUNCH_1));
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(100000, 100000);
    }

    @Test(expected = NotFoundException.class)
    public void deleteAnotherUserMeal() {
        service.delete(100008, UserTestData.USER_ID);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> filtered = service.getBetweenDates(LocalDate.of(2019, 10, 17),
                LocalDate.of(2019, 10, 17), UserTestData.USER_ID);
        MealTestData.assertMatch(filtered, Arrays.asList(MealTestData.USER_LUNCH_1, MealTestData.USER_BREAKFAST_1));
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(UserTestData.ADMIN_ID);
        MealTestData.assertMatch(all,
                Arrays.asList(MealTestData.ADMIN_DINNER, MealTestData.ADMIN_LUNCH, MealTestData.ADMIN_BREAKFAST));
    }

    @Test
    public void update() {
        Meal updated = new Meal(MealTestData.USER_DINNER);
        updated.setDescription("Второй ужин");
        updated.setCalories(1200);
        updated.setId(100004);
        service.update(updated, UserTestData.USER_ID);
        MealTestData.assertMatch(service.get(100004, UserTestData.USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateAnotherUserMeal() {
        Meal updated = new Meal(MealTestData.USER_DINNER);
        updated.setDescription("Второй ужин");
        updated.setCalories(1200);
        updated.setId(100004);
        service.update(updated, UserTestData.ADMIN_ID);
    }

}