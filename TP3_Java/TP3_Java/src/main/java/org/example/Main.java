package org.example;

public class Main {
    public static void main(String[] args) {

        Thread servidorThread = new Thread(() -> Servidor.main(null));
        servidorThread.start();


        try {
            Thread.sleep(1000); // 1 segundo
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try {
            ClienteCLI.main(null);
        } catch (Exception e) {
            System.out.println("Erro ao iniciar cliente: " + e.getMessage());
        }
    }
}