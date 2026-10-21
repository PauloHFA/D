package com.example.solid.acceptance;

import com.example.solid.after.application.Notificador;
import com.example.solid.after.infra.InMemoryPedidoRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PedidoAcceptanceTest {
    @ParameterizedTest(name = "{0}: finaliza pedido, salva e notifica o cliente")
    @EnumSource(Versao.class)
    void finalizarPedido_deveSalvarENotificarComMesmoComportamento(Versao versao) {
        Resultado resultado = executarFluxo(versao);

        assertEquals("PED-ACEITE-001", resultado.pedidoId());
        assertEquals(new BigDecimal("100.00"), resultado.total());
        assertTrue(resultado.salvo());
        assertEquals("ana@example.com", resultado.destinatario());
    }

    private Resultado executarFluxo(Versao versao) {
        return switch (versao) {
            case BEFORE -> executarBefore();
            case AFTER -> executarAfter();
        };
    }

    private Resultado executarBefore() {
        com.example.solid.before.domain.Pedido pedido =
                new com.example.solid.before.domain.Pedido(
                        "PED-ACEITE-001",
                        new com.example.solid.before.domain.Cliente("Ana", "ana@example.com"),
                        java.util.List.of(new com.example.solid.before.domain.Item(
                                "Livro", new BigDecimal("50.00"), 2
                        ))
                );
        var resultado = new com.example.solid.before.application.FinalizarPedido().executar(pedido);

        assertTrue(resultado.notificado());
        return new Resultado(
                resultado.pedidoId(),
                resultado.total(),
                resultado.salvo(),
                resultado.destinatario()
        );
    }

    private Resultado executarAfter() {
        com.example.solid.after.domain.Pedido pedido =
                new com.example.solid.after.domain.Pedido(
                        "PED-ACEITE-001",
                        new com.example.solid.after.domain.Cliente("Ana", "ana@example.com"),
                        java.util.List.of(new com.example.solid.after.domain.Item(
                                "Livro", new BigDecimal("50.00"), 2
                        ))
                );
        var repositorio = new InMemoryPedidoRepository();
        var destinatariosNotificados = new java.util.ArrayList<String>();
        Notificador notificadorFalso = (destinatario, mensagem) ->
                destinatariosNotificados.add(destinatario + ":" + mensagem);
        var resultado = new com.example.solid.after.application.FinalizarPedido(
                repositorio,
                notificadorFalso
        ).executar(pedido);

        assertEquals(java.util.List.of("ana@example.com:Pedido confirmado"), destinatariosNotificados);
        assertEquals(
                pedido,
                repositorio.buscar("PED-ACEITE-001").orElseThrow()
        );
        return new Resultado(
                resultado.pedidoId(),
                resultado.total(),
                resultado.salvo(),
                resultado.destinatario()
        );
    }

    private enum Versao {
        BEFORE,
        AFTER
    }

    private record Resultado(
            String pedidoId,
            BigDecimal total,
            boolean salvo,
            String destinatario
    ) {
    }
}
