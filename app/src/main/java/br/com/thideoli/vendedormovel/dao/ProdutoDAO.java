package br.com.thideoli.vendedormovel.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.thideoli.vendedormovel.model.Produto;

public class ProdutoDAO extends DAO {

    public ProdutoDAO(Context context) {
        super(context);
    }

    public List<Produto> listAll(){
        String sql = "SELECT * FROM Produtos";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Produto> produtos = new ArrayList<Produto>();

        while (c.moveToNext()){
            Produto produto = getProduto(c);
            produtos.add(produto);
        }

        c.close();

        return produtos;
    }

    public Produto findByCode(String codigo){
        String sql = "SELECT * FROM Produtos WHERE codigo = ?";

        String[] args = {codigo};

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, args);

        Produto produto = null;

        if (c.moveToNext())
            produto = getProduto(c);

        c.close();

        return produto;
    }

    public void insert(Produto produto) throws Exception{
        ContentValues cv = getContentValues(produto);

        SQLiteDatabase db = getWritableDatabase();
        db.insert("Produtos", null, cv);
    }

    public void update(Produto produto){
        ContentValues cv = getContentValues(produto);

        String[] args = {produto.getCodigo()};

        SQLiteDatabase db = getWritableDatabase();
        db.update("Produtos", cv, "codigo = ?", args);
    }

    @NonNull
    private ContentValues getContentValues(Produto produto) {
        ContentValues cv = new ContentValues();
        cv.put("codigo", produto.getCodigo());
        cv.put("descricao", produto.getDescricao());
        cv.put("preco", produto.getPreco());
        cv.put("estoque", produto.getEstoque());
        return cv;
    }

    @NonNull
    private Produto getProduto(Cursor c) {
        return new Produto(
                c.getString(c.getColumnIndex("codigo")),
                c.getString(c.getColumnIndex("descricao")),
                c.getDouble(c.getColumnIndex("preco")),
                c.getInt(c.getColumnIndex("estoque"))
        );
    }
}
