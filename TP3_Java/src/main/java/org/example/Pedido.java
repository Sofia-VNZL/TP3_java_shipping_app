package org.example;

import java.util.Date;

public class Pedido {
    private int idPedido;
    private String cpfEmissor;
    private String statusPedido;
    private Date dataEmissao;
    private Date dataEntregaPrevista;
    private Date dataEntregaReal;
    private double valorFrete;
    private double valorTotal;

    public Pedido() {}
    public Pedido(int idPedido, String cpfEmissor, String statusPedido, Date dataEmissao,
                  Date dataEntregaPrevista, Date dataEntregaReal, double valorFrete, double valorTotal) {
        this.idPedido = idPedido;
        this.cpfEmissor = cpfEmissor;
        this.statusPedido = statusPedido;
        this.dataEmissao = dataEmissao;
        this.dataEntregaPrevista = dataEntregaPrevista;
        this.dataEntregaReal = dataEntregaReal;
        this.valorFrete = valorFrete;
        this.valorTotal = valorTotal;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", cpfEmissor='" + cpfEmissor + '\'' +
                ", statusPedido='" + statusPedido + '\'' +
                ", dataEmissao=" + dataEmissao +
                ", dataEntregaPrevista=" + dataEntregaPrevista +
                ", dataEntregaReal=" + dataEntregaReal +
                ", valorFrete=" + valorFrete +
                ", valorTotal=" + valorTotal +
                '}';
    }


}

