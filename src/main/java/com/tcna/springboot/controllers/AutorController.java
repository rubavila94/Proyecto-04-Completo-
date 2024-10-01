package com.tcna.springboot.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.tcna.springboot.entities.Autor;
import com.tcna.springboot.services.AutorService;

@Controller
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @GetMapping({ "/listar", "/" })
    public String listarAutores(Model model) {
        List<Autor> autores = autorService.listarTodosLosAutores();
        model.addAttribute("autores", autores);
        return "autor/lista_autores";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoAutor(Model model) {
        model.addAttribute("autor", new Autor());
        return "autor/formulario_autor";
    }

    @PostMapping("/guardar")
    public String guardarAutor(@ModelAttribute Autor autor) {
        autorService.guardarAutor(autor);
        return "redirect:/autores/listar";
    }

    // Formulario para mostrar el formulario de editar un autor
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarAutor(@PathVariable Long id, Model model) {
        Optional<Autor> autor = autorService.buscarPorId(id);
        autor.ifPresent(value -> model.addAttribute("autor", value));
        return "autor/formulario_autor";
    }

    // Metodo para editar directamente un autor
    @PostMapping("/actualizar")
    public String actualizarAutor(@ModelAttribute Autor autor) {
        autorService.actualizaAutor(autor);
        return "redirect:/autores/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarAutor(@PathVariable Long id) throws ClassNotFoundException {
        autorService.eliminarAutor(id);
        return "redirect:/autores/listar";
    }
}
