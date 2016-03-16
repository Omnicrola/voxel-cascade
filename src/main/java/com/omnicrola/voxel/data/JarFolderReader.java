package com.omnicrola.voxel.data;

import sun.misc.Launcher;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Eric on 3/14/2016.
 */
public class JarFolderReader {

    private static final Logger logger = Logger.getLogger(JarFolderReader.class.getName());

    public List<File> read(String folderPath) {
        try {
            return getFiles(folderPath);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Read error " + e.getMessage());
        } catch (URISyntaxException e) {
            logger.log(Level.SEVERE, "Exception: " + e.getMessage());
        }
        return null;
    }

    private List<File> getFiles(String path) throws IOException, URISyntaxException {
        final File sourceFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());

        if (sourceFile.isFile()) {  // Run with JAR file
            JarFile jar = new JarFile(sourceFile);
            logger.log(Level.INFO, "Loading from JAR");
            final Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
            ArrayList<File> files = new ArrayList<>();
            while (entries.hasMoreElements()) {
                final String name = entries.nextElement().getName();
                if (name.startsWith(path + "/")) { //filter according to the path
                    logger.log(Level.FINE, "Found : " + name);
                    files.add(new File(name));
                }
            }
            jar.close();
            return files;
        } else { // Run with IDE
            final URL url = Launcher.class.getResource("/" + path);
            if (url != null) {
                logger.log(Level.INFO, "Loading from IDE: " + url);
                final File apps = new File(url.toURI());
                List<File> files = Arrays.asList(apps.listFiles());
                return files;
            }
        }
        return null;
    }

}
