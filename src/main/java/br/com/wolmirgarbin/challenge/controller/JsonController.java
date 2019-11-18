package br.com.wolmirgarbin.challenge.controller;

import br.com.wolmirgarbin.challenge.model.City;
import br.com.wolmirgarbin.challenge.model.Place;
import br.com.wolmirgarbin.challenge.service.PlaceService;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("json")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class JsonController {

    PlaceService placeService;

    @ApiOperation(value = "Retorna a lista com todas as cidades")
    @GetMapping
    public List<Place> findAllFormatted() {
        return placeService.findAllCities().stream().map(Place::adapter).collect(Collectors.toList());
    }

    @ApiOperation(value = "Retorna a lista com todas as cidades no formato da consulta")
    @GetMapping("/source")
    public List<City> findAllCities() {
        return placeService.findAllCities();
    }
}
