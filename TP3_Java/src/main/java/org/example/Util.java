package org.example;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


class Util {
    public static final String CSV_PATH = "src/main/resources/usuarios.csv";

    public static int calcularProximoID() {
        int maiorID = 0;

        try (CSVReader leitor = new CSVReader(new FileReader(CSV_PATH))) {
            String[] linha;
            leitor.readNext();

            while ((linha = leitor.readNext()) != null) {
                int idAtual = Integer.parseInt(linha[0]);
                if (idAtual > maiorID) {
                    maiorID = idAtual;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao calcular o próximo ID: " + e.getMessage());
        }

        return maiorID + 1;
    }

    public static void notificacao(String mensagem) {
        System.out.println(mensagem);
    }

    public static List<Usuario> lerUsuariosCSV() {
        List<Usuario> usuarios = new ArrayList<>();
        try (CSVReader leitor = new CSVReader(new FileReader(Util.CSV_PATH))) {
            String[] linha;
            leitor.readNext();

            while ((linha = leitor.readNext()) != null) {

                int id = Integer.parseInt(linha[0]);// ID
                String nome = linha[1];
                String cpf = linha[2];
                String celular = linha[3];
                String email = linha[4];
                String senha = linha[5];
                String nivel = linha[6];
                String cargo = linha[7];

                Usuario u = new Cliente(id, nome, cpf, celular, email, senha, nivel, cargo);

                usuarios.add(u);

            }

        } catch (Exception e) {
            System.out.println("Erro ao ler o CSV: " + e.getMessage());
        }
        return usuarios;
    }
}
