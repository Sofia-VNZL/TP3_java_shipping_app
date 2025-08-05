package org.example;

public class Produto {
    private int idPedido;
    private String nomeProduto;
    private double pesoUnitarioProduto;
    private int quantidadeSolicitada;
    private double valorUnitario;

    public Produto() { }
    public Produto(int idPedido, String nomeProduto, double pesoUnitarioProduto,
                   int quantidadeSolicitada, double valorUnitario) {
        this.idPedido = idPedido;
        this.nomeProduto = nomeProduto;
        this.pesoUnitarioProduto = pesoUnitarioProduto;
        this.quantidadeSolicitada = quantidadeSolicitada;
        this.valorUnitario = valorUnitario;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "idPedido=" + idPedido +
                ", nomeProduto='" + nomeProduto + '\'' +
                ", pesoUnitarioProduto=" + pesoUnitarioProduto +
                ", quantidadeSolicitada=" + quantidadeSolicitada +
                ", valorUnitario=" + valorUnitario +
                '}';
    }

}
