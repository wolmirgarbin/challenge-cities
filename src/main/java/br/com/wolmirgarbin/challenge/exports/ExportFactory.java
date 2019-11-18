package br.com.wolmirgarbin.challenge.exports;

import br.com.wolmirgarbin.challenge.exception.UnsupportedTypeException;

public class ExportFactory {

    public static ExportPattern get(ExportType type) {
        switch (type) {
            case CSV: return new ExportCsv();
            case JSON: return new ExportJson();
            default: throw new UnsupportedTypeException("Tipo não suportado.");
        }
    }
}
