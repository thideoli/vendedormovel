package br.com.thideoli.vendedormovel.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;


import br.com.thideoli.vendedormovel.model.ProdutoPedido;


public class ProdutoPedidoDAO extends DAO {

    public ProdutoPedidoDAO(Context context) {
        super(context);
    }

    public List<ProdutoPedido> listByPedido(String pedido){
        String sql = "SELECT * FROM ProdutosPedido WHERE pedido = ?";

        String[] args = {pedido};

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, args);

        List<ProdutoPedido> produtosPedido = new ArrayList<ProdutoPedido>();

        while (c.moveToNext()){
            ProdutoPedido produtoPedido = getProdutoPedido(c);
            produtosPedido.add(produtoPedido);
        }

        c.close();

        return produtosPedido;
    }

    public void deleteByPedido(String pedido){
        String[] args = {pedido};

        SQLiteDatabase db = getReadableDatabase();
        db.delete("ProdutosPedido", "pedido = ?", args);

    }

    public void insert(ProdutoPedido produtoPedido) throws Exception{
        ContentValues cv = getContentValues(produtoPedido);

        SQLiteDatabase db = getWritableDatabase();
        db.insert("ProdutosPedido", null, cv);
    }

    @NonNull
    private ContentValues getContentValues(ProdutoPedido pedido) {
        ContentValues cv = new ContentValues();
        cv.put("pedido", pedido.getPedido());
        cv.put("produto", pedido.getProduto());
        cv.put("preco", pedido.getPreco());
        cv.put("quantidade", pedido.getQuantidade());
        cv.put("subtotal", pedido.getSubtotal());
        return cv;
    }

    @NonNull
    private ProdutoPedido getProdutoPedido(Cursor c) {
        return new ProdutoPedido(
                c.getString(c.getColumnIndex("pedido")),
                c.getString(c.getColumnIndex("produto")),
                c.getDouble(c.getColumnIndex("preco")),
                c.getInt(c.getColumnIndex("quantidade")),
                c.getDouble(c.getColumnIndex("subtotal"))
        );
    }
}
