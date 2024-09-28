package com.tcna.springboot.services.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tcna.springboot.entities.Editorial;
import com.tcna.springboot.repositories.EditorialRepository;
import com.tcna.springboot.services.EditorialService;

@Service
public class EditorialServiceImpl implements EditorialService{
    // Inyectamos el repositorio
    @Autowired
    private EditorialRepository editorialRepository;

    @Override
    public Editorial guardarEditorial(Editorial editorial) {
        return editorialRepository.save(editorial);
    }

    @Override
    public Optional<Editorial> buscarPorId(Long id) {
        return editorialRepository.findById(id);
    }

    @Override
    public Optional<Editorial> buscarPorNombre(String nombre) {
        return editorialRepository.findByNombre(nombre);
    }

    @Override
    public List<Editorial> listarTodasEditoriales() {
        return editorialRepository.findAll();
    }

    @Override
    public Editorial actualizarEditorial(Editorial editorial) {
        return editorialRepository.save(editorial);
    }

    @Override
    public void eliminarEditorial(Long id) {
        editorialRepository.deleteById(id);
    }

}
