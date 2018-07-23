package com.project.art.artesanato20.impl;

import com.project.art.artesanato20.models.Artigo;

import java.util.ArrayList;

public interface IArtigo {

    ArrayList<Artigo> getItemList();

    void addItemToList (Artigo artigo);

    Artigo getArtigoById(String id);

    ArrayList<Artigo> getArtigosByTags(ArrayList<String> tags);

    void clearList();
}
