package com.example.solid.before.infra;

import com.example.solid.before.domain.Pedido;

import java.util.HashMap;
import java.util.Map;

public final class MySqlPedidoRepository {
    private final Map<String, Pedido> pedidos = new HashMap<>();

    public void salvar(Pedido pedido) {
        pedidos.put(pedido.id(), pedido);
    }

    public boolean contem(String pedidoId) {
        return pedidos.containsKey(pedidoId);
    }
}
