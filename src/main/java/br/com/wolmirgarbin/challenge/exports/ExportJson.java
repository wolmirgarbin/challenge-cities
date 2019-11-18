package br.com.wolmirgarbin.challenge.exports;

import br.com.wolmirgarbin.challenge.exception.ExportException;
import br.com.wolmirgarbin.challenge.model.Place;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Slf4j
public class ExportJson implements ExportPattern {

    @Override
    public void export(List<Place> placeList, Writer writer) throws ExportException {
        try {
            new ObjectMapper().writeValue(writer, placeList);
        } catch (IOException e) {
            LOGGER.error("Error exporting to json.", e);
            throw new ExportException("Não foi possível exportar os dados para json");
        }
    }
}
