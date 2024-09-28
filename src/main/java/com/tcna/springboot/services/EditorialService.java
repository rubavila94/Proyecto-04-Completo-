package com.tcna.springboot.services;

import java.util.Optional;
import com.tcna.springboot.entities.Editorial;
import java.util.List;

public interface EditorialService {

    Editorial guardarEditorial(Editorial editorial);

    Optional<Editorial> buscarPorId(Long id);

    Optional<Editorial> buscarPorNombre(String nombre);

    List<Editorial> listarTodasEditoriales();

    Editorial actualizarEditorial(Editorial editorial);

    void eliminarEditorial(Long id);

}
