package Projekt;

import com.optimaize.anythingworks.common.host.Host;
import com.optimaize.command4j.CommandExecutor;
import com.optimaize.command4j.Mode;
import org.json.JSONException;
import org.json.JSONObject;
import org.nameapi.client.lib.NameApiModeFactory;
import org.nameapi.client.lib.NameApiPortUrlFactory;
import org.nameapi.client.lib.NameApiRemoteExecutors;
import org.nameapi.client.services.genderizer.persongenderizer.PersonGenderizerCommand;
import org.nameapi.client.services.system.ping.PingCommand;
import org.nameapi.ontology5.input.context.ContextBuilder;
import org.nameapi.ontology5.input.context.Priority;
import org.nameapi.ontology5.input.entities.person.InputPerson;
import org.nameapi.ontology5.input.entities.person.NaturalInputPerson;
import org.nameapi.ontology5.input.entities.person.NaturalInputPersonBuilder;
import org.nameapi.ontology5.input.entities.person.NullNaturalInputPerson;
import org.nameapi.ontology5.input.entities.person.name.InputPersonName;
import org.nameapi.ontology5.input.entities.person.name.builder.NameBuilders;
import org.nameapi.ontology5.services.genderizer.GenderizerResult;
import org.nameapi.ontology5.services.parser.personnameparser.PersonNameParserResult;
import se.walkercrou.places.Place;

import javax.naming.Context;

public class ProcessData {
    public static  String FULL_DATA;
    public static String FULL_NAME;
    public static String LOCATION;

    public ProcessData(String data) {
        FULL_DATA = data;
        String[] splitData = data.split("\\s+",3);
        FULL_NAME = splitData[0] + " " + splitData[1];
        LOCATION =  splitData[2];
    }

    public void getGenderAndNameInformation(){
        PersonNameParserResult result = NameAPI.checkPerson();
        String firstName = null;
        String surname = null;

        try {
            if (result!=null) {
            JSONObject jsonObject = new JSONObject(result);
            firstName = jsonObject.getJSONObject("bestMatch").getJSONObject("parsedPerson").getString("addressingGivenName");
            surname = jsonObject.getJSONObject("bestMatch").getJSONObject("parsedPerson").getString("addressingSurname");
            boolean isFemale = jsonObject.getJSONObject("bestMatch").getJSONObject("parsedPerson").getJSONObject("gender").getJSONObject("gender").getBoolean("female");
            boolean isMale = jsonObject.getJSONObject("bestMatch").getJSONObject("parsedPerson").getJSONObject("gender").getJSONObject("gender").getBoolean("male");
            System.out.println("First name: " + firstName);
            System.out.println("Surname: " + surname);
            if (isFemale) {
                System.out.println("She is a female");
            } else {
                System.out.println("He is a male");
            }
        }
        }catch (JSONException e){
            System.out.println(e);
        }
        finally {
            if(firstName==null || surname==null){
                LOCATION=FULL_DATA;
                FULL_NAME = null;
            }
        }
    }
    public void getAddressInformation(){
        Place result = AddressAPI.checkAddress();

        System.out.println("Name: " + result.getName());
        System.out.println("Phone: " + result.getPhoneNumber());
        System.out.println("International Phone: " + result.getInternationalPhoneNumber());
        System.out.println("Website: " + result.getWebsite());
        System.out.println("Always Opened: " + result.isAlwaysOpened());
        System.out.println("Status: " + result.getStatus());
        System.out.println("Google Place URL: " + result.getGoogleUrl());
        System.out.println("Price: " + result.getPrice());
        System.out.println("Address: " + result.getAddress());
        System.out.println("Vicinity: " + result.getVicinity());
        System.out.println("Reviews: " + result.getReviews().size());
        System.out.println("Hours: " + result.getHours());


        if(FULL_NAME==null){
            System.out.println("Business Address");
            for (String types : result.getTypes()){
                System.out.print(types);
            }
        }
        else{
            System.out.println("Commercial Address");
        }

    }

}
