package com.example.solid.after;

import com.example.solid.after.application.FinalizarPedido;
import com.example.solid.after.application.Notificador;
import com.example.solid.after.application.PedidoRepository;
import com.example.solid.after.domain.Cliente;
import com.example.solid.after.domain.Item;
import com.example.solid.after.domain.Pedido;
import com.example.solid.after.infra.ConsoleNotificador;
import com.example.solid.after.infra.InMemoryPedidoRepository;

import java.math.BigDecimal;
import java.util.List;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        PedidoRepository repositorio = new InMemoryPedidoRepository();
        Notificador notificador = new ConsoleNotificador();
        FinalizarPedido finalizarPedido = new FinalizarPedido(repositorio, notificador);
        Pedido pedido = new Pedido(
                "PED-001",
                new Cliente("Ana", "ana@example.com"),
                List.of(new Item("Livro", new BigDecimal("50.00"), 2))
        );

        FinalizarPedido.ResultadoFinalizacao resultado = finalizarPedido.executar(pedido);
        System.out.printf(
                "Pedido %s finalizado: total=%s, salvo=%s%n",
                resultado.pedidoId(),
                resultado.total(),
                resultado.salvo()
        );
    }
}
