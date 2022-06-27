package marco.ffloat.particle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class ParticleSystem {

    private static final int MAX_PARTICLES = 250;
    private static int currentCount;
    private static final List<IParticle> PARTICLES = new ArrayList<>();

    public static void addParticle(IParticle particle) {
        if (currentCount < MAX_PARTICLES) {
            PARTICLES.add(particle);
            currentCount++;
        }
    }

    public static void render(SpriteBatch batch, float delta) {
        PARTICLES.forEach(p -> p.render(batch, delta));
    }

    public static void tick(float delta) {
        PARTICLES.forEach(p -> p.tick(delta));

        for (int i = PARTICLES.size() - 1; i > 0; i--) {
            IParticle particle = PARTICLES.get(i);
            if (particle.isDead()) {
                PARTICLES.remove(particle);
                currentCount--;
            }
        }
    }

    public static void dispose() {
        PARTICLES.forEach(IParticle::dispose);
    }
}