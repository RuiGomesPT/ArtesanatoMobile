package com.project.art.artesanato20.impl;

import com.project.art.artesanato20.models.User;

import java.util.ArrayList;

public interface IUser {

    ArrayList<User> getUserList();

    void addUserToList(User user);

    void clearUserList();



}
