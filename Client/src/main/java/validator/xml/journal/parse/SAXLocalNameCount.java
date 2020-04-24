package validator.xml.journal.parse;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import validator.xml.journal.model.CaseFile;
import validator.xml.journal.model.Journal;
import validator.xml.journal.model.Records;
import validator.xml.journal.model.SubClassesOfXMLFile;
import validator.xml.journal.serverlink.StoreInDataBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SAXLocalNameCount extends DefaultHandler {


    private Journal journal;
    private CaseFile caseFile;
    private Records record;
    //The whole list of parties
    private ArrayList<LinkedHashMap<String, String>> parties;
    //A single party
    private LinkedHashMap<String, String> party;
    //Contains a single element of data from the file
    private String content;
    //Helper enum to deal with the values that have the same name
    private SubClassesOfXMLFile currentSubClass;

    //Operations at the beginning of the tag
    @Override
    public void startElement(String uri, String localName,
                             String qName, Attributes attributes) throws SAXException {

        if ("journal".equals(qName)) {

        } else if (qName.equals("entry")) {
            journal = new Journal();
            currentSubClass = SubClassesOfXMLFile.CLASS;

        } else if (qName.equals("caseFile")) {
            caseFile = new CaseFile();
            currentSubClass = SubClassesOfXMLFile.CASEFILE;

        } else if (qName.equals("record")) {
            record = new Records();
            currentSubClass = SubClassesOfXMLFile.RECORD;


        } else if (qName.equals("parties")) {
            parties = new ArrayList<>();
        } else if (qName.equals("party")) {
            party = new LinkedHashMap<>();
        }
    }

    //Stores the data between the tags
    @Override
    public void characters(char[] ch, int start, int length) {

        content = new String(ch, start, length);
    }

    //Operations done at the closing tag
    @Override
    public void endElement(String uri, String localName,
                           String qName) {

        if (currentSubClass == SubClassesOfXMLFile.CLASS) {

            switch (qName) {

                case "classIdent":
                    journal.setClassIndent(content);
                    break;

                case "title":
                    journal.setTitle(content);
                    break;
            }
        }

        if (currentSubClass == SubClassesOfXMLFile.CASEFILE) {
            switch (qName) {


                case "caseFile":
                    journal.setCaseFile(caseFile);
                    break;

                case "caseYear":
                    caseFile.setCaseYear(content);
                    break;

                case "caseSequenceNumber":
                    caseFile.setCaseSequenceNumber(content);
                    break;

                case "title":
                    caseFile.setTitle(content);
                    break;

                case "publicTitle":
                    caseFile.setPublicTitle(content);
                    break;

            }
        }

        if (currentSubClass == SubClassesOfXMLFile.RECORD) {
            switch (qName) {


                case "record":
                    journal.setRecord(record);
                    break;


                case "recordYear":
                    record.setRecordYear(content);
                    break;

                case "recordSequenceNumber":
                    record.setRecordSequenceNumber(content);
                    break;

                case "recordNumber":
                    record.setRecordNumber(content);
                    break;

                case "title":
                    record.setTitle(content);
                    break;

                case "publicTitle":
                    record.setPublicTitle(content);
                    break;

                case "recordDate":
                    record.setRecordDate(content);
                    break;

                case "partyType":
                    party.put("partyType", content);
                    break;

                case "partyName":
                    party.put("partyName", content);
                    break;


                case "party":
                    parties.add(party);
                    break;

                case "parties":
                    record.setParties(parties);
                    break;

                case "entry":
                    try {
                        StoreInDataBase.addToDB(journal);
                    } catch (IOException e) {
                        System.out.println("Could not gain access to the server");
                    }
                    break;
            }

        }

//The java 12 new style switch statements
     /*   if (currentlyInClass) {

            switch (qName) {

                case "class" -> {
                    journal.setCaseFile(caseFile);
                }

                case "classIdent" -> {
                    journal.setClassIndent(content);
                }

                case "title" -> {
                    journal.setTitle(content);
                }
            }

        }

        if (currentlyInCaseFile) {
            switch (qName) {


                case "caseFile" -> {
                    journal.setCaseFile(caseFile);
                }

                case "caseYear" -> {
                    caseFile.setCaseYear(content);
                }

                case "caseSequenceNumber" -> {
                    caseFile.setCaseSequenceNumber(content);
                }

                case "title" -> {
                    caseFile.setTitle(content);
                }

                case "publicTitle" -> {
                    caseFile.setPublicTitle(content);
                }

            }
        }

        if (currentlyInRecord) {
            switch (qName) {


                case "record" -> {
                    journal.setRecord(record);
                }

                case "recordYear" -> {
                    record.setRecordYear(content);
                }

                case "recordSequenceNumber" -> {
                    record.setRecordSequenceNumber(content);
                }

                case "recordNumber" -> {
                    record.setRecordNumber(content);
                }

                case "title" -> {
                    record.setTitle(content);
                }

                case "publicTitle" -> {
                    record.setPublicTitle(content);
                }

                case "recordDate" -> {
                    record.setRecordDate(content);
                }

                case "parties" -> {
                    record.setParties(parties);
                }

                case "party" -> {
                    parties.add(party);
                }

                case "partyType" -> {
                    partyType = content;
                }

                case "partyName" -> {
                    party.put(partyType, content);
                }

                case "entry" -> {
                    users.add(journal);
                }

            }
        }*/
    }
}