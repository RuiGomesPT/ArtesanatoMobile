package com.project.art.artesanato20;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.project.art.artesanato20.models.Artesao;
import com.project.art.artesanato20.models.Event;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{
    private ArrayList<Event> EVENTS;
    private Context context;

    public EventAdapter(ArrayList<Event> EVENTS) {this.EVENTS = EVENTS;}

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card, parent, false);
        return new ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull final EventAdapter.ViewHolder holder, int position) {
        final Event event = EVENTS.get(position);
        holder.name.setText(event.getNome());
        holder.date.setText(event.getData());
        holder.hour.setText(event.getHora());

        holder.switchEvent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.switchEvent.isChecked()) {
                    Intent intent = new Intent(context, NotService.class);
                    intent.putExtra("id", event.getId());
                    context.startService(intent);
                } else {

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return EVENTS.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView name, date, hour;
        protected Switch switchEvent;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameEvent);
            date = itemView.findViewById(R.id.dateEvent);
            hour = itemView.findViewById(R.id.hourEvent);
            switchEvent = itemView.findViewById(R.id.switchEvent);


        }

    }
}
