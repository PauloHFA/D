package com.example.solid.after.infra;

import com.example.solid.after.application.PedidoRepository;
import com.example.solid.after.domain.Pedido;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class InMemoryPedidoRepository implements PedidoRepository {
    private final Map<String, Pedido> pedidos = new HashMap<>();

    @Override
    public void salvar(Pedido pedido) {
        pedidos.put(pedido.id(), pedido);
    }

    public Optional<Pedido> buscar(String pedidoId) {
        return Optional.ofNullable(pedidos.get(pedidoId));
    }
}
