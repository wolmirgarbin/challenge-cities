package br.com.wolmirgarbin.challenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Region extends Base {

    private Integer id;

    @JsonProperty("sigla")
    String acronym;

    @JsonProperty("nome")
    String name;

}
