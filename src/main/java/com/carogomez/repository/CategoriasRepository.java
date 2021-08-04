package com.carogomez.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carogomez.model.Categoria;

public interface CategoriasRepository extends JpaRepository<Categoria, Integer> {

}
