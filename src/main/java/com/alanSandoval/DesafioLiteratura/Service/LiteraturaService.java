package com.alanSandoval.DesafioLiteratura.Service;

import com.alanSandoval.DesafioLiteratura.Model.Autor;
import com.alanSandoval.DesafioLiteratura.Model.Libro;
import com.alanSandoval.DesafioLiteratura.Repository.AutorRepository;
import com.alanSandoval.DesafioLiteratura.Repository.LibroRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LiteraturaService {

    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;

    public LiteraturaService(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    @Transactional
    public void guardarLibrosDesdeJson(JsonNode json) {
        // El array con resultados viene en json.get("results")
        JsonNode resultados = json.get("results");

        if (resultados == null || !resultados.isArray()) {
            System.out.println("JSON no contiene 'results' o no es un array.");
            return;
        }

        for (JsonNode libroJson : resultados) {
            String titulo = libroJson.get("title").asText();

            // Idioma: puede venir en otro campo, si no, puedes poner un valor por defecto
            String idioma = "desconocido";
            if (libroJson.has("languages") && libroJson.get("languages").isArray() && libroJson.get("languages").size() > 0) {
                idioma = libroJson.get("languages").get(0).asText();
            }

            List<Autor> autores = new ArrayList<>();
            JsonNode autoresJson = libroJson.get("authors");
            if (autoresJson != null && autoresJson.isArray()) {
                for (JsonNode autorJson : autoresJson) {
                    String nombreAutor = autorJson.has("name") ? autorJson.get("name").asText() : "Desconocido";

                    Integer anioNacimiento = autorJson.has("birth_year") && !autorJson.get("birth_year").isNull()
                            ? autorJson.get("birth_year").asInt() : null;
                    Integer anioMuerte = autorJson.has("death_year") && !autorJson.get("death_year").isNull()
                            ? autorJson.get("death_year").asInt() : null;

                    // Buscar autor en DB para evitar duplicados
                    Optional<Autor> autorExistente = autorRepository.findByNombre(nombreAutor);
                    Autor autor;
                    if (autorExistente.isPresent()) {
                        autor = autorExistente.get();
                    } else {
                        autor = new Autor(nombreAutor, anioNacimiento, anioMuerte);
                        autorRepository.save(autor);
                    }
                    autores.add(autor);
                }
            }

            // Crear y guardar libro
            Libro libro = new Libro(titulo, idioma, autores);
            libroRepository.save(libro);

            System.out.println("Libro guardado: " + titulo);
        }
    }
}
