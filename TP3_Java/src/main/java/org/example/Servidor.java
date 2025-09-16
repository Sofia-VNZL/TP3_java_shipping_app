package org.example;

import io.javalin.Javalin;
import java.util.*;

public class Servidor {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);

        /// Endpoint raiz
        app.get("/", ctx -> {
            ctx.result(" API Shipping App rodando com sucesso!");
        });

        ///Login
        app.post("/login", ctx -> {
            String body = ctx.body();

            // Extração manual dos campos do JSON
            String cpfOuEmail = body.split("\"cpf\":\"")[1].split("\"")[0];
            String senha = body.split("\"senha\":\"")[1].split("\"")[0];

            Usuario usuario = Usuario.buscarUsuario(cpfOuEmail, senha);

            if (usuario != null) {
                // Evita erro de serialização se Jackson não estiver configurado
                ctx.status(200).result("Login OK: " + usuario.getNomeCompleto());
            } else {
                ctx.status(401).result("Login inválido");
            }
        });

        ///Fazer pedido
        app.post("/pedidos", ctx -> {
            String body = ctx.body();

            try {
                String cpf = extrairCampo(body, "cpfCliente");
                String nomeProduto = extrairCampo(body, "nomeProduto");
                String qtdStr = extrairCampo(body, "quantidadeSolicitada");
                String valorStr = extrairCampo(body, "valorUnitario");

                int quantidade = Integer.parseInt(qtdStr);
                double valorUnitario = Double.parseDouble(valorStr);

                Produto produto = new Produto(0, nomeProduto, 0.0, quantidade, valorUnitario);
                Pedido pedido = new Pedido(0, cpf);
                pedido.setStatus("Fechado");
                pedido.adicionarProdutoDireto(produto);

                pedido.salvarEmCSV("src/main/resources/Pedido.csv");
                ctx.status(201).result("Pedido salvo com sucesso");

            } catch (Exception e) {
                ctx.status(400).result("Erro ao processar pedido: " + e.getMessage());
            }
        });

        ///Consultar pedidos por CPF
        app.get("/pedidos/{cpf}", ctx -> {
            String cpf = ctx.pathParam("cpf");
            List<Pedido> pedidosOriginais = Pedido.buscarPedidosPorCpf(cpf);

            List<Map<String, Object>> pedidosFormatados = new ArrayList<>();
            for (Pedido p : pedidosOriginais) {
                Map<String, Object> pedidoMap = new HashMap<>();
                pedidoMap.put("idPedido", p.getIdPedido());
                pedidoMap.put("cpfCliente", p.getCpfCliente());
                pedidoMap.put("dataPedido", p.getDataPedido().toString());
                pedidoMap.put("status", p.getStatus());
                pedidoMap.put("valorTotal", p.calcularValorTotal());

                List<Map<String, Object>> produtosFormatados = new ArrayList<>();
                for (Produto prod : p.getProdutos()) {
                    Map<String, Object> prodMap = new HashMap<>();
                    prodMap.put("idPedido", prod.getIdPedido());
                    prodMap.put("nomeProduto", prod.getNomeProduto());
                    prodMap.put("pesoUnitarioProduto", prod.getPesoUnitarioProduto());
                    prodMap.put("quantidadeSolicitada", prod.getQuantidadeSolicitada());
                    prodMap.put("valorUnitario", prod.getValorUnitario());
                    produtosFormatados.add(prodMap);
                }

                pedidoMap.put("produtos", produtosFormatados);
                pedidosFormatados.add(pedidoMap);
            }

            ctx.result(pedidosFormatados.toString());
        });

        ///Consultar catálogo de produtos
        app.get("/produtos", ctx -> {
            List<Produto> catalogo = Produto.carregarCatalogoCSV("src/main/resources/produtos.csv");

            List<Map<String, Object>> produtosFormatados = new ArrayList<>();
            for (Produto p : catalogo) {
                Map<String, Object> produtoMap = new HashMap<>();

                produtoMap.put("nomeProduto", p.getNomeProduto());
                produtoMap.put("pesoUnitarioProduto", p.getPesoUnitarioProduto());
                produtoMap.put("valorUnitario", p.getValorUnitario());
                produtosFormatados.add(produtoMap);
            }

            ctx.result(produtosFormatados.toString());
        });
    }
    private static String extrairCampo(String json, String campo) throws Exception {
        String[] partes = json.split("\"" + campo + "\":");
        if (partes.length < 2) throw new Exception("Campo '" + campo + "' não encontrado.");
        String valorBruto = partes[1].trim();

        if (valorBruto.startsWith("\"")) {
            return valorBruto.split("\"")[1];
        } else {
            return valorBruto.split("[,}]")[0];
        }
    }
}