package org.example;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

    static List<Produto> carregarCatalogoCSV(String caminhoCSV) {
        List<Produto> catalogo = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(caminhoCSV))) {
            reader.readNext();

            String[] linha;
            while ((linha = reader.readNext()) != null) {
                Produto p = new Produto(
                        Integer.parseInt(linha[0]),
                        linha[1],
                        Double.parseDouble(linha[2]),
                        Integer.parseInt(linha[3]),
                        Double.parseDouble(linha[4])
                );
                catalogo.add(p);
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar catálogo de produtos: " + e.getMessage());
        }
        return catalogo;
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
