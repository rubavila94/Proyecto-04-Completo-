package com.tcna.springboot.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tcna.springboot.entities.Categoria;
import com.tcna.springboot.entities.Libro;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByTitulo(String titulo);

    // Buscara todos los libros dentro de una categoria
    List<Libro> findByCategoria(Categoria categoria);

}
