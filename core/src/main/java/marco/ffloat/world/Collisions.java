package marco.ffloat.world;

import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Response;

public class Collisions {

    public static CollisionFilter player = (item, other) -> {
        if (item.userData instanceof Player && other.userData instanceof NPC)
            return Response.cross;
        return Response.slide;
    };

    public static CollisionFilter npc = (item, other) -> {
        if (item.userData instanceof Player && other.userData instanceof NPC)
            return Response.cross;
        return Response.slide;
    };

    public static CollisionFilter obstacle = (item, other) -> {
        if (item.userData instanceof Obstacle && other.userData instanceof Player)
            return Response.cross;
        return Response.slide;
    };

    public static CollisionFilter bullet = (item, other) -> {
        if (item.userData instanceof Bullet && other.userData instanceof Obstacle)
            return Response.touch;
        return Response.cross;
    };
}
