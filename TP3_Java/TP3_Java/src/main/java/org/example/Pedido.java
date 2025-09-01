package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Pedido {
    private int idPedido;
    private LocalDate dataPedido;
    private String status;
    private List<Produto> produtos;

    public Pedido(int idPedido) {
        this.idPedido = idPedido;
        this.dataPedido = LocalDate.now();
        this.status = "Em aberto";
        this.produtos = new ArrayList<>();
    }

    public void abrirPedido(List<Produto> catalogoProdutos) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Pedido " + idPedido + " iniciado. Digite o nome do produto para adicionar. Digite 'fim' para encerrar.");

        while (true) {
            System.out.print("Produto: ");
            String nomeProduto = scanner.nextLine();

            if (nomeProduto.equalsIgnoreCase("fim")) {
                break;
            }

            Produto produtoSelecionado = null;
            for (Produto p : catalogoProdutos) {
                if (p.getNomeProduto().equalsIgnoreCase(nomeProduto)) {
                    produtoSelecionado = p;
                    break;
                }
            }

            if (produtoSelecionado == null) {
                System.out.println("Produto não encontrado.");
                continue;
            }

            System.out.print("Quantidade: ");
            int quantidade = Integer.parseInt(scanner.nextLine());

            Produto produtoPedido = new Produto(
                    idPedido,
                    produtoSelecionado.getNomeProduto(),
                    produtoSelecionado.getPesoUnitarioProduto(),
                    quantidade,
                    produtoSelecionado.getValorUnitario()
            );

            produtos.add(produtoPedido);
            System.out.println("Produto adicionado: " + produtoPedido.getNomeProduto() + " x" + quantidade);
        }

        this.status = "Fechado";
        System.out.println("Pedido fechado com " + produtos.size() + " itens.");
    }

    public double calcularValorTotal() {
        double total = 0;
        for (Produto p : produtos) {
            total += p.getValorUnitario() * p.getQuantidadeSolicitada();
        }
        return total;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", dataPedido=" + dataPedido +
                ", status='" + status + '\'' +
                ", produtos=" + produtos +
                ", valorTotal=" + calcularValorTotal() +
                '}';
    }
}