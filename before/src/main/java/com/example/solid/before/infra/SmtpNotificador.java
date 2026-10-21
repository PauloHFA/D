package com.example.solid.before.infra;

import java.util.ArrayList;
import java.util.List;

public final class SmtpNotificador {
    private final List<String> destinatariosNotificados = new ArrayList<>();

    public void notificar(String destinatario, String mensagem) {
        destinatariosNotificados.add(destinatario);
    }

    public boolean notificou(String destinatario) {
        return destinatariosNotificados.contains(destinatario);
    }
}
