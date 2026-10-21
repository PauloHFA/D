package com.example.solid.after.application;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;

class DependenciasCamadasTest {
    @Test
    void dominioEAplicacao_naoImportamInfraestrutura() throws IOException {
        Path raiz = Path.of("src/main/java/com/example/solid/after");
        for (String camada : new String[]{"domain", "application"}) {
            try (var arquivos = Files.walk(raiz.resolve(camada))) {
                arquivos.filter(arquivo -> arquivo.toString().endsWith(".java"))
                        .forEach(arquivo -> assertFalse(
                                lerArquivo(arquivo).contains("com.example.solid.after.infra"),
                                () -> arquivo + " não deve depender de infra"
                        ));
            }
        }
    }

    private String lerArquivo(Path arquivo) {
        try {
            return Files.readString(arquivo);
        } catch (IOException exception) {
            throw new IllegalStateException("Não foi possível ler " + arquivo, exception);
        }
    }
}
