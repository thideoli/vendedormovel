package br.com.thideoli.vendedormovel.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.thideoli.vendedormovel.model.Cliente;

public class ClienteDAO extends DAO {

    public ClienteDAO(Context context) {
        super(context);
    }

    public List<Cliente> listAll(){
        String sql = "SELECT * FROM Clientes ORDER BY razao_social ASC";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Cliente> clientes = new ArrayList<Cliente>();

        while (c.moveToNext()){
            Cliente cliente = getCliente(c);
            clientes.add(cliente);
        }

        c.close();

        return clientes;
    }

    public Cliente findByCode(String codigo){
        String sql = "SELECT * FROM Clientes WHERE codigo = ? ORDER BY razao_social ASC";

        String[] args = {codigo};

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, args);

        Cliente cliente = null;

        if (c.moveToNext())
            cliente = getCliente(c);

        c.close();

        return cliente;
    }

    public void insert(Cliente cliente) throws Exception{
        ContentValues cv = getContentValues(cliente);

        SQLiteDatabase db = getWritableDatabase();
        db.insert("Clientes", null, cv);
    }

    public void update(Cliente cliente){
        ContentValues cv = getContentValues(cliente);

        String[] args = {cliente.getCodigo()};

        SQLiteDatabase db = getWritableDatabase();
        db.update("Clientes", cv, "codigo = ?", args);
    }

    @NonNull
    private ContentValues getContentValues(Cliente cliente) {
        ContentValues cv = new ContentValues();
        cv.put("codigo", cliente.getCodigo());
        cv.put("razao_social", cliente.getRazaoSocial());
        cv.put("cnpj", cliente.getCnpj());
        cv.put("endereco", cliente.getEndereco());
        cv.put("telefone", cliente.getTelefone());
        cv.put("email", cliente.getEmail());
        return cv;
    }

    @NonNull
    private Cliente getCliente(Cursor c) {
        return new Cliente(
                c.getString(c.getColumnIndex("codigo")),
                c.getString(c.getColumnIndex("razao_social")),
                c.getString(c.getColumnIndex("cnpj")),
                c.getString(c.getColumnIndex("endereco")),
                c.getString(c.getColumnIndex("telefone")),
                c.getString(c.getColumnIndex("email"))
        );
    }
}
