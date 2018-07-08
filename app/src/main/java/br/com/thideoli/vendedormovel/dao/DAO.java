package br.com.thideoli.vendedormovel.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DAO extends SQLiteOpenHelper {

    public DAO(Context context) {
        super(context, "VendedorMovel", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "";

        //Cria tabela Clientes
        sql = "CREATE TABLE Clientes (codigo TEXT PRIMARY KEY, razao_social TEXT NOT NULL, cnpj TEXT NOT NULL, endereco TEXT NOT NULL, telefone TEXT NOT NULL, email TEXT NOT NULL);";
        db.execSQL(sql);

        //Cria tabela Produtos
        sql = "CREATE TABLE Produtos (codigo TEXT PRIMARY KEY, descricao TEXT NOT NULL, preco REAL NOT NULL, estoque INTEGER NOT NULL);";
        db.execSQL(sql);

        //Cria tabela Pedidos
        sql = "CREATE TABLE Pedidos (codigo TEXT PRIMARY KEY, vendedor TEXT NOT NULL, data TEXT NOT NULL, total REAL NOT NULL, sincronizado INTEGER NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}