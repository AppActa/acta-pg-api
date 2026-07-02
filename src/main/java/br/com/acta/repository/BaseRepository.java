package br.com.acta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<ENT> extends JpaRepository<ENT, Long> {
}
