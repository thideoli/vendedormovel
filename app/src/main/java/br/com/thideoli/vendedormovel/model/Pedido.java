package br.com.thideoli.vendedormovel.model;

import java.io.Serializable;
import java.util.List;

public class Pedido implements Serializable{
    private String codigo;
    private String cliente;
    private String vendedor;
    private String data;
    private double total;
    private int enviado;
    private List<ProdutoPedido> produtosPedido;

    public Pedido() {
    }

    public Pedido(String codigo, String cliente, String vendedor, String data, double total, int enviado, List<ProdutoPedido> produtosPedido) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.data = data;
        this.total = total;
        this.enviado = enviado;
        this.produtosPedido = produtosPedido;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getEnviado() {
        return enviado;
    }

    public void setEnviado(int enviado) {
        this.enviado = enviado;
    }

    public List<ProdutoPedido> getProdutosPedido() {
        return produtosPedido;
    }

    public void setProdutosPedido(List<ProdutoPedido> produtosPedido) {
        this.produtosPedido = produtosPedido;
    }
}
