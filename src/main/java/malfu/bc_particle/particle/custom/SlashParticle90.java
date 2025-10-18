package malfu.bc_particle.particle.custom;


import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import malfu.bc_particle.particle.ModParticles;
import malfu.bc_particle.particle.parameters.SlashParticleEffect;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.Locale;

@Environment(EnvType.CLIENT)
public class SlashParticle90 extends SlashParticle180 {


    SlashParticle90(ClientWorld world, double x, double y, double z, float scale, float pitch, float yaw, float localYaw, float roll, boolean light, String colorHex, SpriteProvider spriteProvider) {
        super(world, x, y, z, scale, pitch, yaw, localYaw, roll, light, colorHex, spriteProvider);
    }

    @Environment(EnvType.CLIENT)
    public static class Provider implements ParticleFactory<SlashParticleEffect> {
        private final SpriteProvider spriteProvider;

        public Provider(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SlashParticleEffect settings, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new SlashParticle90(clientWorld, d, e, f, settings.getScale(), settings.getPitch(), settings.getYaw(), settings.getLocalYaw() , settings.getRoll(),
                    settings.getLight(), settings.getColorHex(), spriteProvider);
        }
    }
}
