package com.tcna.springboot.services.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tcna.springboot.entities.Autor;
import com.tcna.springboot.entities.Libro;
import com.tcna.springboot.repositories.AutorRepository;
import com.tcna.springboot.services.AutorService;

@Service
public class AutorServiceImpl implements AutorService {
    // Inyectamos el repositorio
    @Autowired
    private AutorRepository autorRepository;

    @Override
    public Autor guardarAutor(Autor autor) {
        return autorRepository.save(autor);
    }

    @Override
    public Optional<Autor> buscarPorId(Long id) {
        return autorRepository.findById(id);
    }

    @Override
    public Optional<Autor> buscarPorNombre(String nombre) {
        return autorRepository.findByNombre(nombre);
    }

    @Override
    public List<Autor> listarTodosLosAutores() {
        return autorRepository.findAll();
    }

    @Override
    public Autor actualizaAutor(Autor autor) {
        return autorRepository.save(autor);

    }

    @Override
    public void eliminarAutor(Long id) throws ClassNotFoundException {
        // autorRepository.deleteById(id);
        Optional<Autor> optionalAutor = autorRepository.findById(id);

        if (optionalAutor.isPresent()) {
            Autor autor = optionalAutor.get();
            eliminarRelacionesDeAutor(autor);
            autorRepository.deleteById(id);
        } else {
            throw new ClassNotFoundException("Error");
        }
    }

    @Override
    public List<Autor> buscarPorIds(List<Long> ids) {
        return autorRepository.findAllById(ids);
    }

    // Metodo personalizado
    private void eliminarRelacionesDeAutor(Autor autor) {
        for (Libro libro : autor.getLibros()) {
            libro.getAutores().remove(autor);
        }
        autor.getLibros().clear();
    }

}
