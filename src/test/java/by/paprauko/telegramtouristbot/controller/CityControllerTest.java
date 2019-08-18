package by.paprauko.telegramtouristbot.controller;

import by.paprauko.telegramtouristbot.database.entity.City;
import by.paprauko.telegramtouristbot.service.CityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CityController.class)
public class CityControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CityService cityService;

    @Test
    public void whenGetCities_thenReturnJsonArray() throws Exception {
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

        List<City> allCities = Arrays.asList(minsk, moscow, tokyo);

        given(cityService.findAll()).willReturn(allCities);

        mvc.perform(get("/city").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is(minsk.getName())))
                .andExpect(jsonPath("$[1].name", is(moscow.getName())))
                .andExpect(jsonPath("$[2].name", is(tokyo.getName())));
        verify(cityService, VerificationModeFactory.times(1)).findAll();
        reset(cityService);
    }

    @Test
    public void whenGetCity_thenReturnOne() throws Exception {
        City minsk = City.builder()
                .id(11L)
                .name("Minsk")
                .information("Belarus")
                .build();
        given(cityService.find(11L)).willReturn(Optional.of(minsk));

        mvc.perform(get("/city/11").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.information", is(minsk.getInformation())));
        verify(cityService, VerificationModeFactory.times(1)).find(11L);
        reset(cityService);
    }

    @Test
    public void whenGetUnExistedCity_theReturnNotFound() throws Exception {
        given(cityService.find(99L)).willReturn(Optional.empty());

        mvc.perform(get("/city/99").contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(cityService, VerificationModeFactory.times(1)).find(99L);
        reset(cityService);
    }

    @Test
    public void whenPutExistedCity_thenReturnOne() throws Exception {
        City moscow = City.builder()
                .id(22L)
                .name("Moscow")
                .information("Russia")
                .build();

        given(cityService.updateCity(moscow)).willReturn(moscow);
        mvc.perform(put("/city/22").contentType(APPLICATION_JSON).content(JsonUtil.toJson(moscow)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Moscow")));
        verify(cityService, VerificationModeFactory.times(1)).updateCity(moscow);
        reset(cityService);
    }

    @Test
    public void whenPutUnExistedCity_thenReturnNotFound() throws Exception {
        City unExisted = City.builder()
                .id(99L)
                .name("unExisted")
                .information("unExisted")
                .build();

        given(cityService.updateCity(unExisted)).willReturn(null);
        mvc.perform(put("/city/99").contentType(APPLICATION_JSON).content(JsonUtil.toJson(unExisted)))
                .andExpect(status().isNotFound());
        verify(cityService, VerificationModeFactory.times(1)).updateCity(unExisted);
        reset(cityService);
    }
}