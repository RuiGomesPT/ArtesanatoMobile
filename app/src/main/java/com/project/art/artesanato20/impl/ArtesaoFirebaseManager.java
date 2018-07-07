package com.project.art.artesanato20.impl;

import com.project.art.artesanato20.models.Artesao;
import java.util.ArrayList;

public class ArtesaoFirebaseManager implements IArtesao {
        public ArrayList<Artesao> artList = new ArrayList<>();

    public ArrayList<Artesao> getArtList() {
        return artList;
    }

    static ArtesaoFirebaseManager artfm = null;


    public static ArtesaoFirebaseManager getInstance() {
        if (artfm == null) {
            artfm = new ArtesaoFirebaseManager();
        }

        return artfm;
    }

    public void addArtToList (Artesao artesao) {
        artList.add(artesao);
    }

    public Artesao getArtesaoById(String id) {
        Artesao artesao = new Artesao();
        for (int i = 0; i < artList.size(); i++) {
            if (artList.get(i).getId().equals(id)) {
                artesao = artList.get(i);
                return artesao;
            }
        }
        return artesao;
    }

    public void clearArtList() {artList.clear();}
}
