package by.paprauko.telegramtouristbot.validator;

import by.paprauko.telegramtouristbot.database.repository.CityRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueCityNameValidator implements ConstraintValidator<UniqueCityName, String> {

    private CityRepository cityRepository;

    public UniqueCityNameValidator(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public void initialize(UniqueCityName constraint) {
    }

    public boolean isValid(String name, ConstraintValidatorContext context) {
        return name != null && !cityRepository.findByName(name.toLowerCase()).isPresent();
    }

}
