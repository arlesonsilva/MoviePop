package com.example.arlesonls.moviespop;

import java.io.Serializable;

public class Filmes implements Serializable {

    private String nome;
    private String sinopse;
    private String imagem;
    private int nuAcesso;

    public String getNome() {

        return nome;
    }

    public void setNome(String nome) {

        this.nome = nome;
    }

    public String getSinopse() {

        return sinopse;
    }

    public void setSinopse(String sinopse) {

        this.sinopse = sinopse;
    }

    public String getImagem() {

        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public int getNuAcesso() {

        return nuAcesso;
    }

    public void setNuAcesso(int nuAcesso) {
        this.nuAcesso = nuAcesso;
    }
}
