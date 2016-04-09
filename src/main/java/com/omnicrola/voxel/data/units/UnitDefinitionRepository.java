package com.omnicrola.voxel.data.units;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Eric on 1/18/2016.
 */
public class UnitDefinitionRepository {

    private static final Logger LOGGER = Logger.getLogger(UnitDefinitionRepository.class.getName());

    private XmlGameDefinitions gameDefinitions;

    public UnitDefinitionRepository(XmlGameDefinitions gameDefinitions) {
        this.gameDefinitions = gameDefinitions;
    }

    public WeaponDefinition getWeaponDefinition(int weaponId) {
        return findInStream(this.gameDefinitions.weapons, w -> w.getId() == weaponId, WeaponDefinition.NULL);
    }

    public ProjectileDefinition getProjectileDefinition(int projectileId) {
        return findInStream(this.gameDefinitions.projectiles, p -> p.getId() == projectileId, ProjectileDefinition.NONE);
    }

    public UnitDefinition getUnitDefinition(int definitionId) {
        return findInStream(this.gameDefinitions.units, u -> u.getId() == definitionId, UnitDefinition.NONE);
    }

    public StructureDefinition getBuildingDefinition(int definitionId) {
        return findInStream(this.gameDefinitions.structures, s -> s.getGlobalId() == definitionId, StructureDefinition.NONE);
    }

    private <T> T findInStream(List<T> haystack, Predicate<T> pitchfork, T defaultValue) {
        Optional<T> firstItem = haystack.stream().filter(pitchfork).findFirst();
        if (firstItem.isPresent()) {
            return firstItem.get();
        } else {
            LOGGER.log(Level.WARNING, "Could not find unit definition for ID: " + pitchfork);
            return defaultValue;
        }
    }

    XmlGameDefinitions getGameDefinitions() {
        return gameDefinitions;
    }
}
