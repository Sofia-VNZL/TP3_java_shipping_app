package org.example;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Objects;
import java.util.Scanner;

public abstract class Usuario {
    private int IDUsuario;
    private String nomeCompleto;
    private String cpf;
    private String numeroCelular;
    private String email;
    private String senha;
    private String nivelAcesso;
    private String cargo;

    public Usuario() {
    }

    public Usuario(int IDUsuario, String nomeCompleto, String cpf, String numeroCelular, String email, String senha, String nivelAcesso, String cargo) {
        this.IDUsuario = IDUsuario;
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.numeroCelular = numeroCelular;
        this.email = email;
        this.senha = senha;
        this.nivelAcesso = nivelAcesso;
        this.cargo = cargo;
    }

    //getters e setters!!!!
    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public int getIDUsuario() {
        return IDUsuario;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getNivelAcesso() {
        return nivelAcesso;
    }

    public String getCargo() {
        return cargo;
    }

    public void setIDUsuario(int IDUsuario) {
        this.IDUsuario = IDUsuario;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setNivelAcesso(String nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return "ID: " + IDUsuario +
                ", Nome: " + nomeCompleto +
                ", CPF: " + cpf +
                ", Celular: " + numeroCelular +
                ", Email: " + email +
                ", Nível de Acesso: " + nivelAcesso +
                ", Cargo: " + cargo;
    }

    //metodos----------------------------------------------------------------------
    public abstract void mostrarMenu();

    public void cadastrar() {
        Scanner scanner = new Scanner(System.in);
        this.IDUsuario = Util.calcularProximoID();


        System.out.println("-----Cadastro de Usuário-----");

        System.out.print("Nome completo: ");
        this.nomeCompleto = scanner.nextLine();

        System.out.print("CPF: ");
        this.cpf = scanner.nextLine();

        System.out.print("Número de celular: ");
        this.numeroCelular = scanner.nextLine();

        System.out.print("Email: ");
        this.email = scanner.nextLine();

        System.out.print("Senha: ");
        this.senha = scanner.nextLine();

        System.out.print("Nível de acesso: Cliente (solicitar verificação) ");
        this.nivelAcesso = "Cliente";

        System.out.print("Cargo: ");
        this.cargo = scanner.nextLine();

        // Salvando no CSV
        try (CSVWriter writer = new CSVWriter(new FileWriter(Util.CSV_PATH, true))) {
            String[] novaLinha = {
                    String.valueOf(IDUsuario),
                    nomeCompleto,
                    cpf,
                    numeroCelular,
                    email,
                    senha,
                    nivelAcesso,
                    cargo
            };
            writer.writeNext(novaLinha);
            System.out.println("Cadastro realizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    public static Usuario login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("-----Login-----");
        System.out.print("Digite seu CPF (com pontos e traço) ou seu e-mail: ");
        String identificador = scanner.nextLine().trim();

        System.out.print("Digite sua senha: ");
        String senhaDigitada = scanner.nextLine().trim();

        try (CSVReader leitor = new CSVReader(new FileReader(Util.CSV_PATH))) {
            leitor.readNext();
            String[] linha;

            while ((linha = leitor.readNext()) != null) {
                String cpf = linha[2];
                String email = linha[4];
                String senha = linha[5];

                if ((identificador.equalsIgnoreCase(cpf) || identificador.equalsIgnoreCase(email)) &&
                        senhaDigitada.equals(senha)) {

                    Usuario usuarioLogado;

                    switch (linha[6]) { // linha[6] é o nível de acesso
                        case "Cliente":
                            usuarioLogado = new Cliente(
                                    Integer.parseInt(linha[0]), linha[1], linha[2], linha[3],
                                    linha[4], linha[5], linha[6], linha[7]
                            );
                            break;
                        case "Administrador":
                            usuarioLogado = new Administrador(
                                    Integer.parseInt(linha[0]), linha[1], linha[2], linha[3],
                                    linha[4], linha[5], linha[6], linha[7]
                            );
                            break;
                        default:
                            System.out.println("Nível de acesso desconhecido.");
                            return null;
                    }


                    System.out.println("Login realizado com sucesso! Bem-vindo(a), " + usuarioLogado.getNomeCompleto());
                    System.out.println("Nível de acesso: " + usuarioLogado.getNivelAcesso());
                    usuarioLogado.mostrarMenu();

                    return usuarioLogado;
                }
            }

            System.out.println("CPF/E-mail ou senha incorretos");
        } catch (Exception e) {
            System.out.println("Erro ao realizar login: " + e.getMessage());
        }

        return null;
    }

}


