package de.citec.sc.gardengnome.service.rsb;

import de.citec.sc.gardengnome.database.PersonMemory;
import java.io.IOException;
import java.util.logging.Logger;

import rsb.Factory;
import rsb.RSBException;
import rsb.patterns.LocalServer;

/**
 *
 * @author cunger
 */
public class InitDB {

    PersonMemory memory;
    LocalServer server;

    private static final Logger log = Logger.getLogger(Logger.class.getName());

    
    public InitDB() {
    }
    
    
    public void connectToDB(String db_host, int db_port, String db_name) {
                
        memory = new PersonMemory(db_host,db_port);
        memory.setDB(db_name);
        
        log.info("Connected to database '" + db_name +"' (host: " + db_host + ", port: " + db_port + ").");
    }   
        
    public void activate(String rsb_scope) throws RSBException, InterruptedException {

        server = Factory.getInstance().createLocalServer(rsb_scope);
        server.activate();
        server.addMethod("query", new RsbCallback(this, rsb_scope));
        server.addMethod("write", new RsbCallback(this, rsb_scope));
        server.addMethod("request", new RsbCallback(this, rsb_scope));

        log.info("server started" + rsb_scope);

        server.waitForShutdown();
    }
    
    public PersonMemory getMemory() {
        return memory;
    }
            
    public void factoryReset(String data_path, String db_name) throws IOException {
        
        memory.initialize(data_path,db_name);
    }

    
    public void deactivate() throws RSBException, InterruptedException {

        server.deactivate();
        memory.shutDown();
    }
}

