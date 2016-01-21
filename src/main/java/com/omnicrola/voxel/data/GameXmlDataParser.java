package com.omnicrola.voxel.data;

import com.omnicrola.voxel.data.level.LevelDefinition;
import com.omnicrola.voxel.data.level.LevelDefinitionRepository;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.data.units.XmlGameDefinitions;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Eric on 1/18/2016.
 */
public class GameXmlDataParser {

    private JAXBContext jaxbContext;

    public GameXmlDataParser() {
        this.jaxbContext = getJaxContext();
    }

    public UnitDefinitionRepository loadDefinitions(InputStream inputStream) {
        try {
            Unmarshaller unmarshaller = getUnmarshaller();
            XmlGameDefinitions gameDefinitions = (XmlGameDefinitions) unmarshaller.unmarshal(inputStream);
            return new UnitDefinitionRepository(gameDefinitions);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Unmarshaller getUnmarshaller() throws JAXBException {
        return jaxbContext.createUnmarshaller();
    }

    public LevelDefinition loadLevels(String levelPath) {
        try {
            Unmarshaller unmarshaller = getUnmarshaller();
            List<LevelDefinition> levels = Arrays.asList(new File(levelPath).listFiles())
                    .stream()
                    .filter(f -> f.getName().endsWith(".level"))
                    .map(f -> getFileInputStream(f))
                    .map(stream -> loadSingleLevel(stream, unmarshaller))
                    .collect(Collectors.toList());
            return new LevelDefinitionRepository(levels);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    private LevelDefinition loadSingleLevel(InputStream stream, Unmarshaller unmarshaller) {
        try {
            return (LevelDefinition) unmarshaller.unmarshal(stream);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static FileInputStream getFileInputStream(File f) {
        try {
            return new FileInputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeDefinitions(OutputStream stream, XmlGameDefinitions gameDefinitions) {
        try {
            Marshaller marshaller = getMarshaller();
            marshaller.marshal(gameDefinitions, stream);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void writeLevel(OutputStream outputStream, LevelDefinition levelDefinition) {
        try {
            Marshaller marshaller = getMarshaller();
            marshaller.marshal(levelDefinition, outputStream);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private Marshaller getMarshaller() throws JAXBException {
        Marshaller marshaller = this.jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        return marshaller;
    }


    private static JAXBContext getJaxContext() {
        try {
            return JAXBContext.newInstance(XmlGameDefinitions.class, LevelDefinition.class);
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

}
