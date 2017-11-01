package com.example.arlesonls.moviespop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class FilmeController {
    private Context context;

    public FilmeController(Context context){

        this.context = context;
    }

    public void inserirFilme(Filmes filme){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(dbHelper.NOME, filme.getNome() );
        contentValues.put(dbHelper.SINOPSE, filme.getSinopse() );
        contentValues.put(dbHelper.IMAGEM, filme.getImagem());
        contentValues.put(dbHelper.NU_ACESSO, filme.getNuAcesso()+1);

        long id = db.insert(dbHelper.TABLE, null, contentValues);
        db.close();
    }

    public boolean verificaFilme(Filmes filme) {
        DbHelper dbHelper = new DbHelper(this.context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT count(*) FROM " + dbHelper.TABLE +"  WHERE " + dbHelper.NOME
                + " = \"" +filme.getNome() +"\"";
        long quantidadeFilmeCadastrado = DatabaseUtils.longForQuery(db, sql,null);
        return quantidadeFilmeCadastrado > 0;
    }

    public void updateNuAcesso(Filmes filme) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "UPDATE " + dbHelper.TABLE + " SET " + dbHelper.NU_ACESSO +
                " = " + dbHelper.NU_ACESSO + " +1 WHERE " + dbHelper.NOME + " = \"" + filme.getNome() +"\"";

        db.execSQL(sql);
        db.close();
    }

    public List<Filmes> listaFilme() {

        DbHelper dbHelper = new DbHelper(this.context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + dbHelper.TABLE + " ORDER BY " +dbHelper.NU_ACESSO +" DESC";

        Cursor cursor = db.rawQuery(sql, null);

        List<Filmes> filmes = new ArrayList<>();

        while (cursor.moveToNext()) {
            Filmes filme = new Filmes();
            filme.setNome(cursor.getString(cursor.getColumnIndex(dbHelper.NOME)));
            filme.setSinopse(cursor.getString(cursor.getColumnIndex(dbHelper.SINOPSE)));
            filme.setImagem(cursor.getString(cursor.getColumnIndex(dbHelper.IMAGEM)));
            filme.setNuAcesso(Integer.parseInt(cursor.getString(cursor.getColumnIndex(dbHelper.NU_ACESSO))));

            filmes.add(filme);
        }
        cursor.close();
        return filmes;

    }
}
