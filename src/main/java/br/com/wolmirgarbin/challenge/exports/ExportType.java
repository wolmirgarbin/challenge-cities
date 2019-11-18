package br.com.wolmirgarbin.challenge.exports;

import lombok.Getter;

public enum ExportType {
    CSV("text/csv"), JSON("application/json");

    @Getter
    private String contentType;

    ExportType(String contentType) {
        this.contentType = contentType;
    }
}
