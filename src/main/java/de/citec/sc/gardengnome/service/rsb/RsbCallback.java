package de.citec.sc.gardengnome.service.rsb;

import rsb.Event;
import rsb.patterns.EventCallback;

/**
 * Created by mschade on 01/04/16.
 */
public class RsbCallback extends EventCallback {

    InitDB initDB;
    String rsb_scope;

    public RsbCallback(InitDB initDB, String rsb_scope){
        this.initDB = initDB;
        this.rsb_scope = rsb_scope;
    }

    @Override
    public Event invoke(final Event request) throws Exception {
        if(request.getScope().toString().equals(rsb_scope + "/write/")){
            WriteHandler writeHandler = new WriteHandler(initDB);
            String payload = writeHandler.handleEvent(request);
            return new Event(String.class, payload);
        }else if (request.getScope().toString().equals(rsb_scope + "/query/")){
            QueryHandler queryHandler = new QueryHandler(initDB);
            String payload = queryHandler.handleEvent(request);
            return new Event(String.class, payload);
        }else if (request.getScope().toString().equals(rsb_scope + "/request/")){
            Request queryHandler = new Request(initDB);
            String payload = queryHandler.handleEvent(request);
            return new Event(String.class, payload);
        }else{
            return new Event(String.class, "Error");
        }

    }

}