package com.oytu.darwinismo;

import java.io.Serializable;

public class Produto implements Serializable{
    private String nome;
    private Double espaco;
    private Double valor;

    public Produto(String nome, Double espaco, Double valor) {
        this.nome = nome;
        this.espaco = espaco;
        this.valor = valor;
    }



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getEspaco() {
        return espaco;
    }

    public void setEspaco(Double espaco) {
        this.espaco = espaco;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }    
} // fim classe Produto