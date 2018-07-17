package com.project.art.artesanato20.impl;

import com.project.art.artesanato20.models.Event;

import java.util.ArrayList;

public interface IEvent {
    ArrayList<Event> getEventList();

    void addEventToList (Event event);

    Event getEventById(String id);

    void clearList();
}

