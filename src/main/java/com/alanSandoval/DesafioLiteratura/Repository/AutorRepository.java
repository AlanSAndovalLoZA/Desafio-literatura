package com.alanSandoval.DesafioLiteratura.Repository;

import com.alanSandoval.DesafioLiteratura.Model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    // Método para traer autores con sus libros en una sola consulta (evita LazyInitializationException)
    @Query("SELECT DISTINCT a FROM Autor a LEFT JOIN FETCH a.libros")
    List<Autor> findAllWithLibros();

    // Método ya existente para autores vivos
    List<Autor> findByAnioNacimientoLessThanEqualAndAnioMuerteGreaterThanEqual(Integer anioNacimiento, Integer anioMuerte);

    Optional<Autor> findByNombre(String nombre);
}
