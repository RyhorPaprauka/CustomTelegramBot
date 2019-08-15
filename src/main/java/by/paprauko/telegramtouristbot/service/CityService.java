package by.paprauko.telegramtouristbot.service;

import by.paprauko.telegramtouristbot.database.entity.City;
import by.paprauko.telegramtouristbot.database.dto.CityDto;
import by.paprauko.telegramtouristbot.database.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CityService {

    private final CityRepository cityRepository;

    public List<City> findAll() {
        return cityRepository.findAll();
    }

    public Optional<City> find(Long id) {
        return cityRepository.findById(id);
    }

    public City saveCity(CityDto city) {
        City newCity = City.builder()
                .name(city.getName().toLowerCase())
                .information(city.getInformation())
                .build();

        return cityRepository.save(newCity);
    }

    public City updateCity(City city) {
        if (cityRepository.existsById(city.getId())) {
            city.setName(city.getName().toLowerCase());
            return cityRepository.save(city);
        } else {
            return null;
        }
    }

    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }

    public Optional<City> getCityByName(String cityName) {
        return cityRepository.findByName(cityName);
    }
}
