package br.com.wolmirgarbin.challenge.model;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class Base implements Serializable {

    abstract Integer getId();

}
