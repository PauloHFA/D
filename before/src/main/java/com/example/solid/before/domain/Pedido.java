package com.example.solid.before.domain;

import java.math.BigDecimal;
import java.util.List;

public record Pedido(String id, Cliente cliente, List<Item> itens) {
    public Pedido {
        itens = List.copyOf(itens);
    }

    public BigDecimal total() {
        return itens.stream()
                .map(Item::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
