package by.paprauko.telegramtouristbot.database.dto;

import by.paprauko.telegramtouristbot.validator.UniqueCityName;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CityDto {

    @UniqueCityName
    private String name;
    @NotBlank
    private String information;
}
