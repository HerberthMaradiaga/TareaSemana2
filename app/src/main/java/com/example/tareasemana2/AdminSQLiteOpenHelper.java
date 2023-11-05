package com.example.tareasemana2;
//se importan las librerias que vamos a utilizar
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.jetbrains.annotations.Nullable;
//creamos la clase de la base de datos
public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    //creamos la base de datos
    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable
    SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //creamos la tabla de la base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table articulos(codigo int primary key,descripcion text,precio real)");
    }
    //y decñaramos un evento para poder actualizar la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
