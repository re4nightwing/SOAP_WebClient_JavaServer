package currency.server;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Endpoint;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public class Server {

    static String getJsonString() {
        URL url = null;
        try {
            url = new URL(
                    "https://raw.githubusercontent.com/re4nightwing/SOAP_WebClient_JavaServer/main/static/currency_data.json");
        } catch (MalformedURLException e2) {
            e2.printStackTrace();
        }
        StringBuilder builder = new StringBuilder();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                builder.append(str);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String jsonStr = builder.toString();

        return jsonStr;
    }

    @WebMethod
    public Double convert(Float amountInSourceCurrency, String sourceCurrency, String targetCurrency) {
        JSONParser parser = new JSONParser();
        Object keyvalue1 = null;
        Object keyvalue2 = null;
        Double returnVal = 0.0;
        //System.out.println(amountInSourceCurrency+" : "+sourceCurrency+" : "+targetCurrency);
        try {
            Object obj = parser.parse(getJsonString());
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject rateObject = (JSONObject) jsonObject.get("rates");
            //Object defValue = rateObject.get("USD");
            keyvalue1 = rateObject.get(sourceCurrency);
            keyvalue2 = rateObject.get(targetCurrency);
            returnVal = (1.0 / Double.valueOf(String.valueOf(keyvalue1))) * (Double.valueOf(String.valueOf(keyvalue2)) * amountInSourceCurrency);
            //System.out.println(keyvalue1 +" : "+keyvalue2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(returnVal);
        return returnVal;
    }
    @WebMethod
    public String currencyData() {
        JSONParser parser = new JSONParser();

        String curr = null;
        String joined = null;

        try {
            Object obj = parser.parse(getJsonString());
            JSONObject jsonObject = (JSONObject) obj;
            curr = (String) jsonObject.get("base");
            JSONObject rateObject = (JSONObject) jsonObject.get("rates");
            Set<String> setKeys = rateObject.keySet();
            joined = String.join(",", setKeys);
            for (String key : setKeys) {
                Object keyvalue = rateObject.get(key);
                //System.out.println(key + " : " + keyvalue);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Currency Data sent!");
        return joined;
    }
    public static void main(String[] args){
        Endpoint.publish("http://localhost:8888/currency_server", new Server());
    }
}