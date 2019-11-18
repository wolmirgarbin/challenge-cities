package br.com.wolmirgarbin.challenge.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlaceTest {

    @Test
    public void testFormattedName() {
        Place place = new Place();
        place.setSiglaEstado("SC");
        place.setNomeCidade("Blumenau");

        assertEquals("Blumenau/SC", place.getNomeFormatado());
    }
}
