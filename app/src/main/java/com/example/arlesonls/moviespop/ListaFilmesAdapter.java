package com.example.arlesonls.moviespop;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListaFilmesAdapter extends RecyclerView.Adapter<ListaFilmesAdapter.ListaFilmeViewHolder> {
    private List<Filmes> listaFilmes = new ArrayList<>();

    private Activity activityMain;

    public  ListaFilmesAdapter(List<Filmes> listaFilmes, Activity activity){
        this.listaFilmes = listaFilmes;
        this.activityMain = activity;
    }

    @Override
    public ListaFilmeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_populares, parent, false);
        return new ListaFilmeViewHolder(view, listaFilmes);
    }

    @Override
    public void onBindViewHolder(ListaFilmeViewHolder holder, int position) {
        final Filmes filme = listaFilmes.get(position);

        holder.txtFilme.setText(filme.getNome());
        Picasso.with(activityMain)
                .load("http://image.tmdb.org/t/p/w500/" +filme.getImagem())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imgFilme);

    }

    @Override
    public int getItemCount() {
        return listaFilmes.size();
    }

    public static class ListaFilmeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imgFilme;
        private TextView txtFilme;
        private List<Filmes> listaFilme;


        public ListaFilmeViewHolder(View itemView, List<Filmes> listaFilme) {
            super(itemView);
            itemView.setOnClickListener(this);
            imgFilme = (ImageView) itemView.findViewById(R.id.item_img_filme);
            txtFilme = (TextView) itemView.findViewById(R.id.item_txt_filme);
            this.listaFilme = listaFilme;
        }

        @Override
        public void onClick(View view) {
            Filmes filme = this.listaFilme.get(getPosition());
            Intent intent = new Intent(view.getContext(), FilmeActivity.class);
            intent.putExtra("FILME", (Serializable) filme);
            view.getContext().startActivity(intent);

        }
    }

}