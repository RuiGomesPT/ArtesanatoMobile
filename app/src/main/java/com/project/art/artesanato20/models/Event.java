package com.project.art.artesanato20.models;

public class Event {
    private String id;
    private String nome;
    private String info;
    private String data;
    private String hora;
    private String imgURL;

    public Event() {

    }

    public Event(String id, String nome, String info, String data, String hora, String imgURL) {
        this.id = id;
        this.nome = nome;
        this.info = info;
        this.data = data;
        this.hora = hora;
        this.imgURL = imgURL;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
