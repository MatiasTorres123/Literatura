# 📚 Literatura - App de Biblioteca en Consola

Este proyecto es una **aplicación de consola en Java** que permite:

✔ Buscar libros por título  
✔ Listar todos los libros guardados  
✔ Filtrar libros por idioma  
✔ Guardar libros y autores desde una API externa (Gutendex)  
✔ Listar autores registrados  
✔ Listar autores vivos en un año específico

Es un sistema simple de gestión de libros que combina:

- Consumo de API externa para buscar libros
- Base de datos local con Spring Data JPA
- Persistencia con PostgreSQL

---

## 🧠 Descripción general

Cuando ejecutás la aplicación:

1. Podés buscar un libro por título.
2. El sistema consulta la API pública de **Gutendex** para obtener datos reales.
3. Si el libro no estaba registrado, se guarda en la base de datos junto con su autor.
4. Podés consultar registros ya guardados y hacer filtros por idioma o autor.

Este proyecto sirve tanto como ejercicio práctico de Java + Spring Boot + JPA como base para un futuro sistema más grande.

---

## ⚙️ Tecnologías usadas

- Java 17+
- Spring Boot  
- Spring Data JPA  
- PostgreSQL (o H2, MySQL, etc.)  
- API externa: **Gutendex Books API**

---
