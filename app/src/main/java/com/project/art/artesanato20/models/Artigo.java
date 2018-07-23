package com.project.art.artesanato20.models;

import java.util.ArrayList;

public class Artigo {
    public String id;
    public String nome;
    public String id_creator;
    public String nome_creator;
    public String photoURL;
    public String tipo;

    public Artigo() {

    }

    public Artigo(String id, String nome, String id_creator, String nome_creator, String photoURL, String tipo) {
        this.id = id;
        this.nome = nome;
        this.id_creator = id_creator;
        this.nome_creator = nome_creator;
        this.photoURL = photoURL;
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId_creator() {
        return id_creator;
    }

    public void setId_creator(String id_creator) {
        this.id_creator = id_creator;
    }

    public String getNome_creator() {
        return nome_creator;
    }

    public void setNome_creator(String nome_creator) {
        this.nome_creator = nome_creator;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
