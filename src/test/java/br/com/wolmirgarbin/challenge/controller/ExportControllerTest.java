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

public class ExportControllerTest extends ChallengeApplicationTests {

    private static final String EXPECTED_CSV = "IDESTADO,NOMECIDADE,NOMEFORMATADO,NOMEMESOREGIAO,REGIAONOME,SIGLAESTADO\n" +
            "1,Blumenau,Blumenau/SC,MesoRegião,MicroRegião,SC\n";

    private static final String EXPECTED_JSON = "[" +
            "{\"idEstado\":1,\"siglaEstado\":\"SC\",\"regiaoNome\":\"MicroRegião\",\"nomeCidade\":\"Blumenau\",\"nomeMesoregiao\":\"MesoRegião\",\"nomeFormatado\":\"Blumenau/SC\"}" +
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
        when(placeConsume.findAllCitiesByState(eq("1|2"))).thenReturn(List.of(getCity(1, "Blumenau")));
    }

    @Test
    public void testExportCsv() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/export/{type}", "csv")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(EXPECTED_CSV));
    }

    @Test
    public void testExportJson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/export/{type}", "json")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(EXPECTED_JSON));
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
