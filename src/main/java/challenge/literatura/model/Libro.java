package challenge.literatura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String titulo;
    @ManyToOne
    private Autor autor;
    private String idioma;
    private Integer NumDescargas;

    public Libro() {
    }

    public Libro(DatosLibro dato, Autor autor) {
        this.titulo = dato.titulo();
        this.autor = autor;
        this.idioma = dato.idioma().get(0);
        this.NumDescargas = dato.cantidadDescargas();
    }

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

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getNumDescargas() {
        return NumDescargas;
    }

    public void setNumDescargas(Integer numDescargas) {
        NumDescargas = numDescargas;
    }

    @Override
    public String toString() {
        return "Libro: " + titulo + "\n" +
                "Autor: " + (autor != null ? autor.getNombre() : "Desconocido") + "\n" +
                "Idioma: " + idioma + "\n" +
                "Descargas: " + NumDescargas +"\n";
    }
}