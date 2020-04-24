package validator.xml.journal.serverlink;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import validator.xml.journal.model.Journal;

import java.io.IOException;
import java.util.logging.Logger;

//Class that assists loading the data from a single Journal object to the server
public class StoreInDataBase {

    private static Logger LOGGER = LoggerSetUp.setUp();

    public static void addToDB(Journal journal) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(journal);

        } catch (JsonProcessingException e) {
            LOGGER.info("Could not convert journal object to json string" + e.getStackTrace());
            System.out.println("Could not convert journal object to json string. Please try again");
            System.exit(0);
        }

        //Creating the http POST request to load the data to the server
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost("http://localhost:8080/api/entry");

            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            CloseableHttpResponse response = client.execute(httpPost);
        }
    }


}
