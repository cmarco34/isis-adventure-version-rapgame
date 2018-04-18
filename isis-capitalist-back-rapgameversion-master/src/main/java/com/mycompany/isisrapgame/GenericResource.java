/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.isisrapgame;

import com.google.gson.Gson;
import generated.World;
import java.io.FileNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.xml.bind.JAXBException;

/**
 * REST Web Service
 *
 * @author cmarco01
 */
@Path("generic")
public class GenericResource {

    @Context
    private UriInfo context;
    private Services serv;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
        this.serv = new Services();
    }

    /**
     * Retrieves representation of an instance of com.mycompany.isisrapgame.GenericResource
     * @return an instance of java.lang.String
     */
    @GET
    @Path("world")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_XML)
    public World getXml(@Context HttpServletRequest request) throws JAXBException, FileNotFoundException {
        String username = request.getHeader("X-User");
        World world = serv.readWorldFromXml();
        return world;
    }
    
    @GET
    @Path("world")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String getJson(@Context HttpServletRequest request) throws JAXBException {
        String username = request.getHeader("X-User");
        World world = serv.readWorldFromXml();
        return new Gson().toJson(world);
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(javax.ws.rs.core.MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
}
