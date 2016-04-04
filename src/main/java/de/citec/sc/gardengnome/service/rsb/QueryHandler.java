package de.citec.sc.gardengnome.service.rsb;

import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import rsb.AbstractEventHandler;
import rsb.Event;
import rsb.RSBException;

/**
 *
 * @author cunger
 */
public class QueryHandler {
    
    InitDB initDB;
    JSONParser json;
    
    private static final Logger log = Logger.getLogger(Logger.class.getName());

    
    public QueryHandler(InitDB initDB) {
        
        this.initDB = initDB;
        this.json  = new JSONParser();
    }
    

    public String handleEvent(final Event event) {
                
        String data = event.getData().toString();

        log.info("Received:  " + event.toString());
        log.info("With data: " + data);
        
        // Test
        //if (data.equals("test")) try { initDB.mouth.speak("Yay!"); } catch (RSBException e) { log.severe(e.getMessage()); }
        
        // Real query 
        
        String uid  = null;
        String ask  = null;
        String coll = null;
        String doc  = null;
        try {
             JSONObject object = (JSONObject) json.parse(data);

             if (object.containsKey("uid"))  uid  = (String) object.get("uid");
             if (object.containsKey("ask"))  ask  = (String) object.get("ask");
             if (object.containsKey("coll")) coll = (String) object.get("coll");
             if (object.containsKey("doc"))  doc  = (String) object.get("doc");
        }
        catch (Exception e) {
            log.severe(e.getMessage());
            System.out.print("Problems parsing ");
        }

        String payload;
                
        if (uid != null && ask != null) {
                        
            String answer = "";
            
            switch (ask) {
                        
                case "age":         answer += initDB.memory.queryAge(uid); break;
                case "hasbirthday": answer += initDB.memory.hasBirthday(uid); break;
                case "name":        answer += initDB.memory.queryAttribute("info",uid,"name"); break;
                case "gender":      answer += initDB.memory.queryAttribute("info",uid,"gender"); break;
                case "height":      answer += initDB.memory.queryAttribute("info",uid,"height"); break;
                case "birthdate":   answer += initDB.memory.queryAttribute("info",uid,"birthdate"); break;
                    
                default: answer = "ERROR";
            }
                        
            payload = "{ \"uid\" : "+uid+", \""+ask+"\": \""+answer+"\" }";
            System.out.print("Payload: " + payload);
            //answer(payload);
            return payload;
        } 
         
        if (coll != null && doc != null) {
                        
            payload = initDB.memory.queryDocument(coll,doc);

            return payload;
        }

        return "ERROR";
    }

}
