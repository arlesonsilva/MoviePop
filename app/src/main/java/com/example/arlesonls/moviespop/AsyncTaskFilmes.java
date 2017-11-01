package com.example.arlesonls.moviespop;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AsyncTaskFilmes extends AsyncTask<String, Void, Void>{
    private List<Filmes> listaFilme = new ArrayList<>();
    private ProgressDialog processComunica;
    private Activity mainActivity;

    public AsyncTaskFilmes (Activity activity){
        this.mainActivity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        processComunica = new ProgressDialog(mainActivity);
        processComunica.setMessage("Carregando filmes");
        processComunica.setCancelable(false);
        processComunica.show();
    }

    @Override
    protected Void doInBackground(String... strings) {

        String urlString = strings[0];
        HttpURLConnection urlConnection = null;
        StringBuilder stringBuilder = new StringBuilder();

        try{
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            StringBuffer buffer = new StringBuffer();

            if(inputStream == null){
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null){
                stringBuilder.append(line);
            }
            reader.close();

            try {
                JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                listaFilme = jsonToListaFilme(jsonArray);
                Log.d("onPost",jsonObject.toString());
            }catch (JSONException e){
                Log.e("JSOn","Error",e); e.printStackTrace();
            }

            return null;

        }catch (IOException e){
            Log.e("Conection","Error",e);
            return null;

        }finally {
            if (urlConnection !=null){
                urlConnection.disconnect();
            }
        }

    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if(processComunica.isShowing()){
            processComunica.dismiss();
        }

        ListaFilmesAdapter listaFilmesAdapter = new ListaFilmesAdapter(listaFilme, mainActivity);
        RecyclerView recyclerView = (RecyclerView) mainActivity.findViewById(R.id.recycler);
        recyclerView.setAdapter(listaFilmesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));

    }

    private List<Filmes> jsonToListaFilme (JSONArray jsonArray){
        List<Filmes> listaFilme = new ArrayList<>();

        for(int i=0;i < jsonArray.length();i++){

            try{
                Filmes filme = new Filmes();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                filme.setImagem(jsonObject.getString("poster_path"));
                filme.setNome(jsonObject.getString("original_title"));
                filme.setSinopse(jsonObject.getString("overview"));
                listaFilme.add(filme);
            }catch (JSONException e){
                Log.e("JSOn","Error",e); e.printStackTrace();
            }
        }

        return listaFilme;

    }
}
