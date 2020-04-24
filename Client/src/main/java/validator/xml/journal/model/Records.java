package validator.xml.journal.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@Data
public class Records {

    private String recordYear;

    private String recordSequenceNumber;

    private String recordNumber;

    private String title;

    public String publicTitle;

    private String recordDate;

    ArrayList<LinkedHashMap<String, String>> parties;
}
