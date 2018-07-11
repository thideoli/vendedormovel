package br.com.thideoli.vendedormovel;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.thideoli.vendedormovel.adapter.ProdutoPedidoAdapter;
import br.com.thideoli.vendedormovel.model.Pedido;
import br.com.thideoli.vendedormovel.model.ProdutoPedido;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Pedido");
        setContentView(R.layout.activity_order);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Pedido pedido = (Pedido) intent.getSerializableExtra("pedido");

        TextView codigo = findViewById(R.id.order_code);
        TextView cliente = findViewById(R.id.order_customer);
        TextView vendedor = findViewById(R.id.order_seller);
        TextView data = findViewById(R.id.order_date);
        TextView total = findViewById(R.id.order_total);
        TextView enviado = findViewById(R.id.order_sended);


        codigo.setText(pedido.getCodigo());
        cliente.setText(pedido.getCliente());
        vendedor.setText(pedido.getVendedor());
        data.setText(pedido.getData());
        total.setText(String.valueOf(pedido.getTotal()));
        enviado.setText("Aguardando");
        if(pedido.getEnviado() == 1) {
            enviado.setTextColor(Color.GREEN);
            enviado.setText("Enviado");
        }

        List<ProdutoPedido> produtosPedido = pedido.getProdutosPedido();
        ListView lvItens = findViewById(R.id.list_itens_order);
        ProdutoPedidoAdapter arrayAdapter = new ProdutoPedidoAdapter(OrderActivity.this, produtosPedido);
        lvItens.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
