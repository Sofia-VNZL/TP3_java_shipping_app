package org.example;

import com.opencsv.CSVReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    public int getIdPedido() {
        return idPedido;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public double getPesoUnitarioProduto() {
        return pesoUnitarioProduto;
    }

    public int getQuantidadeSolicitada() {
        return quantidadeSolicitada;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public String toCSV() {
        return idPedido + "," +
                nomeProduto + "," +
                pesoUnitarioProduto + "," +
                quantidadeSolicitada + "," +
                valorUnitario;
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

    public static List<Produto> carregarCatalogoCSV(String caminho) {
        List<Produto> produtos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            br.readLine();
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                String nome = partes[1];
                double peso = Double.parseDouble(partes[2]);
                int qtd = Integer.parseInt(partes[3]);
                double valor = Double.parseDouble(partes[4]);
                produtos.add(new Produto(0, nome, peso, qtd, valor));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return produtos;
    }



    public void salvarEmCSV(String caminhoArquivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(caminhoArquivo, true))) {
            writer.println(idPedido + "," +
                    nomeProduto + "," +
                    pesoUnitarioProduto + "," +
                    quantidadeSolicitada + "," +
                    valorUnitario);
            System.out.println("Produto " + nomeProduto + " salvo em " + caminhoArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar produto: " + e.getMessage());
        }
    }

}