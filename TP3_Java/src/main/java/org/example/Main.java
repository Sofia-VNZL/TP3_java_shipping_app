package org.example;

public class Main {
    public static void main(String[] args) {
        Administrador admin = new Administrador();
        admin.exibirUsuarios();
        Usuario novo = new Usuario(
                6,
                "Lucas Oliveira",
                "55566677788",
                "11988776655",
                "lucas@email.com",
                "senha123",
                "cliente",
                "Comprador"
        );

        admin.adicionarUsuario(novo);
        admin.exibirUsuarios();

        admin.removerUsuarioPorId(6);

        admin.exibirUsuarios();
    }
}