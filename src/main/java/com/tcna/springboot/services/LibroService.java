package com.tcna.springboot.services;

import com.tcna.springboot.entities.Categoria;
import com.tcna.springboot.entities.Libro;
import java.util.Optional;
import java.util.List;

public interface LibroService {

    Libro guardarLibro(Libro libro);

    Optional<Libro> buscarPorId(Long id);

    Optional<Libro> buscarPorTitulo(String titulo);

    List<Libro> listarTodosLosLibros();

    Libro actualizaLibro(Libro libro);

    void eliminarLibro(Long id);

    List<Libro> buscarPorCategoria(Categoria categoria);
}
