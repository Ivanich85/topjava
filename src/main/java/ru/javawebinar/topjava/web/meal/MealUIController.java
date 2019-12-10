package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.BindingUtils;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/ajax/profile/meals")
public class MealUIController extends AbstractMealController {

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal get(@PathVariable("id") int id){
        return super.get(id);
    }


    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity createOrUpdate(@Valid Meal updateMeal, BindingResult result) {
        ResponseEntity entityResult = BindingUtils.getBindingResult(result, "user");
        if (entityResult.equals(ResponseEntity.ok().build())) {
            if (updateMeal.isNew()) {
                super.create(updateMeal);
            } else {
                super.update(updateMeal, updateMeal.getId());
            }
        }
        return entityResult;
    }

    @Override
    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getBetween(
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "startTime", required = false) LocalTime startTime,
            @RequestParam(value = "endDate", required = false) LocalDate endDate,
            @RequestParam(value = "endTime", required = false) LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}