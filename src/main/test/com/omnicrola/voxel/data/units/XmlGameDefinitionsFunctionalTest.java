package com.omnicrola.voxel.data.units;

import com.jme3.math.ColorRGBA;
import com.omnicrola.util.TestTools;
import com.omnicrola.voxel.data.GameXmlDataParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Created by Eric on 1/18/2016.
 */
public class XmlGameDefinitionsFunctionalTest extends TestTools {
    private static final Random rand = new Random();
    private static final char[] CHARS = generateXmlCharacters();

    private File testFile;
    private GameXmlDataParser gameXmlDataParser;

    @Before
    public void setup() {
        this.gameXmlDataParser = new GameXmlDataParser();
        this.testFile = new File("units-test.units");
        this.testFile.deleteOnExit();
    }

    @After
    public void teardown() {
        if (this.testFile.exists()) {
            this.testFile.delete();
        }
    }

    @Test
    public void testWritingAnUnitCanBeReadBackIn() throws Exception {
        XmlGameDefinitions xmlGameDefinitions = new XmlGameDefinitions();
        UnitDefinition entityDefinition = createUnitDefinition();
        xmlGameDefinitions.units.add(entityDefinition);

        writeAndReloadDefinitions(xmlGameDefinitions);
    }

    @Test
    public void testWritingAWeaponCanBeReadBackIn() throws Exception {
        XmlGameDefinitions xmlGameDefinitions = new XmlGameDefinitions();
        WeaponDefinition entityDefinition = createWeaponDefinition();
        xmlGameDefinitions.weapons.add(entityDefinition);

        writeAndReloadDefinitions(xmlGameDefinitions);
    }

    @Test
    public void testWritingAProjectileCanBeReadBackIn() throws Exception {
        XmlGameDefinitions xmlGameDefinitions = new XmlGameDefinitions();
        ProjectileDefinition projectileDefinition = createProjectileDefinition();
        xmlGameDefinitions.projectiles.add(projectileDefinition);

        writeAndReloadDefinitions(xmlGameDefinitions);
    }

    private void writeAndReloadDefinitions(XmlGameDefinitions xmlGameDefinitions) throws FileNotFoundException {
        gameXmlDataParser.writeDefinitions(new FileOutputStream(this.testFile), xmlGameDefinitions);

        UnitDefinitionRepository definitionRepository = gameXmlDataParser.loadDefinitions(new FileInputStream(this.testFile));
        XmlGameDefinitions actualDefinitions = definitionRepository.getGameDefinitions();

        checkDefinitions(xmlGameDefinitions, actualDefinitions);
    }

    private void checkDefinitions(XmlGameDefinitions xmlGameDefinitions, XmlGameDefinitions actualDefinitions) {
        assertEquals(xmlGameDefinitions.units.size(), actualDefinitions.units.size());
        assertEquals(xmlGameDefinitions.weapons.size(), actualDefinitions.weapons.size());
        assertEquals(xmlGameDefinitions.projectiles.size(), actualDefinitions.projectiles.size());

    }

    private UnitDefinition createUnitDefinition() {
        UnitDefinition entityDefinition = new UnitDefinition();
        entityDefinition.globalId = randInt();
        entityDefinition.weaponId = randInt();
        entityDefinition.color = ColorRGBA.randomColor();
        entityDefinition.hitpoints = randF();
        entityDefinition.mass = randF();
        entityDefinition.modelGeometry = randS(50);
        entityDefinition.modelTexture = randS(50);
        entityDefinition.movementDefinition = createMovementDefinition();
        return entityDefinition;
    }

    private WeaponDefinition createWeaponDefinition() {
        WeaponDefinition weaponDefinition = new WeaponDefinition();
        weaponDefinition.globalId = randInt();
        weaponDefinition.projectileId = randInt();
        weaponDefinition.range = randF();
        weaponDefinition.range = randF();
        return weaponDefinition;
    }

    private ProjectileDefinition createProjectileDefinition() {
        ProjectileDefinition projectileDefinition = new ProjectileDefinition();
        projectileDefinition.damage = randF();
        projectileDefinition.globalId = randInt();
        projectileDefinition.model = randS(50);
        projectileDefinition.muzzleVelocity = randF();
        projectileDefinition.obeysGravity = randF() > 0.5f;
        projectileDefinition.texture = randS(50);
        return projectileDefinition;
    }

    private MovementDefinition createMovementDefinition() {
        MovementDefinition movementDefinition = new MovementDefinition();
        movementDefinition.maxVelocity = randF();
        movementDefinition.turnspeed = randF();
        return movementDefinition;
    }

    private String randS(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(CHARS[rand.nextInt(CHARS.length)]);
        }
        return stringBuilder.toString();
    }

    private float randF() {
        return rand.nextFloat();
    }

    private int randInt() {
        return rand.nextInt();
    }

    private static char[] generateXmlCharacters() {
        String s = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890- ";
        char[] chars = new char[s.length()];
        s.getChars(0, s.length(), chars, 0);
        return chars;
    }
}