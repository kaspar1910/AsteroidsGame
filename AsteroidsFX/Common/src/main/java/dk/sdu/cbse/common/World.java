package dk.sdu.cbse.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class World {

    private final Map<UUID, Entity> entities = new ConcurrentHashMap<>();

    public void addEntity(Entity entity) {
        entities.put(entity.getId(), entity);
    }

    public void removeEntity(Entity entity) {
        if (entity != null) {
            entities.remove(entity.getId());
        }
    }

    public Collection<Entity> getEntities() {
        return entities.values();
    }

    public List<Entity> getEntitiesByClass(Class<? extends Entity> entityType) {
        List<Entity> matchingEntities = new ArrayList<>();

        for (Entity entity : entities.values()) {
            if (entityType.isInstance(entity)) {
                matchingEntities.add(entity);
            }
        }

        return matchingEntities;
    }
}