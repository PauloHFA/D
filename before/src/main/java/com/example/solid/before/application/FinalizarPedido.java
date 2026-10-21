package com.example.solid.before.application;

import com.example.solid.before.domain.Pedido;
import com.example.solid.before.infra.MySqlPedidoRepository;
import com.example.solid.before.infra.SmtpNotificador;

import java.math.BigDecimal;
import java.util.Objects;

public final class FinalizarPedido {
    private final MySqlPedidoRepository repositorio = new MySqlPedidoRepository();
    private final SmtpNotificador notificador = new SmtpNotificador();

    public ResultadoFinalizacao executar(Pedido pedido) {
        Objects.requireNonNull(pedido, "pedido não pode ser nulo");
        repositorio.salvar(pedido);
        notificador.notificar(pedido.cliente().email(), "Pedido confirmado");

        return new ResultadoFinalizacao(
                pedido.id(),
                pedido.total(),
                repositorio.contem(pedido.id()),
                notificador.notificou(pedido.cliente().email()),
                pedido.cliente().email()
        );
    }

    public record ResultadoFinalizacao(
            String pedidoId,
            BigDecimal total,
            boolean salvo,
            boolean notificado,
            String destinatario
    ) {
    }
}
