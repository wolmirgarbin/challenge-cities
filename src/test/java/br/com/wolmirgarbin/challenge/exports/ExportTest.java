package br.com.wolmirgarbin.challenge.exports;

import br.com.wolmirgarbin.challenge.model.Place;
import org.junit.Test;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ExportTest {

    private static final String EXPECTED_CSV = "IDESTADO,NOMECIDADE,NOMEFORMATADO,NOMEMESOREGIAO,REGIAONOME,SIGLAESTADO\n1,cidade,cidade/sc,mesoregião,região,sc\n";
    private static final String EXPECTED_JSON = "[{\"idEstado\":1,\"siglaEstado\":\"sc\",\"regiaoNome\":\"região\",\"nomeCidade\":\"cidade\",\"nomeMesoregiao\":\"mesoregião\",\"nomeFormatado\":\"cidade/sc\"}]";


    @Test
    public void testCsv() {
        StringWriter stringWriter = new StringWriter();
        ExportFactory.get(ExportType.CSV).export(getList(), stringWriter);

        assertEquals(EXPECTED_CSV, stringWriter.toString());
    }

    @Test
    public void testJson() {
        StringWriter stringWriter = new StringWriter();
        ExportFactory.get(ExportType.JSON).export(getList(), stringWriter);

        assertEquals(EXPECTED_JSON, stringWriter.toString());
    }

    private List<Place> getList() {
        List<Place> ls = new ArrayList<>();

        Place place = new Place();
        place.setIdEstado(1);
        place.setNomeCidade("cidade");
        place.setNomeMesoregiao("mesoregião");
        place.setRegiaoNome("região");
        place.setSiglaEstado("sc");
        ls.add(place);

        return ls;
    }
}
