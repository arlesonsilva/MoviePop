package com.example.arlesonls.moviespop;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

import static android.view.View.INVISIBLE;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    private RecyclerView viewRecycler;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewRecycler = (RecyclerView) findViewById(R.id.recycler);
        textView1 = (TextView) findViewById(R.id.text_view1);
        textView2 = (TextView) findViewById(R.id.text_view2);
        textView3 = (TextView) findViewById(R.id.text_view3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("MoviesPoP");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.populares:
                        textView1.setVisibility(INVISIBLE);
                        textView2.setVisibility(INVISIBLE);
                        textView3.setVisibility(INVISIBLE);
                        new AsyncTaskFilmes(MainActivity.this).execute("https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=6da3b07792e93f9625482e43d8480521");
                        break;
                    case R.id.acessados:
                        textView1.setVisibility(INVISIBLE);
                        textView2.setVisibility(INVISIBLE);
                        textView3.setVisibility(INVISIBLE);
                        List<Filmes> listaFilme = ListaFilmes();
                        if (listaFilme == null || listaFilme.size()<1){
                            Toast.makeText(MainActivity.this,"Nenhum filme encontrado",Toast.LENGTH_LONG).show();
                        }
                        ListaFilmesAdapter listaFilmesAdapter = new ListaFilmesAdapter(listaFilme, MainActivity.this);
                        RecyclerView recyclerView = (RecyclerView) MainActivity.this.findViewById(R.id.recycler);
                        recyclerView.setAdapter(listaFilmesAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private List<Filmes> ListaFilmes() {
        FilmeController filme = new FilmeController(this);
        return filme.listaFilme();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
