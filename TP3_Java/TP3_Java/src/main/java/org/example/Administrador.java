package org.example;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Administrador extends Usuario {
    private static final String CSV_PATH = "src/main/resources/usuarios.csv";

    public Administrador() {
        super();
    }

    public Administrador(int idUsuario, String nomeCompleto, String cpf, String numeroCelular, String email, String senha, String nivelAcesso, String cargo) {
        super(idUsuario, nomeCompleto, cpf, numeroCelular, email, senha, nivelAcesso, cargo);
    }

//metodos---------------------------------------------------------------------------------------------------------------

    public List<Usuario> lerUsuariosCSV() {
        List<Usuario> usuarios = new ArrayList<>();
        try (CSVReader leitor = new CSVReader(new FileReader(CSV_PATH))) {
            String[] linha;
            leitor.readNext();

            while ((linha = leitor.readNext()) != null) {
                Usuario u = new Usuario(
                        Integer.parseInt(linha[0]), // ID
                        linha[1], // nomeCompleto
                        linha[2], // CPF
                        linha[3], // númeroCelular
                        linha[4], // Email
                        linha[5], // Senha
                        linha[6], // nívelAcesso
                        linha[7]  // Cargo
                );
                usuarios.add(u);
            }

        } catch (Exception e) {
            System.out.println("Erro ao ler o CSV: " + e.getMessage());
        }
        return usuarios;
    }

    public void exibirUsuarios() {
        List<Usuario> usuarios = lerUsuariosCSV();
        for (Usuario u : usuarios) {
            System.out.println(u);
        }
    }

    public void removerUsuarioPorId(int idRemover) {
        List<String[]> usuariosAtualizados = new ArrayList<>();

        try (CSVReader leitor = new CSVReader(new FileReader(CSV_PATH))) {
            String[] linha;
            String[] cabecalho = leitor.readNext();
            usuariosAtualizados.add(cabecalho);

            while ((linha = leitor.readNext()) != null) {
                int id = Integer.parseInt(linha[0]);
                if (id != idRemover) {
                    usuariosAtualizados.add(linha);
                } 
            }

            try (CSVWriter escritor = new CSVWriter(new FileWriter(CSV_PATH))) {
                escritor.writeAll(usuariosAtualizados);
                System.out.println("Usuário com ID " + idRemover + " removido com sucesso.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao remover usuário: " + e.getMessage());
        }
        //proximamente irei desenvolver mais medidas para casos de uso inesperado do sistema!!!!!!
    }

}
