package com.omnicrola.voxel.data;

import com.omnicrola.voxel.data.level.LevelDefinition;
import com.omnicrola.voxel.data.level.LevelDefinitionRepository;
import com.omnicrola.voxel.data.read.IFileReaderStrategy;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.data.units.XmlGameDefinitions;
import com.omnicrola.voxel.entities.commands.BuildStructureCommand;
import com.omnicrola.voxel.entities.commands.BuildUnitCommand;
import com.omnicrola.voxel.entities.commands.BuildVoxelCommand;
import com.omnicrola.voxel.entities.commands.EntityCommand;
import com.omnicrola.voxel.entities.control.AutomatedWeaponControlFactory;
import com.omnicrola.voxel.entities.control.fx.ParticleEffectControlFactory;
import com.omnicrola.voxel.entities.control.move.RotationControlFactory;
import com.omnicrola.voxel.entities.control.resources.PassiveHarvestControlFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by Eric on 1/18/2016.
 */
public class GameXmlDataParser {

    private static final Logger logger = Logger.getLogger(GameXmlDataParser.class.getName());

    private final IFileReaderStrategy fileReaderStrategy;
    private JAXBContext jaxbContext;

    public GameXmlDataParser(IFileReaderStrategy fileReaderStrategy) {
        this.fileReaderStrategy = fileReaderStrategy;
        this.jaxbContext = getJaxContext();
    }

    public UnitDefinitionRepository loadDefinitions(InputStream inputStream) {
        try {
            logger.log(Level.INFO, "Loading units");
            Unmarshaller unmarshaller = getUnmarshaller();
            XmlGameDefinitions gameDefinitions = (XmlGameDefinitions) unmarshaller.unmarshal(inputStream);
            UnitDefinitionRepository unitDefinitionRepository = new UnitDefinitionRepository(gameDefinitions);
            return unitDefinitionRepository;
        } catch (JAXBException e) {
            logger.log(Level.SEVERE, "Failed to read units XML. " + e.getMessage());
        }
        return null;
    }

    private Unmarshaller getUnmarshaller() throws JAXBException {
        return jaxbContext.createUnmarshaller();
    }

    public LevelDefinitionRepository loadLevels(String levelPath) {
        try {
            logger.log(Level.INFO, "Loading levels from '" + levelPath + "'");
            Unmarshaller unmarshaller = getUnmarshaller();
            List<File> allFilesInDirectory = this.fileReaderStrategy.getDirectoryContents(levelPath);
            logger.log(Level.INFO, "Found " + allFilesInDirectory.size() + " files in level directory");
            List<LevelDefinition> levels = allFilesInDirectory
                    .stream()
                    .filter(f -> f.getName().endsWith(".level"))
                    .map(f -> this.fileReaderStrategy.getInputStream(f))
                    .map(stream -> loadSingleLevel(stream, unmarshaller))
                    .collect(Collectors.toList());
            logger.log(Level.INFO, "Successfully loaded " + levels.size() + " levels.");
            return new LevelDefinitionRepository(levels);
        } catch (JAXBException e) {
            logger.log(Level.SEVERE, "Level load failure: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private LevelDefinition loadSingleLevel(InputStream stream, Unmarshaller unmarshaller) {
        try {
            return (LevelDefinition) unmarshaller.unmarshal(stream);
        } catch (JAXBException e) {
            logger.log(Level.SEVERE, e.getMessage());
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
            logger.log(Level.SEVERE, e.getMessage());
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
                    // commands
                    EntityCommand.class,
                    BuildUnitCommand.class,
                    BuildStructureCommand.class,
                    BuildVoxelCommand.class,
                    // controls
                    AutomatedWeaponControlFactory.class,
                    RotationControlFactory.class,
                    ParticleEffectControlFactory.class,
                    PassiveHarvestControlFactory.class
            );
        } catch (JAXBException e) {
            logger.log(Level.SEVERE, e.getMessage());
            return null;
        }
    }
}
