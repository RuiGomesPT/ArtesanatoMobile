package com.project.art.artesanato20.impl;

import com.project.art.artesanato20.models.Artigo;
import com.project.art.artesanato20.models.User;

import java.util.ArrayList;

public class UserFirebaseManager implements IUser{
    public ArrayList<User> userList = new ArrayList<>();


    public ArrayList<User> getUserList() {
        return userList;
    }

    static UserFirebaseManager ufm = null;


    public static UserFirebaseManager getInstance() {
        if (ufm == null) {
            ufm = new UserFirebaseManager();
        }

        return ufm;
    }

    public void addUserToList(User user) {
        userList.add(user);
    }

    public void clearUserList() {
        userList.clear();
    }
}
