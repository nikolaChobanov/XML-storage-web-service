package docu.journal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class Records {

    @JsonView(Views.Public.class)
    private String recordYear;
    @JsonView(Views.Public.class)
    private String recordSequenceNumber;
    @JsonView(Views.Public.class)
    private String recordNumber;
    @JsonView(Views.Internal.class)
    private String title;
    @JsonView(Views.PublicData.class)
    public String publicTitle;
    @JsonView(Views.Public.class)
    private String recordDate;
    @JsonView(Views.Public.class)
    ArrayList<LinkedHashMap<String, String>> parties;


    @SuppressWarnings("unchecked")
    @JsonProperty("record")
    private void unpackNes(Map<String, Object> brand) {
        this.recordYear = (String) brand.get("recordYear");
        this.recordSequenceNumber = (String) brand.get("recordSequenceNumber");
        this.recordNumber = (String) brand.get("recordNumber");
        this.title = (String) brand.get("title");
        this.publicTitle = (String) brand.get("publicTitle");
        this.recordDate = (String) brand.get("recordDate");
        this.parties = (ArrayList<LinkedHashMap<String, String>>) brand.get("parties");
    }
}
