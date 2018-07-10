package br.com.thideoli.vendedormovel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import br.com.thideoli.vendedormovel.adapter.PedidoAdapter;
import br.com.thideoli.vendedormovel.dao.PedidoDAO;
import br.com.thideoli.vendedormovel.model.Pedido;

public class OrdersActivity extends AppCompatActivity {

    private ListView lvPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Pedidos");
        setContentView(R.layout.activity_orders);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onResume() {
        super.onResume();

        PedidoDAO pedidoDAO = new PedidoDAO(OrdersActivity.this);

        List<Pedido> pedidos = pedidoDAO.listAll();

        lvPedidos = findViewById(R.id.list_orders);
        PedidoAdapter arrayAdapter = new PedidoAdapter(OrdersActivity.this, pedidos);
        lvPedidos.setAdapter(arrayAdapter);
        lvPedidos.setOnItemClickListener(chamaTelaDetalhePedido());

    }

    private AdapterView.OnItemClickListener chamaTelaDetalhePedido() {
        return new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pedido pedido = (Pedido) lvPedidos.getItemAtPosition(position);

                /*
                Intent intent = new Intent(OrdersActivity.this, OrderActivity.class);
                intent.putExtra("pedido", pedido);
                startActivity(intent);
                */
            }
        };
    }
}
