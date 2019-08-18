package by.paprauko.telegramtouristbot.service;

import by.paprauko.telegramtouristbot.database.entity.City;
import by.paprauko.telegramtouristbot.database.dto.CityDto;
import by.paprauko.telegramtouristbot.database.mapper.CityMapper;
import by.paprauko.telegramtouristbot.database.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "cities")
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CityService {

    private final CityRepository cityRepository;
    private final CityMapper mapper;

    @Cacheable
    public List<City> findAll() {
        return cityRepository.findAll();
    }

    @Cacheable
    public Optional<City> find(Long id) {
        return cityRepository.findById(id);
    }

    @CacheEvict(allEntries = true)
    public City saveCity(CityDto cityDto) {
        City newCity = mapper.toEntity(cityDto);
        return cityRepository.save(newCity);
    }

    @CachePut
    public City updateCity(City city) {
        if (cityRepository.existsById(city.getId())) {
            city.setName(city.getName());
            return cityRepository.save(city);
        } else {
            return null;
        }
    }

    @CacheEvict
    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }

    @Cacheable
    public Optional<City> getCityByName(String cityName) {
        return cityRepository.findByNameIgnoreCase(cityName);
    }
}
