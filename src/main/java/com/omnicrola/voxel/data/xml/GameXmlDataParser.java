package com.omnicrola.voxel.data.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Eric on 1/18/2016.
 */
public class GameXmlDataParser {

    private JAXBContext jaxbContext;

    public GameXmlDataParser() {
        this.jaxbContext = getJaxContext();
    }

    public DefinitionRepository loadDefinitions(InputStream inputStream) {
        try {
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            XmlGameDefinitions gameDefinitions = (XmlGameDefinitions) unmarshaller.unmarshal(inputStream);
            return new DefinitionRepository(gameDefinitions);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeDefinitions(OutputStream stream, XmlGameDefinitions gameDefinitions) {
        try {
            Marshaller marshaller = this.jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(gameDefinitions, stream);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private static JAXBContext getJaxContext() {
        try {
            return JAXBContext.newInstance(XmlGameDefinitions.class);
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}
