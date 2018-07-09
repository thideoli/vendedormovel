package br.com.thideoli.vendedormovel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import br.com.thideoli.vendedormovel.adapter.ClienteAdapter;
import br.com.thideoli.vendedormovel.dao.ClienteDAO;
import br.com.thideoli.vendedormovel.model.Cliente;

public class CustomersActivity extends AppCompatActivity {

    private ListView lvClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.customers);
        setContentView(R.layout.activity_customers);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ClienteDAO clienteDAO = new ClienteDAO(CustomersActivity.this);

        List<Cliente> clientes = clienteDAO.listAll();

        lvClientes = findViewById(R.id.list_customers);
        ClienteAdapter arrayAdapter = new ClienteAdapter(CustomersActivity.this, clientes);
        lvClientes.setAdapter(arrayAdapter);
        lvClientes.setOnItemClickListener(chamaTelaDetalheCliente());

    }

    private AdapterView.OnItemClickListener chamaTelaDetalheCliente() {
        return new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cliente cliente = (Cliente) lvClientes.getItemAtPosition(position);

                Intent intent = new Intent(CustomersActivity.this, CustomerActivity.class);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
            }
        };
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
