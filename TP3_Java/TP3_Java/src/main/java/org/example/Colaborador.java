package org.example;

public class Colaborador extends Usuario  {
    public Colaborador(int IDUsuario, String nomeCompleto, String cpf, String numeroCelular, String email, String senha, String nivelAcesso, String cargo) {
        super(IDUsuario, nomeCompleto, cpf, numeroCelular, email, senha, nivelAcesso, cargo);
    }

    @Override
    public void mostrarMenu() {

    }
}
