package br.com.thideoli.vendedormovel.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import br.com.thideoli.vendedormovel.model.Cliente;

public class ClienteDAO extends DAO {

    public ClienteDAO(Context context) {
        super(context);
    }

    public void insert(Cliente cliente){
        ContentValues cv = new ContentValues();
        cv.put("codigo", cliente.getCodigo());
        cv.put("razao_social", cliente.getRazaoSocial());
        cv.put("cnpj", cliente.getCnpj());
        cv.put("endereco", cliente.getEndereco());
        cv.put("telefone", cliente.getTelefone());
        cv.put("email", cliente.getEmail());

        SQLiteDatabase db = getWritableDatabase();
        db.insert("Clientes", null, cv);
    }
}
