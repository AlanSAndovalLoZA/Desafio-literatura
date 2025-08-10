package com.alanSandoval.DesafioLiteratura.Service;

import com.alanSandoval.DesafioLiteratura.Model.Autor;
import com.alanSandoval.DesafioLiteratura.Model.Libro;
import com.alanSandoval.DesafioLiteratura.Repository.AutorRepository;
import com.alanSandoval.DesafioLiteratura.Repository.LibroRepository;
import com.alanSandoval.DesafioLiteratura.Service.ConsumoAPI;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Principal {

    private final LibroRepository libroRepo;
    private final AutorRepository autorRepo;
    private final ConsumoAPI api;

    public Principal(LibroRepository libroRepo, AutorRepository autorRepo) {
        this.libroRepo = libroRepo;
        this.autorRepo = autorRepo;
        this.api = new ConsumoAPI();
    }

    public void mostrarMenu() {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1 - Buscar libro por título");
            System.out.println("2 - Lista de libros registrados");
            System.out.println("3 - Lista de autores registrados");
            System.out.println("4 - Lista de autores vivos en determinado año");
            System.out.println("5 - Lista de libros por idioma");
            System.out.println("0 - Salir");
            System.out.print("Opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> buscarLibro(sc);
                case 2 -> listarLibros();
                case 3 -> listarAutores();
                case 4 -> autoresVivos(sc);
                case 5 -> librosPorIdioma(sc);
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida");
            }
        } while (opcion != 0);
    }

    private void buscarLibro(Scanner sc) {
        System.out.print("Título: ");
        String titulo = sc.nextLine();

        // Validar si el libro ya existe
        Optional<Libro> libroExistente = libroRepo.findByTituloIgnoreCase(titulo);
        if (libroExistente.isPresent()) {
            System.out.println("\n*********************\nEl libro ya está registrado!!\n" );
            return;
        }

        JsonNode json = api.buscarLibroPorTitulo(titulo);
        if (json == null) {
            System.out.println("No se pudo obtener datos de la API.");
            return;
        }

        JsonNode results = json.get("results");
        if (results == null || !results.isArray() || results.size() == 0) {
            System.out.println("No se encontraron libros con ese título.");
            return;
        }

        // Solo procesa el primer libro para evitar saturar la consola
        JsonNode item = results.get(0);

        String nombre = item.get("title").asText();
        String idioma = item.get("languages").get(0).asText();

        List<Autor> autores = new ArrayList<>();
        item.get("authors").forEach(a -> {
            String nom = a.get("name").asText();
            Integer nacimiento = a.get("birth_year").isNull() ? null : a.get("birth_year").asInt();
            Integer muerte = a.get("death_year").isNull() ? null : a.get("death_year").asInt();

            Autor autor = autorRepo.findAll().stream()
                    .filter(existing -> existing.getNombre().equalsIgnoreCase(nom))
                    .findFirst()
                    .orElseGet(() -> autorRepo.save(new Autor(nom, nacimiento, muerte)));
            autores.add(autor);
        });

        Libro libro = new Libro(nombre, idioma, autores);
        libroRepo.save(libro);
        System.out.println("Libro guardado:\n" + libro);
    }

    private void listarLibros() {
        List<Libro> libros = libroRepo.findAll();
        for (Libro libro : libros) {
            System.out.println(libro);
            System.out.println(); // espacio extra entre libros
        }
    }

    private void listarAutores() {
        List<Autor> autores = autorRepo.findAll();
        for (Autor a : autores) {
            System.out.println("Autor: " + a.getNombre());
            System.out.println("Fecha de nacimiento: " + a.getAnioNacimiento());
            System.out.println("Fecha de fallecimiento: " + a.getAnioMuerte());
            System.out.print("Libros: ");
            if (a.getLibros() != null && !a.getLibros().isEmpty()) {
                a.getLibros().forEach(libro -> System.out.print(libro.getTitulo() + "; "));
            } else {
                System.out.print("Ninguno");
            }
            System.out.println("\n");
        }
    }

    private void autoresVivos(Scanner sc) {
        System.out.print("Año: ");
        int anio = sc.nextInt();
        sc.nextLine();

        List<Autor> autores = autorRepo.findAll();

        List<Autor> vivosEnAnio = new ArrayList<>();

        for (Autor autor : autores) {
            Integer nacimiento = autor.getAnioNacimiento();
            Integer muerte = autor.getAnioMuerte();

            if (nacimiento != null && nacimiento <= anio) {
                if (muerte == null || muerte >= anio) {
                    vivosEnAnio.add(autor);
                }
            }
        }

        if (vivosEnAnio.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + anio);
        } else {
            System.out.println("Autores vivos en el año " + anio + ":");
            for (Autor autor : vivosEnAnio) {
                System.out.println("Autor: " + autor.getNombre());
                System.out.println("Fecha de nacimiento: " +
                        (autor.getAnioNacimiento() != null ? autor.getAnioNacimiento() : "Desconocida"));
                System.out.println("Fecha de fallecimiento: " +
                        (autor.getAnioMuerte() != null ? autor.getAnioMuerte() : "Desconocida"));
                System.out.println();
            }
        }
    }

    private void librosPorIdioma(Scanner sc) {
        System.out.print("Idioma (ej: en, es, fr): ");
        String idioma = sc.nextLine();

        List<Libro> libros = libroRepo.findByIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma '" + idioma + "'");
        } else {
            libros.forEach(l -> {
                System.out.println(l);
                System.out.println(); // espacio extra entre libros
            });
        }
    }
}
