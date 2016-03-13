package com.omnicrola.voxel.main.settings;

import com.omnicrola.voxel.settings.GameConstants;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by omnic on 3/12/2016.
 */
public class GameSettingsRepository {
    public static GameSettings load() {
        try {
            File settingsFile = getFile();
            if (settingsFile.exists()) {
                return getGameSettings(settingsFile);
            } else {
                GameSettings gameSettings = new GameSettings();
                save(gameSettings);
                return gameSettings;
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return new GameSettings();
    }

    private static File getFile() {
        return new File(GameConstants.SETTINGS_FILE);
    }

    public static void save(GameSettings gameSettings) {
        try {
            JAXBContext context = getContext();
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(gameSettings, getFile());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private static GameSettings getGameSettings(File settingsFile) throws JAXBException {
        JAXBContext context = getContext();
        Unmarshaller unmarshaller = context.createUnmarshaller();
        GameSettings gameSettings = (GameSettings) unmarshaller.unmarshal(settingsFile);
        return gameSettings;
    }

    public static JAXBContext getContext() throws JAXBException {
        return JAXBContext.newInstance(GameSettings.class);
    }
}
