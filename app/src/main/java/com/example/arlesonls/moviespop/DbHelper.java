package com.example.arlesonls.moviespop;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "moviespop";

    public static final String TABLE = "filmes_populares";
    public static final String ID = "id";
    public static final String NOME = "nome";
    public static final String SINOPSE = "sinopse";
    public static final String IMAGEM = "imagem";
    public static final String NU_ACESSO = "nu_acesso";

    public DbHelper(Context context){

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_FILMES = "CREATE TABLE " + TABLE +
                "(" + ID + "INTEGER PRIMARY KEY, " +
                NOME + " TEXT NOT NULL, "+
                SINOPSE + " TEXT NOT NULL, "+
                IMAGEM + " TEXT NOT NULL, "+
                NU_ACESSO + " INTEGER );";
        db.execSQL(CREATE_TABLE_FILMES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        this.onCreate(db);
    }
}
