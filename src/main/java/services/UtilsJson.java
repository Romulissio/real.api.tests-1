package services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.agencies.Agency;
import entity.AuthToken;
import entity.agencies.DataAgency;

public class UtilsJson {

    public static String getTokenFromJson(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        AuthToken authToken = null;
        try {
            authToken = mapper.readValue(jsonString, AuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        assert authToken != null;
        return authToken.getToken();
    }

    public static DataAgency getListAgencies(String jsonString){
        ObjectMapper mapper = new ObjectMapper();
        DataAgency dataAgency = null;
        try {
            dataAgency = mapper.readValue(jsonString, DataAgency.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        assert dataAgency != null;
        return dataAgency;
    }

    public static Agency getAgency(String jsonString){
        ObjectMapper mapper = new ObjectMapper();
        Agency agency = null;
        try {
            agency = mapper.readValue(jsonString, Agency.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        assert agency != null;
        return agency;
    }
}
