package validator.xml.journal.parse;

import org.xml.sax.SAXException;
import validator.xml.journal.serverlink.LoggerSetUp;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

//Class creating the SAX parser and starts the parsing
public class Parser {


    private static Logger LOGGER = LoggerSetUp.setUp();

    public void parseUsers(Path xmlPath) {

        SAXLocalNameCount handler = new SAXLocalNameCount();
        File xmlDocument = xmlPath.toFile();

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(xmlDocument, handler);

        } catch (SAXException | IOException ex) {
            System.out.println("Error occurred while parsing the document");
            LOGGER.info("Could not parse document" + ex.getStackTrace());
        } catch (ParserConfigurationException e) {
            LOGGER.info("Could not onfigure parser" + e.getStackTrace());
            System.out.println("Error occurred while configuring parser");
        }

    }
}