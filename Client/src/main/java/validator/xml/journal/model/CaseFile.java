package validator.xml.journal.model;

import lombok.Data;

@Data
public class CaseFile {

    private String caseYear;

    private String caseSequenceNumber;

    private String title;

    public String publicTitle;
}
