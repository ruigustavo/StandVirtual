package Crawler;



import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;

public class XMLUnmarshal {
    public static Cars doUnmarshal(String xmlStr) {

        try {

            StringReader stringReader = new StringReader(xmlStr);
            XMLInputFactory xif = XMLInputFactory.newInstance();
            XMLStreamReader xsr = xif.createXMLStreamReader(stringReader);

            JAXBContext jaxbContext = JAXBContext.newInstance(Cars.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (Cars) jaxbUnmarshaller.unmarshal(xsr);

        } catch(JAXBException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

        return null;
    }
}