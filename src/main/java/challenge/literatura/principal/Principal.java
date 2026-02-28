package challenge.literatura.principal;

import challenge.literatura.model.*;
import challenge.literatura.repository.AutorRepository;
import challenge.literatura.repository.LibroRepository;
import challenge.literatura.service.ConsumoAPI;
import challenge.literatura.service.ConvierteDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    public Principal(LibroRepository repositoryLibro,AutorRepository repositoryAutor){
        this.autorRepository=repositoryAutor;
        this.libroRepository=repositoryLibro;
    }
    private Scanner teclado = new Scanner(System.in);
    private String link = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por titulo 
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();
            switch (opcion){
                case 1:
                    buscarLibroPorTitulo();
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        }
    }

    private DatosLibro buscarLibroPorTitulo() {
        System.out.println("Escribe el titulo del libro que deseas buscar");
        var tituloLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(link + "?search="+tituloLibro.replace(" ","%"));
        System.out.println(json);
        var datos = conversor.obtenerDatos(json, Respuesta.class);
        List<DatosLibro> libros = datos.data();

        DatosLibro libro = libros.stream()
                        .findFirst()
                        .orElseThrow(()-> new RuntimeException("No se encontro ningun libro"));

        DatosAutor datosAutor = libro.autores().stream()
                .findFirst()
                .orElseThrow();

        Autor autor = autorRepository.findByNombre(datosAutor.nombre()).orElseGet(() -> autorRepository.save( new Autor( datosAutor.nombre(), datosAutor.anioNacimiento(), datosAutor.anioFallecimiento() ) ) );

        Libro libroNuevo = new Libro(libro,autor);
        Optional<Libro> libroExistente = libroRepository.findByTitulo(libroNuevo.getTitulo());

        if (libroExistente.isEmpty()) {
            libroRepository.save(libroNuevo);
        }

        System.out.println(libroNuevo);
        return libro;
    }
}
