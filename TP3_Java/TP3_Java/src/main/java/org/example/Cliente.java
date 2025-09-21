package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class Cliente extends Usuario {

    public Cliente(int IDUsuario, String nomeCompleto, String cpf, String numeroCelular, String email, String senha, String nivelAcesso, String cargo) {
        super(IDUsuario, nomeCompleto, cpf, numeroCelular, email, senha, nivelAcesso, cargo);
    }

    public Cliente() {
        super(0, "Novo Cliente", "000.000.000-00", "000000000", "email@exemplo.com", "senha", "cliente", "cargo");
    }

    @Override
    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        List<Produto> catalogoProdutos = Produto.carregarCatalogoCSV("src/main/resources/produtos.csv");
        List<Pedido> pedidosDoCliente = new ArrayList<>();
        Pedido pedidoAtual = null;

        while (true) {
            System.out.println("\n-------Menu do Cliente------");
            System.out.println("1 - Abrir novo pedido");
            System.out.println("2 - Consultar pedidos realizados");
            System.out.println("3 - Cancelar pedido em aberto");
            System.out.println("4 - Sair");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    if (pedidoAtual != null && !pedidoAtual.getStatus().equals("Fechado")) {
                        System.out.println("Você já tem um pedido em aberto. Feche ou cancele antes de abrir outro.");
                    } else {
                        int novoId = pedidosDoCliente.size() + 1;
                        pedidoAtual = new Pedido(novoId, this.getCpf());
                        pedidoAtual.abrirPedido(catalogoProdutos);
                        pedidoAtual.fecharPedido();
                        pedidoAtual.salvarEmCSV("src/main/resources/Pedido.csv");
                        pedidosDoCliente.add(pedidoAtual);
                        pedidoAtual = null;
                    }
                    break;

                case "2":
                    if (pedidosDoCliente.isEmpty()) {
                        System.out.println("Nenhum pedido realizado.");
                    } else {
                        for (Pedido p : pedidosDoCliente) {
                            System.out.println(p);
                            p.listarProdutos();
                        }
                    }
                    break;

                case "3":
                    if (pedidoAtual == null || pedidoAtual.getStatus().equals("Fechado")) {
                        System.out.println("Nenhum pedido em aberto para cancelar.");
                    } else {
                        pedidoAtual = null;
                        System.out.println("Pedido cancelado.");
                    }
                    break;

                case "4":
                    System.out.println("Saindo do menu do cliente...");
                    return;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}