package org.example;

public class Main {
    public static void main(String[] args) {
        Administrador admin = new Administrador();
        admin.exibirUsuarios();

        admin.removerUsuarioPorId(2);

        admin.exibirUsuarios();
    }
}