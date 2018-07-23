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
import com.project.art.artesanato20.models.Artigo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ArtigoArtAdapter extends RecyclerView.Adapter<ArtigoArtAdapter.ViewHolder> {
    private ArrayList<Artigo> itemList;
    private Context context;
    private StorageReference mStorageRef;


    public ArtigoArtAdapter(ArrayList<Artigo> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout, parent, false);
        return new ViewHolder(viewItem);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView name;
        protected ImageView image;
        private CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nomeItemCard);
            image = itemView.findViewById(R.id.imgArtLay);
            cv = itemView.findViewById(R.id.cardItem);


        }

    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Artigo artigo = itemList.get(position);
        holder.name.setText(artigo.getNome());

        mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference artImg = mStorageRef.child("images").child(artigo.getPhotoURL());

        File localFile = null;
        try {
            localFile = File.createTempFile("images", "jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        final File newFile = localFile;
        artImg.getFile(newFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Successfully downloaded data to local file
                        // ...

                        holder.image.setImageURI(Uri.fromFile(newFile));
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
            }
        });


        //Picasso.get().load(artigo.getPhotoURL()).fit().into(holder.image);

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ItemActivity.class);
                intent.putExtra("id", artigo.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }



}