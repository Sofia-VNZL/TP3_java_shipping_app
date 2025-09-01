package org.example;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Administrador extends Usuario {
    public Administrador(int IDUsuario, String nomeCompleto, String cpf, String numeroCelular, String email, String senha, String nivelAcesso, String cargo) {
        super(IDUsuario, nomeCompleto, cpf, numeroCelular, email, senha, nivelAcesso, cargo);
    }

    // menu
    @Override
    public void mostrarMenu() {
        System.out.println("Menu do Administrador:");
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        do {
            System.out.println("\n-----MENU DO ADMINISTRADOR-----");
            System.out.println("1. Listar usuários");
            System.out.println("2. Editar usuário");
            System.out.println("3. Remover usuário");
            System.out.println("4. Cadastrar novo usuário");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1:
                    exibirUsuarios();
                    break;
                case 2:
                    editarUsuario(null);
                    break;
                case 3:
                    System.out.print("Digite o ID do usuário que deseja remover: ");
                    int idRemover = Integer.parseInt(scanner.nextLine());
                    removerUsuarioPorId(idRemover);
                    break;
                case 4:
                    Usuario novoUsuario = new Cliente();
                    novoUsuario.cadastrar();
                    break;
                case 0:
                    System.out.println("Encerrando sessão do administrador...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (opcao != 0);
    }



//metodos principais---------------------------------------------------------------------------------------------------------------



    public void exibirUsuarios() {
        List<Usuario> usuarios = Util.lerUsuariosCSV();
        for (Usuario u : usuarios) {
            System.out.println(u);
        }
    }

    public void removerUsuarioPorId(int idRemover) {
        List<String[]> usuariosAtualizados = new ArrayList<>();

        try (CSVReader leitor = new CSVReader(new FileReader(Util.CSV_PATH))) {
            String[] linha;
            String[] cabecalho = leitor.readNext();
            usuariosAtualizados.add(cabecalho);

            while ((linha = leitor.readNext()) != null) {
                int id = Integer.parseInt(linha[0]);
                if (id != idRemover) {

                    usuariosAtualizados.add(linha);
                } 
            }

            try (CSVWriter escritor = new CSVWriter(new FileWriter(Util.CSV_PATH))) {
                escritor.writeAll(usuariosAtualizados);
                System.out.println("Usuário com ID " + idRemover + " removido com sucesso");
            }

        } catch (Exception e) {
            System.out.println("Erro ao remover usuário: " + e.getMessage());
        }

    }



    public void editarUsuario(Usuario usuario) {
        Scanner scanner = new Scanner(System.in);
        List<String[]> usuariosAtualizados = new ArrayList<>();

        try (CSVReader leitor = new CSVReader(new FileReader(Util.CSV_PATH))) {
            String[] cabecalho = leitor.readNext();
            usuariosAtualizados.add(cabecalho);

            List<String[]> linhas = leitor.readAll();
            boolean encontrado = false;

            System.out.print("Digite o ID do usuário que deseja editar: ");
            int idEditar = Integer.parseInt(scanner.nextLine());

            for (String[] linha : linhas) {
                int idAtual = Integer.parseInt(linha[0]);

                if (idAtual == idEditar) {
                    encontrado = true;

                    System.out.println("Usuário encontrado:");
                    System.out.println("1. Nome completo: " + linha[1]);
                    System.out.println("2. CPF: " + linha[2]);
                    System.out.println("3. Número de celular: " + linha[3]);
                    System.out.println("4. Email: " + linha[4]);
                    System.out.println("5. Senha: " + linha[5]);
                    System.out.println("6. Nível de acesso: " + linha[6]);
                    System.out.println("7. Cargo: " + linha[7]);

                    System.out.print("Digite o número do campo que deseja editar: ");
                    int campo = Integer.parseInt(scanner.nextLine());

                    System.out.print("Digite o novo valor: ");
                    String novoValor = scanner.nextLine();

                    linha[campo] = novoValor;

                    System.out.println("Campo atualizado com sucesso!");
                }

                usuariosAtualizados.add(linha);
            }

            if (!encontrado) {
                System.out.println("Nenhum usuário com o ID informado foi encontrado");
                return;
            }

            try (CSVWriter escritor = new CSVWriter(new FileWriter(Util.CSV_PATH))) {
                escritor.writeAll(usuariosAtualizados);
                System.out.println("Arquivo CSV atualizado com sucesso");
            }

        } catch (Exception e) {
            System.out.println("Erro ao editar usuário: " + e.getMessage());
        }
    }
}
