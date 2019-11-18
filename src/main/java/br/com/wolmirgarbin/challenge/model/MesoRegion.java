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
public class MesoRegion extends Base {

    private Integer id;

    @JsonProperty("nome")
    String name;

    @JsonProperty("UF")
    State state;

}
