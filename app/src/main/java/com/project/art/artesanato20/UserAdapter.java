package com.project.art.artesanato20;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.StorageReference;
import com.project.art.artesanato20.models.Artesao;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private ArrayList<Artesao> artList;
    private Context context;

    public UserAdapter(ArrayList<Artesao> artList) {
        this.artList = artList;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.artesao_layout, parent, false);
        return new ViewHolder(viewItem);

    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        final Artesao artesao = artList.get(position);
        holder.name.setText(artesao.getName());
        System.out.println(artesao.getNomeAtv());
        holder.type.setText(artesao.getNomeAtv());

        Picasso.get().load(artesao.getPhotoURL()).fit().into(holder.image);

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PerfilArtesao.class);
                intent.putExtra("id", artesao.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return artList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView name, type;
        protected ImageView image;
        private CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameArtCard);
            type = itemView.findViewById(R.id.typeArtCard);
            image = itemView.findViewById(R.id.imgArtLay);
            cv = itemView.findViewById(R.id.cardArt);


        }

    }
}
