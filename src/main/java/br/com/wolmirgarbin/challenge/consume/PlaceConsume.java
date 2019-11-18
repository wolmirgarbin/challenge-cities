package br.com.wolmirgarbin.challenge.consume;

import br.com.wolmirgarbin.challenge.model.City;
import br.com.wolmirgarbin.challenge.model.State;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "servicodados-ibge", path = "api/v1/localidades", fallback = PlaceConsumeFallback.class)
public interface PlaceConsume {

    @GetMapping("/estados")
    @Cacheable("placeConsumeFindAllStates")
    List<State> findAllStates();

    @GetMapping("/estados/{states}/municipios")
    @Cacheable("placeConsumeFindAllCitiesByState")
    List<City> findAllCitiesByState(@PathVariable("states") String states);

}
