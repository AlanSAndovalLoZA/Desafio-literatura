package com.alanSandoval.DesafioLiteratura.Model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)  // Para evitar error de longitud
    private String titulo;

    @Column(length = 1000)
    private String idioma;

    private Integer totalDescargas = 0; // Campo para total descargas

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores;



    public Libro() {}

    public Libro(String titulo, String idioma, List<Autor> autores) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.autores = autores;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public Integer getTotalDescargas() {
        return totalDescargas;
    }

    public void setTotalDescargas(Integer totalDescargas) {
        this.totalDescargas = totalDescargas;
    }

    @Override
    public String toString() {
        String autoresNombres = "";
        if (autores != null && !autores.isEmpty()) {
            autoresNombres = autores.stream()
                    .map(Autor::getNombre)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("");
        }

        return "*****Libro*****\n" +
                "Título: " + titulo + "\n" +
                "Autor(es): " + autoresNombres + "\n" +
                "Idioma: " + idioma + "\n" +
                "Total descargas: " + (totalDescargas != null ? totalDescargas : "0") + "\n";
    }

}
