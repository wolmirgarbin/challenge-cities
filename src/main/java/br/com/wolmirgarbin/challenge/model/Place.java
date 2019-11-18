package br.com.wolmirgarbin.challenge.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Place {

    Integer idEstado;
    String siglaEstado;
    String regiaoNome;
    String nomeCidade;
    String nomeMesoregiao;
    String nomeFormatado;

    public String getNomeFormatado() {
        return String.format("%s/%s", nomeCidade, siglaEstado);
    }

    public static Place adapter(City city) {
        Place place = new Place();
        place.setIdEstado(city.getMicroRegion().getMesoRegion().getState().getId());
        place.setNomeCidade(city.getName());
        place.setNomeMesoregiao(city.getMicroRegion().getMesoRegion().getName());
        place.setRegiaoNome(city.getMicroRegion().getName());
        place.setSiglaEstado(city.getMicroRegion().getMesoRegion().getState().getAcronym());
        return place;
    }
}
