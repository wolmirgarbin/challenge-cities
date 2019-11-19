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

import javax.cache.CacheManager;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class JsonControllerTest extends ChallengeApplicationTests {

    private static final String EXPECTED_FORMATTED = "[" +
            "{\"idEstado\":1,\"siglaEstado\":\"SC\",\"regiaoNome\":\"MicroRegião\",\"nomeCidade\":\"Blumenau\",\"nomeMesoregiao\":\"MesoRegião\",\"nomeFormatado\":\"Blumenau/SC\"}," +
            "{\"idEstado\":1,\"siglaEstado\":\"SC\",\"regiaoNome\":\"MicroRegião\",\"nomeCidade\":\"Florianópolis\",\"nomeMesoregiao\":\"MesoRegião\",\"nomeFormatado\":\"Florianópolis/SC\"}," +
            "{\"idEstado\":1,\"siglaEstado\":\"SC\",\"regiaoNome\":\"MicroRegião\",\"nomeCidade\":\"São Domingos\",\"nomeMesoregiao\":\"MesoRegião\",\"nomeFormatado\":\"São Domingos/SC\"}," +
            "{\"idEstado\":1,\"siglaEstado\":\"SC\",\"regiaoNome\":\"MicroRegião\",\"nomeCidade\":\"Coronel Martins\",\"nomeMesoregiao\":\"MesoRegião\",\"nomeFormatado\":\"Coronel Martins/SC\"}" +
            "]";

    private static final String EXPECTED_SOURCE = "[" +
            "{\"id\":1,\"nome\":\"Blumenau\",\"microrregiao\":{\"id\":1,\"nome\":\"MicroRegião\",\"mesorregiao\":{\"id\":1,\"nome\":\"MesoRegião\",\"UF\":{\"id\":1,\"sigla\":\"SC\",\"nome\":\"Santa Catarina\",\"regiao\":{\"id\":1,\"sigla\":\"RG\",\"nome\":\"Região\"}}}}}," +
            "{\"id\":1,\"nome\":\"Florianópolis\",\"microrregiao\":{\"id\":1,\"nome\":\"MicroRegião\",\"mesorregiao\":{\"id\":1,\"nome\":\"MesoRegião\",\"UF\":{\"id\":1,\"sigla\":\"SC\",\"nome\":\"Santa Catarina\",\"regiao\":{\"id\":1,\"sigla\":\"RG\",\"nome\":\"Região\"}}}}}," +
            "{\"id\":1,\"nome\":\"São Domingos\",\"microrregiao\":{\"id\":1,\"nome\":\"MicroRegião\",\"mesorregiao\":{\"id\":1,\"nome\":\"MesoRegião\",\"UF\":{\"id\":1,\"sigla\":\"SC\",\"nome\":\"Santa Catarina\",\"regiao\":{\"id\":1,\"sigla\":\"RG\",\"nome\":\"Região\"}}}}}," +
            "{\"id\":1,\"nome\":\"Coronel Martins\",\"microrregiao\":{\"id\":1,\"nome\":\"MicroRegião\",\"mesorregiao\":{\"id\":1,\"nome\":\"MesoRegião\",\"UF\":{\"id\":1,\"sigla\":\"SC\",\"nome\":\"Santa Catarina\",\"regiao\":{\"id\":1,\"sigla\":\"RG\",\"nome\":\"Região\"}}}}}" +
            "]";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CacheManager cacheManager;

    @MockBean
    private PlaceConsume placeConsume;

    @Before
    public void up() {
        cacheManager.getCacheNames().forEach(cacheName -> cacheManager.getCache(cacheName).clear());
        when(placeConsume.findAllStates()).thenReturn(List.of(getState(1, "Santa Catarina", "SC"), getState(2,"Rio Grande do Sul", "RS")));
        when(placeConsume.findAllCitiesByState(eq("1"))).thenReturn(List.of(getCity("Blumenau"), getCity("Florianópolis")));
        when(placeConsume.findAllCitiesByState(eq("1|2"))).thenReturn(List.of(getCity("Blumenau"), getCity("Florianópolis"), getCity("São Domingos"), getCity("Coronel Martins")));
    }

    @Test
    public void testJson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/json")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(EXPECTED_FORMATTED));
    }

    @Test
    public void testJsonSource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/json/source")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(EXPECTED_SOURCE));
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
