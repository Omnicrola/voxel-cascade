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
        try {
            this.jaxbContext = JAXBContext.newInstance();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
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
            marshaller.marshal(gameDefinitions, stream);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}
