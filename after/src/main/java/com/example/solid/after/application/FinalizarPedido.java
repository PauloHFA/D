package com.example.solid.after.application;

import com.example.solid.after.domain.Pedido;

import java.math.BigDecimal;
import java.util.Objects;

public final class FinalizarPedido {
    private final PedidoRepository repositorio;
    private final Notificador notificador;

    public FinalizarPedido(PedidoRepository repositorio, Notificador notificador) {
        this.repositorio = Objects.requireNonNull(repositorio, "repositorio não pode ser nulo");
        this.notificador = Objects.requireNonNull(notificador, "notificador não pode ser nulo");
    }

    public ResultadoFinalizacao executar(Pedido pedido) {
        Objects.requireNonNull(pedido, "pedido não pode ser nulo");
        repositorio.salvar(pedido);
        notificador.notificar(pedido.cliente().email(), "Pedido confirmado");

        return new ResultadoFinalizacao(
                pedido.id(),
                pedido.total(),
                true,
                pedido.cliente().email()
        );
    }

    public record ResultadoFinalizacao(
            String pedidoId,
            BigDecimal total,
            boolean salvo,
            String destinatario
    ) {
    }
}
