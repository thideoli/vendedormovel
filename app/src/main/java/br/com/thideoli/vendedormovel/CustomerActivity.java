package br.com.thideoli.vendedormovel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import br.com.thideoli.vendedormovel.model.Cliente;

public class CustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Cliente");
        setContentView(R.layout.activity_customer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Cliente cliente = (Cliente) intent.getSerializableExtra("cliente");

        TextView codigo = findViewById(R.id.customer_detail_code);
        TextView nome = findViewById(R.id.customer_detail_name);
        TextView cnpj = findViewById(R.id.customer_detail_cnpj);
        TextView endereco = findViewById(R.id.customer_detail_address);
        TextView telefone = findViewById(R.id.customer_detail_phone);
        TextView email = findViewById(R.id.customer_detail_email);

        codigo.setText(cliente.getCodigo());
        nome.setText(cliente.getRazaoSocial());
        cnpj.setText(cliente.getCnpj());
        endereco.setText(cliente.getEndereco());
        telefone.setText(cliente.getTelefone());
        email.setText(cliente.getEmail());

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
