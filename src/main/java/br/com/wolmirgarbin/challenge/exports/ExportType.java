package br.com.wolmirgarbin.challenge.exports;

import br.com.wolmirgarbin.challenge.exception.UnsupportedTypeException;
import lombok.Getter;

public enum ExportType {
    CSV("text/csv"), JSON("application/json");

    @Getter
    private final String contentType;

    ExportType(String contentType) {
        this.contentType = contentType;
    }

    public static ExportType getValueOrThrow(String type) {
        try {
            return ExportType.valueOf(type.toUpperCase());
        } catch (Exception e) {
            throw new UnsupportedTypeException("Tipo não suportado.");
        }
    }
}
