package by.paprauko.telegramtouristbot.database.repository;

import by.paprauko.telegramtouristbot.database.entity.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CityRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CityRepository cityRepository;

    @Test
    public void whenFindByName_thenReturnCity() {
        City minsk = City.builder()
                .name("Minsk")
                .information("Belarus")
                .build();
        entityManager.persistAndFlush(minsk);

        City found = cityRepository.findByNameIgnoreCase(minsk.getName())
                .orElse(null);
        assertThat(found.getName()).isEqualTo(minsk.getName());
    }

    @Test
    public void whenInvalidName_thenReturnNull() {
        assertThat(cityRepository.findByNameIgnoreCase("doesNotExist")
                .orElse(null))
                .isNull();
    }

    @Test
    public void whenFindById_thenReturnCity() {
        City test = City.builder()
                .name("test")
                .information("test")
                .build();
        entityManager.persistAndFlush(test);

        City fromDb = cityRepository.findById(test.getId()).orElse(null);
        assertThat(fromDb.getName()).isEqualTo(test.getName());
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        City fromDb = cityRepository.findById(-11l).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfCity_whenFindAll_thenReturnAllCities() {
        City first = City.builder()
                .name("first")
                .information("first")
                .build();
        City second = City.builder()
                .name("second")
                .information("second")
                .build();
        City third = City.builder()
                .name("third")
                .information("third")
                .build();

        entityManager.persist(first);
        entityManager.persist(second);
        entityManager.persist(third);
        entityManager.flush();

        List<City> allEmployees = cityRepository.findAll();

        assertThat(allEmployees).hasSize(3)
                .extracting(City::getName)
                .containsOnly(first.getName(), second.getName(), third.getName());
    }
}