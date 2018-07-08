package br.com.thideoli.vendedormovel.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import br.com.thideoli.vendedormovel.model.Cliente;
import br.com.thideoli.vendedormovel.model.Produto;

public class ProdutoDAO extends DAO {

    public ProdutoDAO(Context context) {
        super(context);
    }

    public void insert(Produto produto){
        ContentValues cv = new ContentValues();
        cv.put("codigo", produto.getCodigo());
        cv.put("descricao", produto.getDescricao());
        cv.put("preco", produto.getPreco());
        cv.put("estoque", produto.getEstoque());

        SQLiteDatabase db = getWritableDatabase();
        db.insert("Produtos", null, cv);
    }
}
