package com.tcna.springboot.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.tcna.springboot.entities.Editorial;
import com.tcna.springboot.services.EditorialService;
import com.tcna.springboot.services.LibroService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/editoriales")
public class EditorialController {

    @Autowired
    private EditorialService editorialService;

    @Autowired
    private LibroService libroService;

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoEditorial(Model model) {
        model.addAttribute("editorial", new Editorial());
        return "editorial/formulario_editorial";
    }

    @PostMapping("/guardar")
    public String guardarEditorial(@ModelAttribute Editorial editorial) {
        Editorial editorialGuardada = editorialService.guardarEditorial(editorial);
        return "redirect:/editoriales/listar";
    }

    // Mostrar todas las editoriales
    @GetMapping({ "/listar", "/" })
    public String listarEditoriales(Model model) {
        List<Editorial> editoriales = editorialService.listarTodasEditoriales();
        model.addAttribute("editoriales", editoriales);
        return "editorial/listar_editoriales";
    }

    // Mostrar una sola editorial
    @GetMapping("/{id}")
    public String mostrarEditorial(@PathVariable Long id, Model model) {
        Optional<Editorial> editorialOptional = editorialService.buscarPorId(id);
        if (editorialOptional.isPresent()) {
            Editorial editorial = editorialOptional.get();
            model.addAttribute("editorial", editorial);
            model.addAttribute("libros", editorial.getLibros());
        }
        return "editorial/mostrar_editorial";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarEditorial(@PathVariable Long id, Model model) {
        Optional<Editorial> editorial = editorialService.buscarPorId(id);
        editorial.ifPresent(value -> model.addAttribute("editorial", value));
        return "editorial/formulario_editorial";
    }

    @GetMapping("path")
    public String actualizarEditorial(@PathVariable Long id, @ModelAttribute Editorial editorial) {
        Optional<Editorial> editorialOptional = editorialService.buscarPorId(id);
        if (editorialOptional.isPresent()) {
            Editorial editorialActual = editorialOptional.get();
            editorialActual.setNombre(editorial.getNombre());
            editorialService.actualizarEditorial(editorialActual);
        }
        return "editoriales/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarEditorial(@PathVariable Long id) {
        editorialService.eliminarEditorial(id);
        return "redirect:/editoriales/listar";
    }

    // mostramos os libros de una editorial
    @GetMapping("/libros/{id}")
    public String mostrarLibrosDeEditorial(@PathVariable Long id, Model model) {
        Optional<Editorial> editorialOptional = editorialService.buscarPorId(id);
        if (editorialOptional.isPresent()) {
            Editorial editorial = editorialOptional.get();
            model.addAttribute("editorial", editorial);
            model.addAttribute("libros", editorial.getLibros());
        }
        return "editorial/mostrar_libros_editorial";
    }

}
