package com.project.art.artesanato20;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.art.artesanato20.models.Artesao;
import com.project.art.artesanato20.models.Artigo;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private ArrayList<Artesao> artList;
    private Context context;
    private StorageReference mStorageRef;

    public UserAdapter(ArrayList<Artesao> artList) {
        System.out.println("meow");
        this.artList = artList;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        System.out.println("ola");
        this.context = parent.getContext();
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout, parent, false);
        return new ViewHolder(viewItem);

    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        final Artesao artesao = artList.get(position);
        System.out.println("woof");
        holder.name.setText(artesao.getName());

        Picasso.get().load(artesao.getPhotoURL()).fit().into(holder.image);

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ItemActivity.class);
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

        protected TextView name, likes;
        protected ImageView image;
        private CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);
            System.out.println("Mmm");
            name = itemView.findViewById(R.id.nomeItemCard);
            image = itemView.findViewById(R.id.imgItemCard);
            cv = itemView.findViewById(R.id.cardItem);


        }

    }
}
