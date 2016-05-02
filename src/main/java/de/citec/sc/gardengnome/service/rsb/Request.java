package de.citec.sc.gardengnome.service.rsb;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.logging.Logger;
import rsb.Event;

public class Request {

    InitDB initDB;
    JSONParser json;

    private static final Logger log = Logger.getLogger(Logger.class.getName());


    public Request(InitDB initDB) {

        this.initDB = initDB;
        this.json  = new JSONParser();
    }


    public String handleEvent(final Event event) {

        String data = event.getData().toString();

        log.info("Received request:  " + event.toString());
        log.info("With data: " + data);


        String coll = null;
        String find  = null;

        try {
            JSONObject object = (JSONObject) json.parse(data);

            if (object.containsKey("coll")) coll = (String) object.get("coll");
            if (object.containsKey("find"))  find  = object.get("find").toString();
        }
        catch (Exception e) {
            log.severe(e.getMessage());
            System.out.print("Problems parsing ");
        }

        String response;

        if (coll != null && find != null) {

            response = initDB.getMemory().retrieveDocument(coll,find);
            return response;
        }


        return "ERROR";
    }

}


