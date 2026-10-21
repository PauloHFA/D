package com.example.solid.after.infra;

import com.example.solid.after.domain.Cliente;
import com.example.solid.after.domain.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArquivoPedidoRepositoryTest {
    @Test
    void salvar_persisteIdentificadorNoArquivo(@TempDir Path diretorio) throws IOException {
        Path arquivo = diretorio.resolve("pedidos.txt");
        ArquivoPedidoRepository repositorio = new ArquivoPedidoRepository(arquivo);
        Pedido pedido = new Pedido("PED-ARQUIVO-001", new Cliente("Ana", "ana@example.com"), List.of());

        repositorio.salvar(pedido);

        assertEquals("PED-ARQUIVO-001" + System.lineSeparator(), Files.readString(arquivo));
    }
}
