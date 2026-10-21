package com.example.solid.before;

import com.example.solid.before.application.FinalizarPedido;
import com.example.solid.before.domain.Cliente;
import com.example.solid.before.domain.Item;
import com.example.solid.before.domain.Pedido;

import java.math.BigDecimal;
import java.util.List;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        Pedido pedido = new Pedido(
                "PED-001",
                new Cliente("Ana", "ana@example.com"),
                List.of(new Item("Livro", new BigDecimal("50.00"), 2))
        );

        FinalizarPedido.ResultadoFinalizacao resultado =
                new FinalizarPedido().executar(pedido);
        System.out.printf(
                "Pedido %s finalizado: total=%s, salvo=%s, notificado=%s%n",
                resultado.pedidoId(),
                resultado.total(),
                resultado.salvo(),
                resultado.notificado()
        );
    }
}
