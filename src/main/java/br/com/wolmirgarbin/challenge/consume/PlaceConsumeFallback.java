package br.com.wolmirgarbin.challenge.consume;

import br.com.wolmirgarbin.challenge.model.City;
import br.com.wolmirgarbin.challenge.model.State;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class PlaceConsumeFallback implements PlaceConsume {

    @Override
    public List<State> findAllStates() {
        LOGGER.warn("Using fallback return from service.");
        return Collections.emptyList();
    }

    @Override
    public List<City> findAllCitiesByState(String states) {
        LOGGER.warn("Using fallback return from service.");
        return Collections.emptyList();
    }
}
