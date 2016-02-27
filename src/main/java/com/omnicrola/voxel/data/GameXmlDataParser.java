package com.omnicrola.voxel.data;

import com.omnicrola.voxel.data.level.LevelDefinition;
import com.omnicrola.voxel.data.level.LevelDefinitionRepository;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.data.units.XmlGameDefinitions;
import com.omnicrola.voxel.entities.commands.BuildUnitCommand;
import com.omnicrola.voxel.entities.commands.BuildVoxelCommand;
import com.omnicrola.voxel.entities.commands.EntityCommand;
import com.omnicrola.voxel.entities.control.old.AutomatedWeaponControlFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.URL;
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

    public LevelDefinitionRepository loadLevels(String levelPath) {
        try {
            levelPath = adjustRelativePath(levelPath);
            Unmarshaller unmarshaller = getUnmarshaller();
            List<File> allFilesInDirectory = Arrays.asList(new File(levelPath).listFiles());
            List<LevelDefinition> levels = allFilesInDirectory
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

    private String adjustRelativePath(String levelPath) {
        URL systemResource = ClassLoader.getSystemResource("");
        String jarRoot = systemResource.getPath();
        levelPath = jarRoot + levelPath;
        return levelPath;
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
            return JAXBContext.newInstance(
                    XmlGameDefinitions.class,
                    LevelDefinition.class,
                    EntityCommand.class,
                    BuildUnitCommand.class,
                    BuildVoxelCommand.class,
                    AutomatedWeaponControlFactory.class
            );
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}
