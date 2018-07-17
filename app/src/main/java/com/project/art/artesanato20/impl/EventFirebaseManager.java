package com.project.art.artesanato20.impl;

import com.project.art.artesanato20.models.Event;

import java.util.ArrayList;

public class EventFirebaseManager implements IEvent{
    public ArrayList<Event> eventList = new ArrayList<>();

    public ArrayList<Event> getEventList() {
        return eventList;
    }

    static EventFirebaseManager efm = null;


    public static EventFirebaseManager getInstance() {
        if (efm == null) {
            efm = new EventFirebaseManager();
        }

        return efm;
    }

    public void addEventToList (Event event) {
        eventList.add(event);
    }

    public Event getEventById (String id) {
        Event event = new Event();
        for (int i = 0; i < eventList.size(); i++) {
            if (eventList.get(i).getId().equals(id)) {
                event = eventList.get(i);
                return event;
            }
        }
        return event;
    }

    public void clearList() {
        eventList.clear();
    }
}
