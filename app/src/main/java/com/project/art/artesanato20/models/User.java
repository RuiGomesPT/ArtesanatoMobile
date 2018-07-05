package com.project.art.artesanato20.models;

import java.util.ArrayList;

public class User {
    public String id;
    public String nome;
    public String email;
    public String photoID;
    public String tipo;

    public User() {

    }

    public User(String id, String nome, String email, String photoID, String tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.photoID = photoID;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoID() {
        return photoID;
    }

    public void setPhotoID(String photoID) {
        this.photoID = photoID;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
