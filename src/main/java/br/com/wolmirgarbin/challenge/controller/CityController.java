package br.com.wolmirgarbin.challenge.controller;

import br.com.wolmirgarbin.challenge.exception.CityNotFoundException;
import br.com.wolmirgarbin.challenge.model.City;
import br.com.wolmirgarbin.challenge.service.PlaceService;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("city")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CityController {

    PlaceService placeService;

    @ApiOperation(value = "Obtem o código da cidade com base no nome da cidade")
    @GetMapping("{cityName}")
    public Integer findCityByName(@PathVariable("cityName") String cityName) {
        return placeService.findCityByName(cityName)
                .map(City::getId)
                .orElseThrow(() -> new CityNotFoundException("Cidade não encontrada."));
    }

    @ApiOperation(value = "Obtem o código da cidade com base no estado e nome da cidade")
    @GetMapping("{stateAcronym}/{cityName}")
    public Integer findCityByStateAndName(@PathVariable("stateAcronym") String stateAcronym, @PathVariable("cityName") String cityName) {
        return placeService.findCityByStateAcronymAndCityName(stateAcronym, cityName)
                .map(City::getId)
                .orElseThrow(() -> new CityNotFoundException("Cidade não encontrada."));
    }
}
