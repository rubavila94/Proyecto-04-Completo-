package com.tcna.springboot.controllers;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.tcna.springboot.entities.Autor;
import com.tcna.springboot.entities.Categoria;
import com.tcna.springboot.entities.Editorial;
import com.tcna.springboot.entities.Libro;
import com.tcna.springboot.services.AutorService;
import com.tcna.springboot.services.CategoriaService;
import com.tcna.springboot.services.EditorialService;
import com.tcna.springboot.services.LibroService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//Este controlador es el mas largo porque va a utilizar
//todas los demas controladores
@Controller
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @Autowired
    private AutorService autorService;

    @Autowired
    private EditorialService editorialService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping({ "/listar", "/" })
    public String listarLibros(Model model) {
        List<Libro> libros = libroService.listarTodosLosLibros();
        model.addAttribute("Libros", libros);
        return "libro/listar_libros";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoLibro(Model model) {
        Libro libro = new Libro();
        model.addAttribute("libro", libro);
        model.addAttribute("editoriales", editorialService.listarTodasEditoriales());
        model.addAttribute("categorias", categoriaService.listarTodasCategorias());
        model.addAttribute("autores", autorService.listarTodosLosAutores());
        return "libro/formulario_libro";
    }

    @PostMapping("/guardar")
    public String guardarLibro(@ModelAttribute Libro libro,
            @RequestParam("editorialId") Long editorialId,
            @RequestParam("categoriaId") Long categoriaId,
            @RequestParam("autoresIds") List<Long> autoresIds) {

        // Obtener y asignar la editorial y la categoria al libro
        Optional<Editorial> editorial = editorialService.buscarPorId(editorialId);
        editorial.ifPresent(libro::setEditorial);

        Optional<Categoria> categoria = categoriaService.buscarPorId(categoriaId);
        categoria.ifPresent(libro::setCategoria);

        List<Autor> autores = autorService.buscarPorIds(autoresIds);
        libro.setAutores(new ArrayList<>(autores));

        libroService.guardarLibro(libro);
        return "redirect:/libros/listar";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioEditarLibro(@PathVariable Long id, Model model) {
        Optional<Libro> libro = libroService.buscarPorId(id);
        if (libro.isPresent()) {
            model.addAttribute("libro", libro);
            model.addAttribute("editoriales", editorialService.listarTodasEditoriales());
            model.addAttribute("categorias", categoriaService.listarTodasCategorias());
            model.addAttribute("autores", autorService.listarTodosLosAutores());
        }
        return "libro/formulario_libro";
    }

    @PostMapping("{id}/actualizar")
    public String actualizarLibro(@PathVariable Long id,
            @ModelAttribute Libro libro,
            @RequestParam("editorialId") Long editorialId,
            @RequestParam("categoriaId") Long categoriaId,
            @RequestParam("autoresIds") List<Long> autoresIds) {

        // Obtener y asignar la editorial y la categoria al libro
        Optional<Editorial> editorial = editorialService.buscarPorId(editorialId);
        editorial.ifPresent(libro::setEditorial);

        Optional<Categoria> categoria = categoriaService.buscarPorId(categoriaId);
        categoria.ifPresent(libro::setCategoria);

        List<Autor> autores = autorService.buscarPorIds(autoresIds);
        libro.setAutores(new ArrayList<>(autores));

        libro.setId(id);
        libroService.actualizaLibro(libro);
        return "redirect:/libros/listar";
    }

    @GetMapping("path")
    public String mostrarAutoresDelLibro(@PathVariable Long id, Model model) {
        Optional<Libro> librOptional = libroService.buscarPorId(id);
        if (librOptional.isPresent()) {
            Libro libro = librOptional.get();
            model.addAttribute("libro", libro);
            model.addAttribute("autores", libro.getAutores());
        }
        return "libro/mostrar_autores_libro";
    }

    @GetMapping("/{id}/eliminar")
    public String eliminarLibro(@PathVariable Long id) {
        libroService.eliminarLibro(id);
        return "redirect:/libros/listar";
    }

}
