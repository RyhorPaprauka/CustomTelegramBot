package by.paprauko.telegramtouristbot.service;

import by.paprauko.telegramtouristbot.database.dto.CityDto;
import by.paprauko.telegramtouristbot.database.entity.City;
import by.paprauko.telegramtouristbot.database.mapper.CityMapperImpl;
import by.paprauko.telegramtouristbot.database.repository.CityRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CityService.class, CityMapperImpl.class})
public class CityServiceTest {

    @Autowired
    private CityService cityService;

    @MockBean
    private CityRepository cityRepository;

    @Before
    public void setUp() {
        City minsk = City.builder()
                .id(11L)
                .name("Minsk")
                .information("Belarus")
                .build();
        City moscow = City.builder()
                .id(22L)
                .name("Moscow")
                .information("Russia")
                .build();
        City tokyo = City.builder()
                .id(33L)
                .name("Tokyo")
                .information("Japan")
                .build();
        City london = City.builder()
                .id(44L)
                .name("London")
                .information("England")
                .build();

        List<City> allCities = Arrays.asList(minsk, moscow, tokyo);

        Mockito.when(cityRepository.findByNameIgnoreCase(minsk.getName())).thenReturn(Optional.of(minsk));
        Mockito.when(cityRepository.findByNameIgnoreCase("wrong_name")).thenReturn(Optional.empty());
        Mockito.when(cityRepository.findById(minsk.getId())).thenReturn(Optional.of(minsk));
        Mockito.when(cityRepository.findAll()).thenReturn(allCities);
        Mockito.when(cityRepository.findById(-99L)).thenReturn(Optional.empty());
        Mockito.when(cityRepository.save(any(City.class))).thenReturn(london);
        Mockito.when(cityRepository.existsById(london.getId())).thenReturn(true);
        Mockito.when(cityRepository.existsById(-99L)).thenReturn(false);
    }

    @Test
    public void whenValidName_thenCityShouldBeFound() {
        String name = "Minsk";
        City found = cityService.getCityByName(name).orElse(null);

        assertThat(found.getName()).isEqualTo(name);
    }

    @Test
    public void whenInValidName_thenCityShouldNotBeFound() {
        City fromDb = cityService.getCityByName("wrong_name").orElse(null);
        assertThat(fromDb).isNull();

        verifyFindByNameIsCalledOnce("wrong_name");
    }

    @Test
    public void whenValidId_thenCityShouldBeFound() {
        City fromDb = cityService.find(11L).orElse(null);
        assertThat(fromDb.getName()).isEqualTo("Minsk");

        verifyFindByIdIsCalledOnce();
    }

    @Test
    public void whenInValidId_thenCityShouldNotBeFound() {
        City fromDb = cityService.find(-99L).orElse(null);
        verifyFindByIdIsCalledOnce();
        assertThat(fromDb).isNull();
    }

    @Test
    public void whenGetAll_thenReturn3Records() {
        City minsk = City.builder()
                .name("Minsk")
                .information("Belarus")
                .build();

        City moscow = City.builder()
                .name("Moscow")
                .information("Russia")
                .build();
        City tokyo = City.builder()
                .name("Tokyo")
                .information("Japan")
                .build();

        List<City> allCities = cityService.findAll();
        verifyFindAllCitiesIsCalledOnce();
        assertThat(allCities).hasSize(3)
                .extracting(City::getName)
                .contains(minsk.getName(), moscow.getName(), tokyo.getName());
    }

    @Test
    public void whenSaved_thenReturnSavedCity() {
        CityDto dto = CityDto.builder()
                .name("London")
                .information("England")
                .build();
        City savedCity = cityService.saveCity(dto);
        assertThat(savedCity.getName()).isEqualTo(dto.getName());
    }

    @Test
    public void whenUpdate_thenReturnCity() {
        City london = City.builder()
                .id(44L)
                .name("London")
                .information("England")
                .build();
        City updatedCity = cityService.updateCity(london);

        assertThat(updatedCity.getName()).isEqualTo("London");
        verifySaveIsCalledOnce(london);
    }

    @Test
    public void whenUpdateDoNotExist_thenReturnNull() {
        City unExisted = City.builder()
                .id(-99L)
                .name("UnExisted")
                .information("UnExisted")
                .build();
        City updatedCity = cityService.updateCity(unExisted);

        assertThat(updatedCity).isNull();
    }

    private void verifySaveIsCalledOnce(City city) {
        Mockito.verify(cityRepository, VerificationModeFactory.times(1)).save(city);
        Mockito.reset(cityRepository);
    }

    private void verifyFindByNameIsCalledOnce(String name) {
        Mockito.verify(cityRepository, VerificationModeFactory.times(1)).findByNameIgnoreCase(name);
        Mockito.reset(cityRepository);
    }

    private void verifyFindByIdIsCalledOnce() {
        Mockito.verify(cityRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
        Mockito.reset(cityRepository);
    }

    private void verifyFindAllCitiesIsCalledOnce() {
        Mockito.verify(cityRepository, VerificationModeFactory.times(1)).findAll();
        Mockito.reset(cityRepository);
    }
}