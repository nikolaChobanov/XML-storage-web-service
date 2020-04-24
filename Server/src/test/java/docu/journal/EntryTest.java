package docu.journal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import docu.journal.model.CaseFile;
import docu.journal.model.Entry;
import docu.journal.model.Records;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JournalApplication.class)
@WebAppConfiguration
public class EntryTest {


    protected MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;


    @BeforeClass
    public void setUp() {

        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getProductsList() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String uri = "http://localhost:8080/api/entry?page=1&pageSize=3&public=true";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Entry[] journalsList = mapFromJson(content, Entry[].class);
        assertTrue(journalsList.length > 0);
    }


    @Test
    public void createProduct() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String uri = "http://localhost:8080/api/entry";
        Entry entry = new Entry();
        entry.setClassIndent("05");
        entry.setTitle("Class 05");
        CaseFile caseFile = new CaseFile();
        caseFile.setCaseYear("2016");
        caseFile.setCaseSequenceNumber("75");
        caseFile.setTitle("Kindergarten application Jack Hill");
        caseFile.setPublicTitle("Kindergarten application *****");
        entry.setCaseFile(caseFile);
        Records record = new Records();
        record.setRecordYear("2016");
        record.setRecordSequenceNumber("141");
        record.setRecordNumber("1");
        record.setTitle("Application form Jack Hill");
        record.setPublicTitle("Application form *****");
        record.setRecordDate("2016-03-17");
        LinkedHashMap<String, String> party = new LinkedHashMap<>();
        party.put("partyType", "sender");
        party.put("partyName", "William Hill");
        LinkedHashMap<String, String> party2 = new LinkedHashMap<>();
        party2.put("partyType", "recipient");
        party2.put("partyName", "Kindergarten X");
        ArrayList<LinkedHashMap<String, String>> parties = new ArrayList<>();
        record.setParties(parties);
        parties.add(party);
        parties.add(party2);
        entry.setRecord(record);

        String inputJson = mapToJson(entry);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "HTTP/1.1 201 CREATED");
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

}
