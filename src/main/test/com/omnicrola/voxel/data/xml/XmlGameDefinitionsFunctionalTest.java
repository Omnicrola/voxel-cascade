package com.omnicrola.voxel.data.xml;

import com.omnicrola.util.TestTools;
import org.junit.Test;

/**
 * Created by Eric on 1/18/2016.
 */
public class XmlGameDefinitionsFunctionalTest extends TestTools {

    @Test
    public void testWritingAnEntityCanBeReadBackIn() throws Exception {
        XmlGameDefinitions xmlGameDefinitions = new XmlGameDefinitions();
        UnitDefinition entityDefinition = createUnitDefinition();
        xmlGameDefinitions.units.add(entityDefinition);
    }

    private UnitDefinition createUnitDefinition() {
        UnitDefinition entityDefinition = new UnitDefinition();
        return entityDefinition;
    }

}