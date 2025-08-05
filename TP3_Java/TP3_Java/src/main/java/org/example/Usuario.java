package org.example;

import java.util.Objects;

public class Usuario {
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
}


