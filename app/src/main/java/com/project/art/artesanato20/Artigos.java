package com.project.art.artesanato20;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.project.art.artesanato20.impl.ArtigoFirebaseManager;
import com.project.art.artesanato20.models.Artigo;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Artigos extends Fragment {
    RecyclerView rv;
    ArtAdapter artAdapter;
    private ArrayList<String> tags = new ArrayList<>();
    FloatingActionButton filterBtn;
    ArrayList<Artigo> ARTIGOS = new ArrayList<>();
    GoogleSignInClient mGoogleSignInClient;

    public Artigos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_artigos, container, false);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());

        rv = rootView.findViewById(R.id.recViewArtigos);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(rootView.getContext());
        rv.setLayoutManager(llm);

        ARTIGOS = ArtigoFirebaseManager.getInstance().getItemList();

        fillArtList(rootView);

        filterBtn = rootView.findViewById(R.id.filterBtnArt);
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterItems(rootView);
            }
        });


        return rootView;
    }

    public void filterItems(final View view) {
        final CharSequence[] items = {"Têxteis", "Cerâmica", "Elementos Vegetais", "Peles e Couros", "Madeira e Cortiça", "Pedra e Metal", "Papel e Artes Gráficas", "Restauro", "Bens Alimentares", "Outros"};

        final ArrayList selectedItems=new ArrayList();

        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Tags to filter by")
                .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            selectedItems.add(items[indexSelected]);
                        } else if (selectedItems.contains(indexSelected)) {
                            // Else, if the item is already in the array, remove it
                            selectedItems.remove(items[indexSelected]);
                        }
                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        tags.clear();
                        //  Your code when user clicked on OK
                        //  You can write the code  to save the selected item here
                        for (int i = 0; i < selectedItems.size(); i++) {
                            tags.add(selectedItems.get(i).toString());
                        }

                        fillArtList(view);

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //  Your code when user clicked on Cancel
                    }
                }).create();
        dialog.show();
    }

    public void fillArtList(View view) {

        ARTIGOS = ArtigoFirebaseManager.getInstance().getItemList();
        System.out.println(ARTIGOS.size());
        artAdapter = new ArtAdapter(ARTIGOS);
        rv.setAdapter(artAdapter);
    }

}
