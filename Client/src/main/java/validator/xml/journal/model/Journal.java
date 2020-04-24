package validator.xml.journal.model;

import lombok.Data;

@Data
public class Journal {

    private String classIndent;

    private String title;

    private CaseFile caseFile;

    private Records record;

}
