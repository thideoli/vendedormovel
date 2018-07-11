package br.com.thideoli.vendedormovel.model;

import java.io.Serializable;

public class ProdutoPedido implements Serializable{
    private String pedido;
    private String produto;
    private double preco;
    private int quantidade;
    private double subtotal;

    public ProdutoPedido() {
    }

    public ProdutoPedido(String pedido, String produto, double preco, int quantidade, double subtotal) {
        this.pedido = pedido;
        this.produto = produto;
        this.preco = preco;
        this.quantidade = quantidade;
        this.subtotal = subtotal;
    }

    public String getPedido() {
        return pedido;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
