package org.example;

import java.net.http.*;
import java.net.URI;
import java.util.Scanner;

public class ClienteCLI {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        HttpClient client = HttpClient.newHttpClient();

        System.out.println("----- Login -----");
        System.out.print("CPF ou e-mail: ");
        String identificador = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        String loginJson = String.format("{\"cpf\":\"%s\",\"senha\":\"%s\"}", identificador, senha);

        HttpRequest loginRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:7000/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(loginJson))
                .build();

        HttpResponse<String> loginResponse = client.send(loginRequest, HttpResponse.BodyHandlers.ofString());

        if (loginResponse.statusCode() == 200) {
            System.out.println("Login OK!");

            while (true) {
                System.out.println("\n--- Menu do Cliente ---");
                System.out.println("1 - Ver produtos");
                System.out.println("2 - Fazer pedido");
                System.out.println("3 - Consultar pedidos");
                System.out.println("4 - Sair");
                System.out.print("Escolha: ");
                String opcao = scanner.nextLine();

                switch (opcao) {
                    case "1":
                        HttpRequest produtosRequest = HttpRequest.newBuilder()
                                .uri(URI.create("http://localhost:7000/produtos"))
                                .GET()
                                .build();

                        HttpResponse<String> produtosResponse = client.send(produtosRequest, HttpResponse.BodyHandlers.ofString());
                        System.out.println("Catálogo de produtos:");
                        System.out.println(produtosResponse.body());
                        break;

                    case "2":
                        System.out.print("Nome do produto: ");
                        String nome = scanner.nextLine();
                        System.out.print("Quantidade: ");
                        int qtd = Integer.parseInt(scanner.nextLine());


                        HttpRequest produtosRequestPedido = HttpRequest.newBuilder()
                                .uri(URI.create("http://localhost:7000/produtos"))
                                .GET()
                                .build();

                        HttpResponse<String> produtosResponsePedido = client.send(produtosRequestPedido, HttpResponse.BodyHandlers.ofString());
                        String produtosJson = produtosResponsePedido.body();


                        double pesoUnitario = 0.0;
                        double valorUnitario = 0.0;

                        String[] produtos = produtosJson.replace("[", "").replace("]", "").split("\\}, \\{");
                        for (String produto : produtos) {
                            if (produto.contains("nomeProduto=" + nome)) {
                                String[] campos = produto.split(", ");
                                for (String campo : campos) {
                                    if (campo.contains("pesoUnitarioProduto=")) {
                                        pesoUnitario = Double.parseDouble(campo.split("=")[1]);
                                    }
                                    if (campo.contains("valorUnitario=")) {
                                        valorUnitario = Double.parseDouble(campo.split("=")[1]);
                                    }
                                }
                                break;
                            }
                        }

                        if (valorUnitario == 0.0) {
                            System.out.println("Produto não encontrado ou sem valor definido.");
                            break;
                        }


                        String pedidoJson = String.format("""
                            {
                              "idPedido": 0,
                              "cpfCliente": "%s",
                              "status": "Fechado",
                              "produtos": [
                                {
                                  "idPedido": 0,
                                  "nomeProduto": "%s",
                                  "pesoUnitarioProduto": %.2f,
                                  "quantidadeSolicitada": %d,
                                  "valorUnitario": %.2f
                                }
                              ]
                            }
                        """, identificador, nome, pesoUnitario, qtd, valorUnitario);

                        HttpRequest pedidoRequest = HttpRequest.newBuilder()
                                .uri(URI.create("http://localhost:7000/pedidos"))
                                .header("Content-Type", "application/json")
                                .POST(HttpRequest.BodyPublishers.ofString(pedidoJson))
                                .build();

                        HttpResponse<String> pedidoResponse = client.send(pedidoRequest, HttpResponse.BodyHandlers.ofString());
                        System.out.println("Resposta do servidor:");
                        System.out.println(pedidoResponse.body());
                        break;

                    case "3":
                        HttpRequest consultaRequest = HttpRequest.newBuilder()
                                .uri(URI.create("http://localhost:7000/pedidos/" + identificador))
                                .GET()
                                .build();

                        HttpResponse<String> consultaResponse = client.send(consultaRequest, HttpResponse.BodyHandlers.ofString());
                        System.out.println("Pedidos realizados:");
                        System.out.println(consultaResponse.body());
                        break;

                    case "4":
                        System.out.println("Saindo...");
                        return;
                    default:
                        System.out.println("Opção inválida.");
                }
            }
        } else {
            System.out.println("Login inválido.");
        }
    }
}