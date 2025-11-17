package malfu.bc_particle.particle.parameters;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;

import java.util.Locale;

public class SlashParticleEffect implements ParticleEffect {
    public static final ParticleEffect.Factory<SlashParticleEffect> PARAMETERS_FACTORY = new ParticleEffect.Factory<SlashParticleEffect>() {
        public SlashParticleEffect read(ParticleType<SlashParticleEffect> particleType, StringReader stringReader) throws CommandSyntaxException {
            stringReader.expect(' ');
            float scale = stringReader.readFloat();
            stringReader.expect(' ');
            float pitch = stringReader.readFloat();
            stringReader.expect(' ');
            float yaw = stringReader.readFloat();
            stringReader.expect(' ');
            float localYaw = stringReader.readFloat();
            stringReader.expect(' ');
            float roll = stringReader.readFloat();

            stringReader.expect(' ');
            boolean light = stringReader.readBoolean();
            stringReader.expect(' ');
            String colorHex = stringReader.readString();

            return new SlashParticleEffect(particleType ,scale, pitch, yaw, localYaw, roll, light, colorHex);
        }

        public SlashParticleEffect read(ParticleType<SlashParticleEffect> particleType, PacketByteBuf packetByteBuf) {
            return new SlashParticleEffect(particleType, packetByteBuf.readFloat(), packetByteBuf.readFloat(),
                    packetByteBuf.readFloat(), packetByteBuf.readFloat(), packetByteBuf.readFloat(), packetByteBuf.readBoolean(), packetByteBuf.readString());
        }
    };

    private final ParticleType<SlashParticleEffect> type;
    private final float pitch; // Rotation around X axis
    private final float yaw;   // Rotation around Y axis
    private final float localYaw;
    private final float roll;
    private final float scale;
    private final boolean light;
    private final String colorHex;

    public SlashParticleEffect(ParticleType<SlashParticleEffect> type, float scale, float pitch, float yaw, float localYaw, float roll, boolean light, String colorHex) {
        this.type = type;
        this.scale = scale;
        this.pitch = pitch;
        this.yaw = yaw;
        this.localYaw = localYaw;
        this.roll = roll;
        this.light = light;
        this.colorHex = colorHex;
    }

    @Override
    public ParticleType<SlashParticleEffect> getType() {
        return type;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeFloat(this.scale);
        buf.writeFloat(this.pitch);
        buf.writeFloat(this.yaw);
        buf.writeFloat(this.localYaw);
        buf.writeFloat(this.roll);
        buf.writeBoolean(this.light);
        buf.writeString(this.colorHex);
    }

    @Override
    public String asString() {
        return String.format(Locale.ROOT, "%s %.0f %.0f %.0f %.0f %.0f", Registries.PARTICLE_TYPE.getKey(this.getType()),
                this.scale, this.pitch, this.yaw, this.localYaw, this.roll, this.light, this.colorHex);
    }

    public float getScale() {
        return this.scale;
    }

    public float getPitch() {
        return this.pitch;
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getLocalYaw() {
        return this.localYaw;
    }

    public float getRoll() {
        return this.roll;
    }

    public boolean getLight() {return this.light;}

    public String getColorHex() {return this.colorHex;}

    public static Codec<SlashParticleEffect> CODEC(ParticleType<SlashParticleEffect> particleType) {
        return RecordCodecBuilder.create((instance) -> instance.group(
                Codec.FLOAT.fieldOf("scale").forGetter(SlashParticleEffect::getScale),
                Codec.FLOAT.fieldOf("pitch").forGetter(SlashParticleEffect::getPitch),
                Codec.FLOAT.fieldOf("yaw").forGetter(SlashParticleEffect::getYaw),
                Codec.FLOAT.fieldOf("localYaw").forGetter(SlashParticleEffect::getLocalYaw),
                Codec.FLOAT.fieldOf("roll").forGetter(SlashParticleEffect::getRoll),
                Codec.BOOL.fieldOf("light").forGetter(SlashParticleEffect::getLight),
                Codec.STRING.fieldOf("colorHex").forGetter(SlashParticleEffect::getColorHex)
        ).apply(instance, (scale, pitch, yaw, localYaw, roll, light, colorHex) ->
                new SlashParticleEffect(particleType, scale, pitch, yaw, localYaw, roll, light, colorHex)));
    }
}
