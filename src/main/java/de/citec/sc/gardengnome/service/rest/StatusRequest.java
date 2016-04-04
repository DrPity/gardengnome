package de.citec.sc.gardengnome.service.rest;

import de.citec.sc.gardengnome.service.rsb.InitDB;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 *
 * @author cunger
 */
public class StatusRequest extends ServerResource {
    
    public InitDB initDB;

            
    public StatusRequest() {
        
        initDB = new InitDB();
        initDB.connectToDB("localhost",27017,"becker"); 
    }

    @Get
    public String process(Representation entity) throws Exception {
        
        return initDB.getMemory().show();
    }
    
}
