package ru.kuzds.jaxb;

import jakarta.xml.bind.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class XmlHelper {
    private static final ConcurrentMap<Class<?>, JAXBContext> jaxbContexts = new ConcurrentHashMap<>(50);

    private static JAXBContext getJaxbContext(Class<?> clazz) {
        return jaxbContexts.computeIfAbsent(clazz, key -> {
            try {
                return JAXBContext.newInstance(clazz);
            } catch (JAXBException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private static Unmarshaller createUnmarshaller(Class<?> clazz) throws JAXBException {
        JAXBContext jaxbContext = getJaxbContext(clazz);
        return jaxbContext.createUnmarshaller();
    }

    private static Marshaller createMarshaller(Class<?> clazz) throws JAXBException {
        JAXBContext jaxbContext = getJaxbContext(clazz);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        return marshaller;
    }

    public static <T> T unmarshall(String body, Class<T> clazz) {
        try {
            Unmarshaller unmarshaller = createUnmarshaller(clazz);
            StreamSource source = new StreamSource(new StringReader(body));
            JAXBElement<T> jaxbElement = unmarshaller.unmarshal(source, clazz);
            return jaxbElement.getValue();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static <T> String marshal(JAXBElement<T> element) {
        try {
            Marshaller marshaller = createMarshaller(element.getDeclaredType());
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(element, stringWriter);
            return stringWriter.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
