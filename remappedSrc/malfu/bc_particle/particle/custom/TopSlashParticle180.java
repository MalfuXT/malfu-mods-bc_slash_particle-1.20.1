package malfu.bc_particle.particle.custom;


import malfu.bc_particle.particle.parameters.SlashParticleEffect;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;

@Environment(EnvType.CLIENT)
public class TopSlashParticle180 extends BotSlashParticle180 {


    TopSlashParticle180(ClientWorld world, double x, double y, double z, float scale, float pitch, float yaw, float localYaw, float roll, boolean light, String colorHex, SpriteProvider spriteProvider) {
        super(world, x, y, z, scale, pitch, yaw, localYaw, roll, light, colorHex, spriteProvider);
    }

    @Override
    public float setModelOffset() {
        return 0.005F;
    }

    @Environment(EnvType.CLIENT)
    public static class Provider implements ParticleFactory<SlashParticleEffect> {
        private final SpriteProvider spriteProvider;

        public Provider(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SlashParticleEffect settings, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new TopSlashParticle180(clientWorld, d, e, f, settings.getScale(), settings.getPitch(), settings.getYaw(), settings.getLocalYaw() ,
                    settings.getRoll(), settings.getLight(), settings.getColorHex(), spriteProvider);
        }
    }
}
