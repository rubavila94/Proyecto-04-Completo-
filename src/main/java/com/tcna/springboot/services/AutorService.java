package com.tcna.springboot.services;

import com.tcna.springboot.entities.Autor;
import java.util.Optional;
import java.util.List;

public interface AutorService {

    Autor guardarAutor(Autor autor);

    Optional<Autor> buscarPorId(Long id);

    Optional<Autor> buscarPorNombre(String nombre);

    List<Autor> listarTodosLosAutores();

    Autor actualizaLibro(Autor autor);

    void eliminarAutor(Long id) throws ClassNotFoundException;

    List<Autor> buscarPorIds(List<Long> ids);
}
