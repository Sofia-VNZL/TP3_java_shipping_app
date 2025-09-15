package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Pedido {
    private int idPedido;
    private LocalDate dataPedido;
    private String status;
    private List<Produto> produtos;
    private String cpfCliente;

    public Pedido(int idPedido, String cpfCliente) {
        this.idPedido = idPedido;
        this.cpfCliente = cpfCliente;
        this.dataPedido = LocalDate.now();
        this.status = "Em aberto";
        this.produtos = new ArrayList<>();
    }

    public String getStatus() {
        return status;
    }

    public void abrirPedido(List<Produto> catalogoProdutos) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Pedido " + idPedido + " iniciado.");
        System.out.println("\nProdutos disponíveis no catálogo: ");
        for (Produto p : catalogoProdutos) {
            System.out.printf("- %s | Valor: R$ %.2f | Peso: %.2fkg%n",
                    p.getNomeProduto(), p.getValorUnitario(), p.getPesoUnitarioProduto());
        }
        System.out.println("Digite o nome do produto para adicionar. Digite 'fim' para encerrar.");


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

    }

    public void fecharPedido() {
        this.status = "Fechado";
        System.out.println("Pedido fechado com valor total: R$ " + calcularValorTotal());
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

    public void salvarEmCSV(String caminhoArquivo) {

        double total = calcularValorTotal();
        try (PrintWriter writer = new PrintWriter(new FileWriter(caminhoArquivo, true))) {
            for (Produto p : produtos) {
                writer.println(idPedido + "," +
                        cpfCliente + "," +
                        dataPedido + "," +
                        status + "," +
                        p.getNomeProduto() + "," +
                        p.getQuantidadeSolicitada() + "," +
                        p.getValorUnitario() + "," +
                        total);
            }
            System.out.println("Pedido " + idPedido + " salvo com sucesso");

        } catch (IOException e) {
            System.out.println("Erro ao salvar pedido: " + e.getMessage());
        }
    }

    public void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto no pedido");
        } else {
            System.out.println("Produtos no pedido:");
            for (Produto p : produtos) {
                System.out.printf("- %s | Quantidade: %d | Valor unitário: R$ %.2f%n",
                        p.getNomeProduto(), p.getQuantidadeSolicitada(), p.getValorUnitario());
            }
        }
    }

}