package com.alanSandoval.DesafioLiteratura.Repository;

import com.alanSandoval.DesafioLiteratura.Model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    // Método derivado para buscar libros por idioma
    List<Libro> findByIdioma(String idioma);

    // Ejemplo de método con JPQL para traer libros con autores evitando lazy loading
    @Query("SELECT DISTINCT l FROM Libro l JOIN FETCH l.autores")
    List<Libro> findAllWithAutores();

    // Método para buscar libro por título (ignora mayúsculas/minúsculas)
    Optional<Libro> findByTituloIgnoreCase(String titulo);
}




