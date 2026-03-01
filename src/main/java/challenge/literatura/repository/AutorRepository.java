package challenge.literatura.repository;

import challenge.literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor,Long> {
    Optional<Autor> findByNombre(String nombre);

    @Query("Select a from Autor a where a.anioNacimiento <= :anio AND (a.anioFallecimiento is null OR a.anioFallecimiento >= :anio)")
    List<Autor> buscarAutoreVivosEnUnAnio(int anio);
}
