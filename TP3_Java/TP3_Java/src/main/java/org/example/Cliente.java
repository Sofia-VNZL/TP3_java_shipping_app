package org.example;

public class Cliente extends Usuario {
    public Cliente(int IDUsuario, String nomeCompleto, String cpf, String numeroCelular, String email, String senha, String nivelAcesso, String cargo) {
        super(IDUsuario, nomeCompleto, cpf, numeroCelular, email, senha, nivelAcesso, cargo);
    }
    public Cliente() {
        super(0, "Novo Cliente", "000.000.000-00", "000000000", "email@exemplo.com", "senha", "cliente", "cargo");
    }

    @Override
    public void mostrarMenu() {
        System.out.println("Menu do Cliente:");

    }
}