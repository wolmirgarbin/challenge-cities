package br.com.wolmirgarbin.challenge.exports;

import br.com.wolmirgarbin.challenge.exception.ExportException;
import br.com.wolmirgarbin.challenge.model.Place;

import java.io.Writer;
import java.util.List;

public interface ExportPattern {

    void export(List<Place> placeList, Writer Writer) throws ExportException;

}
