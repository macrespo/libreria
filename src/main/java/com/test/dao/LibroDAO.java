package com.test.dao;

import java.util.List;
import java.util.logging.Logger;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import com.test.data.Libro;

public class LibroDAO {

    private static final Logger LOGGER = Logger.getLogger(LibroDAO.class.getName());

    /**
     * @return listado de libros
     */
    public List<Libro> list() {
        LOGGER.info("Obteniendo listado de libros");
        return ObjectifyService.ofy().load().type(Libro.class).order("autor").list();
    }

    /**
     * @param palabra
     * @return listado de libros que contengan _palabra_ en Titulo o Autor
     */
	public List<Libro> search(String palabra) {
        LOGGER.info("Buscando libros con: " + palabra);
        /*
         * La búsqueda de "palabras" se hace sobre el atributo "index", que es el
         * conjunto de las palabras contenidas en el nombre y el autor del libro.
         * Los libros que coincidan son aquellos que conengan palabras que empiecen
         * por la palabra dada.
         * Es decir, si la palabra es "ejemp", se filtran las entidades que tengan
         * alguna palabra en index que sea mayor o igual que "ejemp" pero que al
         * mismo tiempo sean menores que "ejemp" + el mayor caracter que se pueda 
         * obtener (en este caso, al estar usar formato utf-8 será \uFFFF.
         * Cualquier palabra que empiece por "ejemp" será menor que "ejemp" + \uFFFF.
         * Se fitran así las palabras en ese rango.
         */
        Query<Libro> query = ObjectifyService.ofy().load().type(Libro.class)
        	.filter("index >=", palabra)
        	.filter("index <", palabra + '\uFFFF')
        	.order("index");
        
        return query.list();
	}

    /**
     * @param id
     * @return Objeto libro con id proporcionado
     */
    public Libro get(Long id) {
        LOGGER.info("Obteniendo libro " + id);
        return ObjectifyService.ofy().load().type(Libro.class).id(id).now();
    }

    /**
     * Actualiza el libro porporcionado
     * @param libro
     */
    public void save(Libro libro) {
        if (libro == null) {
            throw new IllegalArgumentException("objeto libro null");
        }
        LOGGER.info("Guardando libro nuevo"); //  libro.getId() es null hasta que no se guarde
        ObjectifyService.ofy().save().entity(libro).now();
    }

    /**
     * Elimina el libro especificado
     * @param libro
     */
    public void delete(Libro libro) {
        if (libro == null) {
            throw new IllegalArgumentException("objeto libro null");
        }
        LOGGER.info("Eliminando libro " + libro.getId());
        ObjectifyService.ofy().delete().entity(libro);
    }
}
