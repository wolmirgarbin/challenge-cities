package br.com.wolmirgarbin.challenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class State extends Base {

    private Integer id;

    @JsonProperty("sigla")
    String acronym;

    @JsonProperty("nome")
    String name;

    @JsonProperty("regiao")
    Region region;

}
