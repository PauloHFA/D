package com.example.solid.after.infra;

import com.example.solid.after.application.Notificador;

public final class ConsoleNotificador implements Notificador {
    @Override
    public void notificar(String destinatario, String mensagem) {
        System.out.printf("Notificação para %s: %s%n", destinatario, mensagem);
    }
}
