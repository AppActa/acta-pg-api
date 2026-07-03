package br.com.acta.handler.exception;

import jakarta.persistence.EntityNotFoundException;

public class ModelNotFoundException extends EntityNotFoundException {
    public ModelNotFoundException(String classe) {
        super(classe + " não pode ser encontrado");
    }

    public ModelNotFoundException(String classe, Long id) {
        super(classe + " não pode ser encontrado com o ID " + id);
    }
}

