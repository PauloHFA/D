package com.example.solid.after.infra;

import com.example.solid.after.application.PedidoRepository;
import com.example.solid.after.domain.Pedido;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public final class ArquivoPedidoRepository implements PedidoRepository {
    private final Path arquivo;

    public ArquivoPedidoRepository(Path arquivo) {
        this.arquivo = arquivo;
    }

    @Override
    public void salvar(Pedido pedido) {
        try {
            Files.writeString(
                    arquivo,
                    pedido.id() + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException exception) {
            throw new IllegalStateException("Não foi possível salvar o pedido em " + arquivo, exception);
        }
    }
}
