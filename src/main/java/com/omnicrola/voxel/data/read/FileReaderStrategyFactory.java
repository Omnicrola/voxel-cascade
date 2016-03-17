package com.omnicrola.voxel.data.read;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.ProtectionDomain;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Eric on 3/16/2016.
 */
public class FileReaderStrategyFactory {

    private static final Logger logger = Logger.getLogger(FileReaderStrategyFactory.class.getName());

    public static IFileReaderStrategy build() {
        ProtectionDomain protectionDomain = FileReaderStrategyFactory.class.getProtectionDomain();
        URL sourceCodeLocation = protectionDomain.getCodeSource().getLocation();
        final File sourceFile = new File(sourceCodeLocation.getPath());

        if (sourceFile.isFile()) {
            // we are a JAR file
            try {
                JarFile jar = new JarFile(sourceFile);
                return new ProductionFileReaderStrategy(jar);
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage());
                return null;
            }
        } else {
            // we are running from IDE
            return new DevelopmentFileReaderStrategy();
        }
    }
}
