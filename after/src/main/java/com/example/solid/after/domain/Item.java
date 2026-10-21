package com.example.solid.after.domain;

import java.math.BigDecimal;

public record Item(String nome, BigDecimal preco, int quantidade) {
    public BigDecimal subtotal() {
        return preco.multiply(BigDecimal.valueOf(quantidade));
    }
}
