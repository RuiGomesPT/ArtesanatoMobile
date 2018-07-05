package com.project.art.artesanato20.impl;

import com.project.art.artesanato20.models.Artesao;
import com.project.art.artesanato20.models.Artigo;

import java.util.ArrayList;

public interface IArtesao {

    ArrayList<Artesao> getArtList();

    void addArtToList (Artesao artesao);

    Artesao getArtesaoById(String id);

    void clearArtList();
}
