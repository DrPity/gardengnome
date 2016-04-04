package de.citec.sc.gardengnome.service.rsb;

import rsb.Event;
import rsb.patterns.EventCallback;

/**
 * Created by mschade on 01/04/16.
 */
public class RsbCallback extends EventCallback {

    InitDB initDB;

    public RsbCallback(InitDB initDB){
        this.initDB = initDB;
    }

    @Override
    public Event invoke(final Event request) throws Exception {
        QueryHandler queryHandler = new QueryHandler(initDB);
        String payload = queryHandler.handleEvent(request);
        System.out.print("Payload: " + payload);
        return new Event(String.class, payload);
    }

}