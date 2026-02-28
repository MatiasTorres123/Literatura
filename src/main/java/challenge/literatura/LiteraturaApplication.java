package challenge.literatura;

import challenge.literatura.principal.Principal;
import challenge.literatura.repository.AutorRepository;
import challenge.literatura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {

    @Autowired
    private LibroRepository repositoryLibro;

    @Autowired
    private AutorRepository repositoryAutor;
	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal(repositoryLibro,repositoryAutor);
        principal.muestraElMenu();
    }
}
