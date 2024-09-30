package com.tcna.springboot.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tcna.springboot.entities.Categoria;
import com.tcna.springboot.entities.Libro;
import com.tcna.springboot.services.CategoriaService;
import com.tcna.springboot.services.LibroService;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/autores")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private LibroService libroService;

    @GetMapping({ "/listar", "/" })
    public String listarCategorias(Model model) {
        List<Categoria> categorias = categoriaService.listarTodasCategorias();
        model.addAttribute("categorias", categorias);
        return "categoria/listar_categorias";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevaCategoria(Model model) {
        Categoria categoria = new Categoria();
        model.addAttribute("categoria", categoria);
        return "categoria/formulario_categorias";
    }

    @PostMapping("/guardar")
    public String guardarCategoria(@ModelAttribute Categoria categoria) {
        Categoria categoriaGuardada = categoriaService.guardarCategoria(categoria);
        List<Libro> libros = libroService.buscarPorCategoria(categoriaGuardada);
        categoriaGuardada.setLibros(libros);
        categoriaService.guardarCategoria(categoria);
        return "redirect:/categorias/listar";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditarCategoria(@PathVariable Long id, Model model) {
        Optional<Categoria> categoria = categoriaService.buscarPorId(id);
        categoria.ifPresent(value -> model.addAttribute("categoria", value));
        return "categoria/formulario_categorias";
    }

    @PostMapping("{id}/actualizar")
    public String actualizarCategoria(@PathVariable Long id, @ModelAttribute Categoria categoria) {
        Categoria categoriaActual = categoriaService.buscarPorId(id).orElse(null);

        if (categoriaActual != null) {
            categoria.setLibros(categoriaActual.getLibros());
            categoriaService.actualizarCategoria(categoria);
        }
        return "redirect:/categorias/listar";
    }

    @GetMapping
    public String eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return "redirect:/categorias/listar";
    }

}
