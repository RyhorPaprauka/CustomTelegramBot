package by.paprauko.telegramtouristbot.controller;

import by.paprauko.telegramtouristbot.database.entity.City;
import by.paprauko.telegramtouristbot.database.dto.CityDto;
import by.paprauko.telegramtouristbot.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static by.paprauko.telegramtouristbot.util.UrlPath.CITY;

@RestController
@RequestMapping(CITY)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CityController {

    private final CityService cityService;

    @GetMapping
    public ResponseEntity<List<City>> findAll() {
        return ResponseEntity.ok(cityService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> find(@PathVariable Long id) {
        return cityService.find(id)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PostMapping
    public ResponseEntity<City> saveCity(@RequestBody @Valid CityDto city) {
        City newCity = cityService.saveCity(city);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newCity.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(newCity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<City> updateCity(@RequestBody City city, @PathVariable Long id) {
        city.setId(id);
        City updatedCity = cityService.updateCity(city);

        if (updatedCity == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(updatedCity);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }
}
