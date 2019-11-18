package br.com.wolmirgarbin.challenge.service;

import br.com.wolmirgarbin.challenge.consume.PlaceConsume;
import br.com.wolmirgarbin.challenge.model.City;
import br.com.wolmirgarbin.challenge.model.State;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlaceServiceImpl implements PlaceService {

    PlaceConsume placeConsume;

    @Override
    @Cacheable("placeServiceFindAllStates")
    public List<State> findAllStates() {
        return placeConsume.findAllStates();
    }

    @Override
    @Cacheable("placeServiceFindAllCitiesByState")
    public List<City> findAllCitiesByState(List<Integer> states) {
        String statesPath = states.stream().map(Long::toString).collect(Collectors.joining("|"));
        return placeConsume.findAllCitiesByState(statesPath);
    }

    @Override
    @Cacheable("placeServiceFindAllCities")
    public List<City> findAllCities() {
        return findAllCitiesByState(findAllStates().stream().map(State::getId).collect(Collectors.toList()));
    }

    @Override
    @Cacheable("placeServiceFindCityByName")
    public Optional<City> findCityByName(String name) {
        return findAllCities().stream()
                .filter(city -> city.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    @Cacheable("placeServiceFindCityByStateAcronymAndCityName")
    public Optional<City> findCityByStateAcronymAndCityName(String stateAcronym, String cityName) {
        return findAllStates().stream()
                .filter(state -> state.getAcronym().equalsIgnoreCase(stateAcronym))
                .map(state -> findAllCitiesByState(List.of(state.getId())))
                .flatMap(Collection::stream)
                .filter(city -> city.getName().equalsIgnoreCase(cityName))
                .findFirst();
    }
}
