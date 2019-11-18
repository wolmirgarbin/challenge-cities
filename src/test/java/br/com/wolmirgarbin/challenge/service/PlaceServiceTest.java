package br.com.wolmirgarbin.challenge.service;

import br.com.wolmirgarbin.challenge.ChallengeApplicationTests;
import br.com.wolmirgarbin.challenge.consume.PlaceConsume;
import br.com.wolmirgarbin.challenge.model.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class PlaceServiceTest extends ChallengeApplicationTests {

    @Autowired
    private PlaceService placeService;

    @MockBean
    private PlaceConsume placeConsume;

    @Before
    public void up() {
        when(placeConsume.findAllStates()).thenReturn(List.of(getState(1, "Santa Catarina", "SC"), getState(2,"Rio Grande do Sul", "RS")));
        when(placeConsume.findAllCitiesByState(eq("1"))).thenReturn(List.of(getCity("Blumenau"), getCity("Florianópolis")));
        when(placeConsume.findAllCitiesByState(eq("1|2"))).thenReturn(List.of(getCity("Blumenau"), getCity("Florianópolis"), getCity("São Domingos"), getCity("Coronel Martins")));
    }

    @Test
    public void testStates() {
        List<State> allStates = placeService.findAllStates();
        assertEquals(2, allStates.size());
    }

    @Test
    public void testCities() {
        List<City> allStates = placeService.findAllCities();
        assertEquals(4, allStates.size());
    }

    @Test
    public void testCitiesByState() {
        List<City> allStates = placeService.findAllCitiesByState(List.of(1));
        assertEquals(2, allStates.size());
    }

    @Test
    public void testFindByName() {
        Optional<City> optionalCity = placeService.findCityByName("blumenau");
        assertTrue(optionalCity.isPresent());
        assertEquals("Blumenau", optionalCity.get().getName());
        assertEquals("MicroRegião", optionalCity.get().getMicroRegion().getName());
        assertEquals("MesoRegião", optionalCity.get().getMicroRegion().getMesoRegion().getName());
        assertEquals("Santa Catarina", optionalCity.get().getMicroRegion().getMesoRegion().getState().getName());
    }

    @Test
    public void testFindCityByStateAcronymAndCityName() {
        Optional<City> optionalCity = placeService.findCityByStateAcronymAndCityName("sc", "blumenau");
        assertTrue(optionalCity.isPresent());
        assertEquals("Blumenau", optionalCity.get().getName());
        assertEquals("MicroRegião", optionalCity.get().getMicroRegion().getName());
        assertEquals("MesoRegião", optionalCity.get().getMicroRegion().getMesoRegion().getName());
        assertEquals("Santa Catarina", optionalCity.get().getMicroRegion().getMesoRegion().getState().getName());
    }

    private State getState(int id, String name, String acronym) {
        Region region = Region.builder().id(1).name("Região").acronym("RG").build();
        return State.builder().id(id).name(name).acronym(acronym).region(region).build();
    }

    private City getCity(String name) {
        MesoRegion mesoRegion = MesoRegion.builder().id(1).name("MesoRegião").state(getState(1, "Santa Catarina", "SC")).build();
        MicroRegion microRegiao = MicroRegion.builder().id(1).name("MicroRegião").mesoRegion(mesoRegion).build();
        return City.builder().id(1).name(name).microRegion(microRegiao).build();
    }
}
