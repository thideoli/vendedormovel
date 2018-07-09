package br.com.thideoli.vendedormovel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.thideoli.vendedormovel.R;
import br.com.thideoli.vendedormovel.model.Cliente;

public class ClienteAdapter extends BaseAdapter{

    private final List<Cliente> clientes;
    private Context context;

    public ClienteAdapter(Context context, List<Cliente> clientes) {
        this.clientes = clientes;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.clientes.size();
    }

    @Override
    public Object getItem(int position) {
        return this.clientes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Cliente cliente = clientes.get(position);

        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View view = layoutInflater.inflate(R.layout.list_customers, null);

        TextView nome = view.findViewById(R.id.customer_name);
        nome.setText(cliente.getRazaoSocial());

        TextView cnpj = view.findViewById(R.id.customer_cnpj);
        cnpj.setText(cliente.getCnpj());

        return view;
    }
}
