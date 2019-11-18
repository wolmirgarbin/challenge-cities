package br.com.wolmirgarbin.challenge.service;

import br.com.wolmirgarbin.challenge.model.City;
import br.com.wolmirgarbin.challenge.model.State;

import java.util.List;
import java.util.Optional;

public interface PlaceService {

    List<State> findAllStates();

    List<City> findAllCitiesByState(List<Integer> states);

    List<City> findAllCities();

    Optional<City> findCityByName(String name);

    Optional<City> findCityByStateAcronymAndCityName(String acronym, String cityName);
}
