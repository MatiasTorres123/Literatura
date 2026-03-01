package challenge.literatura.repository;

import challenge.literatura.model.Autor;
import challenge.literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    Optional<Libro> findByTitulo(String titulo);

    List<Libro> findByIdioma(String idioma);

    List<Libro> findByAutor(Autor autor);
}
