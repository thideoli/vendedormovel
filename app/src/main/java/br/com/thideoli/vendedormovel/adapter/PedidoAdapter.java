package br.com.thideoli.vendedormovel.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.thideoli.vendedormovel.R;
import br.com.thideoli.vendedormovel.model.Pedido;

public class PedidoAdapter extends BaseAdapter{

    private final List<Pedido> pedidos;
    private Context context;

    public PedidoAdapter(Context context, List<Pedido> pedidos) {
        this.pedidos = pedidos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.pedidos.size();
    }

    @Override
    public Object getItem(int position) {
        return this.pedidos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Pedido pedido = pedidos.get(position);

        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View view = layoutInflater.inflate(R.layout.list_orders, null);

        TextView cliente = view.findViewById(R.id.order_code);
        cliente.setText(pedido.getCliente());

        TextView data = view.findViewById(R.id.order_date);
        data.setText(pedido.getData());

        TextView enviado = view.findViewById(R.id.order_sended);
        enviado.setText("Aguardando");
        if(pedido.getEnviado() == 1) {
            enviado.setTextColor(Color.GREEN);
            enviado.setText("Enviado");
        }

        return view;
    }
}
