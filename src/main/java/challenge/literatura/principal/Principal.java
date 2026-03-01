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
                    2 - Listar todos los libros
                    3 - Listar libros por idioma
                    4 - Listar autores de libros buscados
                    5 - Listar autores vivos en un determinado año
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();
            switch (opcion){
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarTodosLosLibros();
                    break;
                case 3:
                    listarLibrosPorIdioma();
                    break;
                case 4:
                    listarAutoresDeLibrosBuscados();
                    break;
                case 5:
                    listarAutoresVivosAnio();
                    break;
                case 0:
                    System.out.println("Saliendo!");
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        }
    }



    private void listarTodosLosLibros() {
        libroRepository.findAll().forEach(System.out::println);
    }

    private DatosLibro buscarLibroPorTitulo() {
        System.out.println("Escribe el titulo del libro que deseas buscar");
        var tituloLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(link + "?search="+tituloLibro.replace(" ","+"));
        var datos = conversor.obtenerDatos(json, Respuesta.class);
        List<DatosLibro> libros = datos.data();

        DatosLibro libro = null;
        if(libros.isEmpty()){
            System.out.println("No se encontro ningun libro con ese titulo");
        }else {
            libro = libros.get(0);


            DatosAutor datosAutor = libro.autores().stream()
                    .findFirst()
                    .orElseThrow();


            Autor autor = autorRepository.findByNombre(datosAutor.nombre()).orElseGet(() -> autorRepository.save(new Autor(datosAutor.nombre(), datosAutor.anioNacimiento(), datosAutor.anioFallecimiento())));

            Libro libroNuevo = new Libro(libro, autor);
            Optional<Libro> libroExistente = libroRepository.findByTitulo(libroNuevo.getTitulo());

            if (libroExistente.isEmpty()) {
                libroRepository.save(libroNuevo);
            }

            System.out.println(libroNuevo);
        }
        return libro;
    }

    private void listarLibrosPorIdioma(){
        var menu = """
                    Ingrese el idioma para buscar los libros
                    es - español 
                    en - ingles
                    fr - frances
                    pt - portugues
                    """;
        System.out.println(menu);
        var idioma = teclado.nextLine();
        List<Libro> libros=libroRepository.findByIdioma(idioma);
        if (!libros.isEmpty()) {
            libros.forEach(System.out::println);
        }else{
            System.out.println("Actualmente no hay libros registrados en ese idioma");
        }

    }

    private void listarAutoresDeLibrosBuscados() {
        List<Autor> autores = autorRepository.findAll();
        listar(autores);
    }
    private void listarAutoresVivosAnio() {
        System.out.println("Ingrese el año:");
        var anio = teclado.nextInt();
        List<Autor> autores = autorRepository.buscarAutoreVivosEnUnAnio(anio);
        listar(autores);
    }

    private void listar(List<Autor> autores){
        for (Autor a : autores) {
            System.out.println("Autor: " + a.getNombre());
            System.out.println("Fecha de nacimiento: " + a.getAnioNacimiento());
            System.out.println("Fecha de fallecimiento: " + (a.getAnioFallecimiento() != null ? a.getAnioFallecimiento() : "Sigue vivo"));

            List<Libro> libros = libroRepository.findByAutor(a);

            if (!libros.isEmpty()) {
                System.out.println("Libros:");
                libros.forEach(l-> System.out.println(l.getTitulo()));
            } else {
                System.out.println("Libros: Ninguno registrado");
            }

            System.out.println("\n------------------------\n");



        }
    }

}
