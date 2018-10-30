package com.test.rest;

import com.test.dao.LibroDAO;
import com.test.data.Libro;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/libreria")
@Produces("application/json;charset=utf-8")
@Api(value = "libreria", description = "Servicio de Libreria")
public class LibreriaResource {

    private LibroDAO libroDAO;

    public LibreriaResource() {
        this.libroDAO = new LibroDAO();
    }

    @GET
    @ApiOperation("Obtiene el listado de objetos Libro")
    public Response list() {
        return Response.ok(this.libroDAO.list()).build();
    }

    @GET
    @Path("/search/{palabra}")
    @ApiOperation("Obtiene un listado de objetos Libro en los que aparezca PALABRA en Nombre o Autor")
    public Response search(
    	@ApiParam(value = "palabra", required = true) @PathParam("palabra") String palabra)
    {
        return Response.ok(this.libroDAO.search(palabra)).build();
    }

    @GET
    @Path("/{id}")
    @ApiOperation("Recupera un objeto Libro")
    public Response get(
    	@ApiParam(value = "id del libro a recuperar", required = true) @PathParam("id") Long id)
    {
        Libro libro = this.libroDAO.get(id);
        if (libro == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(libro).build();
    }

    @POST
    @Consumes("application/json;charset=utf-8")
    @ApiOperation("Guarda-Actualiza un objeto Libro")
    public Response save(
    	@ApiParam(value = "objeto libro a añadir", required = true) Libro libro)
    {
        this.libroDAO.save(libro);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @ApiOperation("Elimina un objeto Libro")
    public Response delete(
    	@ApiParam(value = "id del libro a eliminar", required = true) @PathParam("id") Long id)
    {
        Libro libro = this.libroDAO.get(id);
        if (libro == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        this.libroDAO.delete(libro);
        return Response.ok().build();
    }
}
