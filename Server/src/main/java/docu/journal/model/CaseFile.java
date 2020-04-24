package docu.journal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.Map;

@Data
public class CaseFile {

    @JsonView(Views.Public.class)
    private String caseYear;
    @JsonView(Views.Public.class)
    private String caseSequenceNumber;
    @JsonView(Views.Internal.class)
    private String title;
    @JsonView(Views.PublicData.class)
    public String publicTitle;


    @SuppressWarnings("unchecked")
    @JsonProperty("caseFile")
    private void unpackNeste(Map<String, Object> brand) {
        this.caseYear = (String) brand.get("caseYear");
        this.caseSequenceNumber = (String) brand.get("caseSequenceNumber");
        this.title = (String) brand.get("title");
        this.publicTitle = (String) brand.get("publicTitle");
    }
}
