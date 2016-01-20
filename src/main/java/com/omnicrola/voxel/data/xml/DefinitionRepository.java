package com.omnicrola.voxel.data.xml;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Created by Eric on 1/18/2016.
 */
public class DefinitionRepository {
    private XmlGameDefinitions gameDefinitions;

    public DefinitionRepository(XmlGameDefinitions gameDefinitions) {
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

    private <T> T findInStream(List<T> haystack, Predicate<T> pitchfork, T defaultValue) {
        Optional<T> firstItem = haystack.stream().filter(pitchfork).findFirst();
        if (firstItem.isPresent()) {
            return firstItem.get();
        } else {
            return defaultValue;
        }
    }

    XmlGameDefinitions getGameDefinitions() {
        return gameDefinitions;
    }
}
