package br.com.wolmirgarbin.challenge.controller;

import br.com.wolmirgarbin.challenge.ChallengeApplicationTests;
import br.com.wolmirgarbin.challenge.consume.PlaceConsume;
import br.com.wolmirgarbin.challenge.model.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.ws.rs.core.MediaType;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ExportControllerTest extends ChallengeApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlaceConsume placeConsume;

    @Before
    public void up() {
        when(placeConsume.findAllStates()).thenReturn(List.of(getState(1, "Santa Catarina", "SC"), getState(2,"Rio Grande do Sul", "RS")));
        when(placeConsume.findAllCitiesByState(eq("1"))).thenReturn(List.of(getCity(1,"Blumenau"), getCity(2, "Florianópolis"), getCity(3, "São Domingos"), getCity(4, "Coronel Martins")));
        when(placeConsume.findAllCitiesByState(eq("1|2"))).thenReturn(List.of(getCity(1, "Blumenau"), getCity(2, "Florianópolis"), getCity(3, "São Domingos"), getCity(4, "Coronel Martins")));
    }

    @Test
    public void testFindByName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/city/{cityName}", "coronel martins")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(4));
    }

    @Test
    public void testFindByStateAndName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/city/{stateAcronym}/{cityName}", "sc", "coronel martins")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(4));
    }

    private State getState(int id, String name, String acronym) {
        Region region = Region.builder().id(1).name("Região").acronym("RG").build();
        return State.builder().id(id).name(name).acronym(acronym).region(region).build();
    }

    private City getCity(Integer id, String name) {
        MesoRegion mesoRegion = MesoRegion.builder().id(1).name("MesoRegião").state(getState(1, "Santa Catarina", "SC")).build();
        MicroRegion microRegiao = MicroRegion.builder().id(1).name("MicroRegião").mesoRegion(mesoRegion).build();
        return City.builder().id(id).name(name).microRegion(microRegiao).build();
    }
}
