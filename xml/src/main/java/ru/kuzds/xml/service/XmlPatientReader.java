package ru.kuzds.xml.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.kuzds.xml.dto.Patient;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class XmlPatientReader {

    public List<Patient> read(InputStream inputStream) {

        // Instantiate the Factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(inputStream);

            // optional, but recommended
            // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            // get <staff>
            NodeList list = doc.getElementsByTagName("patient");
            List<Patient> patients = new ArrayList<>();
            for (int ii = 0; ii < list.getLength(); ii++) {

                Node node = list.item(ii);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;

                    // get text
                    String firstName = element.getElementsByTagName("first_name").item(0).getTextContent();
                    String middleName = element.getElementsByTagName("middle_name").item(0).getTextContent();
                    String lastName = element.getElementsByTagName("last_name").item(0).getTextContent();
                    String birthday = element.getElementsByTagName("birthday").item(0).getTextContent();
                    String gender = element.getElementsByTagName("gender").item(0).getTextContent();
                    String phone = element.getElementsByTagName("phone").item(0).getTextContent();

                    patients.add(new Patient(firstName, middleName, lastName, birthday, gender, phone));
                }
            }
            return patients;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
