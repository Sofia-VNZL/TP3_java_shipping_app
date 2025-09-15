package org.example;

public class Main {
    public static void main(String[] args) {

        System.out.println("-----Sistema de Pedidos-----");
        Usuario usuarioLogado = Usuario.login();

        if (usuarioLogado == null) {
            System.out.println("Encerrando o sistema... ");
        }


    }
}