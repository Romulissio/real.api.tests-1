package services;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

public class UtilsData {

    public static String decodeCookieToken(Map<String, String> cookie) throws UnsupportedEncodingException {
        return URLDecoder.decode((cookie.get("XSRF-TOKEN")), "UTF-8" );
    }

    public static Long gerRandomBigNumber(){
        return (long) (10000000 + Math.random()*10000);
    }

    public static int getRandomAgencyListIndex(int sizeAgencyList){
        return (int) (1 + (Math.random() * sizeAgencyList));
    }
}
