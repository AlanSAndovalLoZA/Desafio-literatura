# Desafío Literatura con Spring Boot

Este proyecto consume datos de la API Gutendex, guarda libros y autores en una base de datos PostgreSQL y ofrece un menú interactivo para consultar la información.

---

## Menú principal

Cada opción del menú corresponde a funcionalidades que puedes usar en la consola.

### Opciones:

1. **Buscar libro por título**  
   ![Buscar libro](images/menu1-1.png)  
   Al ingresar el título, busca en la API, verifica que no esté repetido y guarda el libro con sus autores.

2. **Lista de libros registrados**  
   ![Lista libros](images/menu2-1.png)  
   Muestra todos los libros que ya tienes guardados en la base de datos.

3. **Lista de autores registrados**  
   ![Lista autores](images/menu3-1.png)  
   Lista todos los autores registrados, mostrando nombre, fechas y sus libros.

4. **Lista de autores vivos en determinado año**  
   ![Autores vivos](images/menu4-1.png)  
   Permite filtrar autores que estuvieron vivos en un año específico.

5. **Lista de libros por idioma**  
   ![Libros por idioma](images/menu5-1.png)  
   Muestra los libros filtrados por idioma (ejemplo: en, es, fr).

0. **Salir**  
   ![Salir](images/menu0-1.png)  
   Salir de la aplicación.

---

## Estructura de carpetas relevantes

- `src/main/java/...`  
  Código fuente Java con modelos, repositorios y servicios.

- `src/main/resources/images/`  
  Aquí están las imágenes usadas para ilustrar el README.

---

## Cómo ejecutar

1. Configura tu base de datos PostgreSQL y los datos en `application.properties`.  
2. Corre la aplicación Spring Boot (`DesafioLiteraturaApplication.java`).  
3. Usa el menú en consola para interactuar.

---

## Capturas de ejemplo

_(Incluye aquí alguna captura general si deseas, o más imágenes de las funciones)_

---

## Contacto

Alan Sandoval  
[GitHub](https://github.com/AlanSAndovalLoZA)  

---

¡Gracias por usar este proyecto!  
