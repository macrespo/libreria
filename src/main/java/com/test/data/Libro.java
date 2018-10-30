package com.test.data;

import java.util.Arrays;
import java.util.HashSet;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.wordnik.swagger.annotations.ApiModel;

/**
 * Libro
 */
@Entity
@Cache
@ApiModel("Objecto Libro")
public class Libro {

    @Id
    private Long id;

    private String nombre;

    @Index
    private String autor;

    private Short aniopub;
    
    private String genero;
    
    /*
     * Aunque es interesante usar la API Search para realizar la busqueda especificada
     * dicha API solo permite realizar búsquedas por palabras completas o por "aproximaciones"
     * a palabras con sentido. En el caso que nos ocupa se piden búsquedas por palabras que 
     * "empiezen" por la de la consulta.
     * Por eso he decidido indexar una lista de string a modo de etiquetas que se obtienen
     * de las palabras del nomnre y del autor, consiguiendo un conjunto (set) de palabras
     * sobre las que se realizará la búsqueda.
     * Cuando se modifica el nombre o el autor del libro el conjunto se actualiza antes de 
     * almacenar la instancia.
     */
    @Index
    private HashSet<String> index = new HashSet<String>();

    public String getNombre() {
    	return nombre;
    }

	public void setNombre(String nombre) {
		this.nombre = nombre;
		this.index.addAll(Arrays.asList(this.nombre.toLowerCase().split(" ")));
    }

    public String getAutor() {
    	return autor;
    }

	public void setAutor(String autor) {
		this.autor = autor; 
		this.index.addAll(Arrays.asList(this.autor.toLowerCase().split(" ")));
    }
    
    public Short getAnioPublicacion() {
        return aniopub;
    }

    public void setAnioPublicacion(Short anio) {
        this.aniopub = anio;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
