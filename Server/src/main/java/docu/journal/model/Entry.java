package docu.journal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Document(collection = "journals")
@JsonRootName(value = "class")
public class Entry {


    @JsonView(Views.Public.class)
    private String classIndent;
    @JsonView(Views.Public.class)
    private String title;

    @JsonView(Views.Public.class)
    private CaseFile caseFile;
    @JsonView(Views.Public.class)
    private Records record;


    @SuppressWarnings("unchecked")
    @JsonProperty("class")
    private void unpackNested(Map<String, Object> brand) {
        this.classIndent = (String) brand.get("classIdent");
        this.title = (String) brand.get("title");
    }

}



