/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.isisrapgame;
import generated.World;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author cmarco01
 */
public class Services { 
    World readWorldFromXml() throws JAXBException {
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream("world.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(World.class);
            Unmarshaller u = jaxbContext.createUnmarshaller();
            World world = (World) u.unmarshal(input);
            System.out.println(world.getName());
            return world;
        }
        catch (JAXBException e) {
            return new World();
        }
    }
    
    void saveWorldToXml(World world) throws FileNotFoundException {
        try {
            OutputStream output = new FileOutputStream("world.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(World.class);
            Marshaller m = jaxbContext.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(world, output);
        }
        catch (JAXBException e) {
        }
    }

}
