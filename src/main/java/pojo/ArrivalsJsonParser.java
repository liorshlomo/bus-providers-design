package pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class ArrivalsJsonParser implements IArrivalsParser {

    @Override
    public List<StopEta> parseResponse(String response) {
        return parseResponseFromJson(response);
    }

    public List<StopEta> parseResponseFromJson(String json) {
        List<StopEta> stopEtas = new ArrayList<>();
        //Using the JSON simple library parse the string into a json object
        JSONParser parser = new JSONParser();
        JSONObject data_obj = null;
        try {
            data_obj = (JSONObject) parser.parse(json);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONArray arr = (JSONArray) data_obj.get("arrivals");

        for(int i = 0; i < arr.size(); i++)
        {
            JSONObject arrivalObject = (JSONObject) arr.get(i);
            long stopIdLong =  (long)arrivalObject.get("stopId");
            int stopId = (int)stopIdLong;
            long etaTimestamp = (long)arrivalObject.get("eta");
            Date eta = new Date(etaTimestamp * 1000L); // Convert timestamp to Date object
            StopEta stopEta = new StopEta(stopId, eta);
            stopEtas.add(stopEta);
        }
        return stopEtas;
    }


}
