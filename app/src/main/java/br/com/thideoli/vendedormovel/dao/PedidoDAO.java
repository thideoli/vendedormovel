package br.com.thideoli.vendedormovel.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.thideoli.vendedormovel.model.Pedido;
import br.com.thideoli.vendedormovel.model.ProdutoPedido;

public class PedidoDAO extends DAO {

    private final Context context;

    public void deletePedidoNotSend(){

        PedidoDAO pedidoDAO = new PedidoDAO(context);
        List<Pedido> pedidos = pedidoDAO.listNotSend();

        for(Pedido pedido : pedidos){

            ProdutoPedidoDAO produtoPedidoDAO = new ProdutoPedidoDAO(context);
            produtoPedidoDAO.deleteByPedido(pedido.getCodigo());

            String[] args = {pedido.getCodigo()};

            SQLiteDatabase db = getReadableDatabase();
            db.delete("Pedidos", "codigo = ?", args);
        }

    }

    public void deleteAll(){

        PedidoDAO pedidoDAO = new PedidoDAO(context);
        List<Pedido> pedidos = pedidoDAO.listAll();

        for(Pedido pedido : pedidos){

            ProdutoPedidoDAO produtoPedidoDAO = new ProdutoPedidoDAO(context);
            produtoPedidoDAO.deleteByPedido(pedido.getCodigo());

            String[] args = {pedido.getCodigo()};

            SQLiteDatabase db = getReadableDatabase();
            db.delete("Pedidos", "codigo = ?", args);
        }

    }

    public PedidoDAO(Context context) {
        super(context);
        this.context = context;
    }

    public List<Pedido> listAll(){
        String sql = "SELECT * FROM Pedidos";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Pedido> pedidos = new ArrayList<Pedido>();

        while (c.moveToNext()){
            Pedido pedido = getPedido(c);
            pedidos.add(pedido);
        }

        c.close();

        return pedidos;
    }

    public List<Pedido> listNotSend(){
        String sql = "SELECT * FROM Pedidos WHERE enviado = 0";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Pedido> pedidos = new ArrayList<Pedido>();

        while (c.moveToNext()){
            Pedido pedido = getPedido(c);
            pedidos.add(pedido);
        }

        c.close();

        return pedidos;
    }

    public Pedido findByCode(String codigo){
        String sql = "SELECT * FROM Pedidos WHERE codigo = ?";

        String[] args = {codigo};

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, args);

        Pedido pedido = null;

        if (c.moveToNext())
            pedido = getPedido(c);

        c.close();

        return pedido;
    }

    public void insert(Pedido pedido) throws Exception{
        ContentValues cv = getContentValues(pedido);

        SQLiteDatabase db = getWritableDatabase();
        db.insert("Pedidos", null, cv);
    }

    public void update(Pedido pedido){
        ContentValues cv = getContentValues(pedido);

        String[] args = {pedido.getCodigo()};

        SQLiteDatabase db = getWritableDatabase();
        db.update("Pedidos", cv, "codigo = ?", args);
    }

    @NonNull
    private ContentValues getContentValues(Pedido pedido) {
        ContentValues cv = new ContentValues();
        cv.put("codigo", pedido.getCodigo());
        cv.put("cliente", pedido.getCliente());
        cv.put("vendedor", pedido.getVendedor());
        cv.put("data", pedido.getData());
        cv.put("total", pedido.getTotal());
        cv.put("enviado", pedido.getEnviado());
        return cv;
    }

    @NonNull
    private Pedido getPedido(Cursor c) {
        ProdutoPedidoDAO produtoPedidoDAO = new ProdutoPedidoDAO(context);
        return new Pedido(
                c.getString(c.getColumnIndex("codigo")),
                c.getString(c.getColumnIndex("cliente")),
                c.getString(c.getColumnIndex("vendedor")),
                c.getString(c.getColumnIndex("data")),
                c.getDouble(c.getColumnIndex("total")),
                c.getInt(c.getColumnIndex("enviado")),
                produtoPedidoDAO.listByPedido(c.getString(c.getColumnIndex("codigo"))));
    }
}
