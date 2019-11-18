package br.com.wolmirgarbin.challenge.exports;

import br.com.wolmirgarbin.challenge.exception.ExportException;
import br.com.wolmirgarbin.challenge.model.Place;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.extern.slf4j.Slf4j;

import java.io.Writer;
import java.util.List;

@Slf4j
public class ExportCsv implements ExportPattern {

    @Override
    public void export(List<Place> placeList, Writer writer) throws ExportException {
        StatefulBeanToCsv<Place> statefulBeanToCsv = new StatefulBeanToCsvBuilder<Place>(writer)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false)
                .build();

        try {
            statefulBeanToCsv.write(placeList);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            LOGGER.error("Error exporting to csv", e);
            throw new ExportException("Não foi possível exportar os dados para csv");
        }
    }
}
