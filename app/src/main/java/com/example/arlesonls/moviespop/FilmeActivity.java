package com.example.arlesonls.moviespop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class FilmeActivity extends AppCompatActivity {
    private ImageView imgPoster;
    private TextView nomeFilme, sinopse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filme);

        imgPoster = (ImageView) findViewById(R.id.img_fime);
        nomeFilme = (TextView) findViewById(R.id.nome_filme);
        sinopse = (TextView) findViewById(R.id.sinopse_filme);
        Intent intent = getIntent();
        Filmes filme = (Filmes) intent.getSerializableExtra("FILME");
        montaExibicao(filme);
        salvarFilme(filme);

    }

    private void salvarFilme(Filmes filme) {
        FilmeController filmeController = new FilmeController(this);
        if(filmeController.verificaFilme(filme)){
            filmeController.updateNuAcesso(filme);
        }else{
            filmeController.inserirFilme(filme);
        }

    }

    private void montaExibicao(Filmes filme){
        nomeFilme.setText(filme.getNome());
        sinopse.setText(filme.getSinopse());
        Picasso.with(this)
                .load("http://image.tmdb.org/t/p/w500/" +filme.getImagem())
                .placeholder(R.mipmap.ic_launcher)
                .into(imgPoster);
    }
}
