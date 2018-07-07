package com.project.art.artesanato20.models;

public class Artesao {
    private String id;
    private String name;
    private String photoURL;
    private String email;
    private String tipo;
    private String nomeAtv;
    private String codAtv;
    private double xCoor;
    private double yCoor;

    public Artesao() {
    }

    public Artesao(String id, String name, String photoURL, String email, String tipo, String nomeAtv, String codAtv, double xCoor, double yCoor) {
        this.id = id;
        this.name = name;
        this.photoURL = photoURL;
        this.email = email;
        this.tipo = tipo;
        this.nomeAtv = nomeAtv;
        this.codAtv = codAtv;
        this.xCoor = xCoor;
        this.yCoor = yCoor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getxCoor() {
        return xCoor;
    }

    public void setxCoor(double xCoor) {
        this.xCoor = xCoor;
    }

    public double getyCoor() {
        return yCoor;
    }

    public void setyCoor(double yCoor) {
        this.yCoor = yCoor;
    }

    public String getNomeAtv() {
        return nomeAtv;
    }

    public void setNomeAtv(String nomeAtv) {
        this.nomeAtv = nomeAtv;
    }

    public String getCodAtv() {
        return codAtv;
    }

    public void setCodAtv(String codAtv) {
        this.codAtv = codAtv;
    }
}
