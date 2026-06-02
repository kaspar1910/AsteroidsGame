package dk.sdu.cbse.commonBullet;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.World;

public interface BulletSPI {
    void createBullet(Entity shooter, World world);
}