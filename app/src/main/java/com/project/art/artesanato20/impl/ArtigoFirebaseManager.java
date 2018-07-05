package com.project.art.artesanato20.impl;

import com.project.art.artesanato20.models.Artigo;

import java.util.ArrayList;

public class ArtigoFirebaseManager implements IArtigo {
    public ArrayList<Artigo> itemList = new ArrayList<>();


    public ArrayList<Artigo> getItemList() {
        return itemList;
    }

    static ArtigoFirebaseManager afm = null;


    public static ArtigoFirebaseManager getInstance() {
        if (afm == null) {
            afm = new ArtigoFirebaseManager();
        }

        return afm;
    }

    public void addItemToList (Artigo artigo) {
        itemList.add(artigo);
    }

    public Artigo getArtigoById (String id) {
        Artigo artigo = new Artigo();
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getId().equals(id)) {
                artigo = itemList.get(i);
                return artigo;
            }
        }
        return artigo;
    }

    public void clearList() {
        itemList.clear();
    }

}

