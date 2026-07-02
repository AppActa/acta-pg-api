package br.com.acta.entity.base;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

@MappedSuperclass
@EqualsAndHashCode(of = "id")
public abstract class ModelBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}