package validator.xml.journal;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import validator.xml.journal.parse.Parser;
import validator.xml.journal.serverlink.LoggerSetUp;

import javax.xml.XMLConstants;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Logger;

public class ClientEnd {


    private static Logger LOGGER = LoggerSetUp.setUp();

    public static void main(String[] args) {

     /*   File inFile = null;
        Path at = null;
        if (1 < args.length) {
            inFile = new File(args[0]);
            at = Paths.get(args[1]);
        } else {
            System.err.println("Invalid arguments count:" + args.length);
            System.exit(0);
        }

*/
        Scanner in = new Scanner(System.in);

        String xml = null;
        String xsd = null;
        if (in.hasNextLine()) {
            xsd = in.nextLine();
        }
        if (in.hasNextLine()) {
            xml = in.nextLine();
        }
        System.out.println("The input schema file is " + " " + xsd);
        System.out.println("The input xml file is " + " " + xml);

        File xsdSchema = new File(xsd);
        Path xmlPath = Paths.get(xml);


        try (Reader reader = Files.newBufferedReader(xmlPath);) {

            String schemaLang = XMLConstants.W3C_XML_SCHEMA_NS_URI;
            SchemaFactory factory = SchemaFactory.newInstance(schemaLang);
            Schema schema = factory.newSchema(xsdSchema);

            Validator validator = schema.newValidator();

            SAXSource source = new SAXSource(new InputSource(reader));
            validator.validate(source);

            System.out.println("The document was validated successfully");

            Parser runner = new Parser();
            runner.parseUsers(xmlPath);

        } catch (SAXException ex) {
            LOGGER.info("The document failed to validate" + ex.getStackTrace());
            System.out.println("The document failed to validate");
        } catch (IOException ex) {
            LOGGER.info("Error occurred while reading the document" + ex.getStackTrace());
            System.out.println("Error occurred while reading the document");
        }


    }
}
