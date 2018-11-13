package Projekt;

import com.optimaize.anythingworks.common.host.Host;
import com.optimaize.command4j.CommandExecutor;
import com.optimaize.command4j.Mode;
import org.nameapi.client.lib.NameApiModeFactory;
import org.nameapi.client.lib.NameApiPortUrlFactory;
import org.nameapi.client.lib.NameApiRemoteExecutors;
import org.nameapi.client.services.genderizer.persongenderizer.PersonGenderizerCommand;
import org.nameapi.client.services.parser.personnameparser.PersonNameParserCommand;
import org.nameapi.ontology5.input.context.ContextBuilder;
import org.nameapi.ontology5.input.context.Priority;
import org.nameapi.ontology5.input.entities.person.InputPerson;
import org.nameapi.ontology5.input.entities.person.NaturalInputPersonBuilder;
import org.nameapi.ontology5.input.entities.person.name.InputPersonName;
import org.nameapi.ontology5.input.entities.person.name.builder.NameBuilders;
import org.nameapi.ontology5.services.genderizer.GenderizerResult;
import org.nameapi.ontology5.services.parser.personnameparser.PersonNameParserResult;

import static Projekt.ProcessData.FULL_NAME;


public class NameAPI {

    static String API_KEY = "";
    public static PersonNameParserResult checkPerson(){
        PersonNameParserResult result = null;
        try {
            org.nameapi.ontology5.input.context.Context context = new ContextBuilder().priority(Priority.REALTIME).build();
            CommandExecutor executor = NameApiRemoteExecutors.get();
            Mode mode = NameApiModeFactory.withContext(
                    API_KEY,
                    context,
                    new Host("rc50-api.nameapi.org", 80), NameApiPortUrlFactory.versionLatestStable()

            );
            InputPersonName name = NameBuilders.western().fullname(FULL_NAME).build();
            InputPerson inputPerson = new NaturalInputPersonBuilder().name((InputPersonName) name).build();

            PersonNameParserCommand command = new PersonNameParserCommand();


            result = executor.execute(command,mode,inputPerson).get();


        }catch (Exception e){
            System.out.println(e);
        }
        return result;

    }



}
