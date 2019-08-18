package by.paprauko.telegramtouristbot.database.mapper;

import by.paprauko.telegramtouristbot.database.dto.CityDto;
import by.paprauko.telegramtouristbot.database.entity.City;
import org.mapstruct.Mapper;

@Mapper
public interface CityMapper {

    City toEntity(CityDto cityDto);
}
