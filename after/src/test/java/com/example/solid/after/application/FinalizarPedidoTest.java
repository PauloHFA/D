package com.example.solid.after.application;

import com.example.solid.after.domain.Cliente;
import com.example.solid.after.domain.Item;
import com.example.solid.after.domain.Pedido;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FinalizarPedidoTest {
    @Test
    void executar_salvaPedidoENotificaCliente_usandoFakesSemInfraestrutura() {
        Map<String, Pedido> pedidosSalvos = new HashMap<>();
        List<String> destinatariosNotificados = new ArrayList<>();
        PedidoRepository repositorioFalso = pedido -> pedidosSalvos.put(pedido.id(), pedido);
        Notificador notificadorFalso = (destinatario, mensagem) ->
                destinatariosNotificados.add(destinatario + ":" + mensagem);
        Pedido pedido = pedidoExemplo();

        FinalizarPedido.ResultadoFinalizacao resultado =
                new FinalizarPedido(repositorioFalso, notificadorFalso).executar(pedido);

        assertEquals(pedido, pedidosSalvos.get("PED-TESTE"));
        assertEquals(List.of("ana@example.com:Pedido confirmado"), destinatariosNotificados);
        assertEquals("PED-TESTE", resultado.pedidoId());
        assertEquals(new BigDecimal("100.00"), resultado.total());
    }

    @Test
    void construtor_rejeitaDependenciasAusentes() {
        assertThrows(
                NullPointerException.class,
                () -> new FinalizarPedido(null, (destinatario, mensagem) -> { })
        );
        assertThrows(
                NullPointerException.class,
                () -> new FinalizarPedido(pedido -> { }, null)
        );
    }

    private Pedido pedidoExemplo() {
        return new Pedido(
                "PED-TESTE",
                new Cliente("Ana", "ana@example.com"),
                List.of(new Item("Livro", new BigDecimal("50.00"), 2))
        );
    }
}
