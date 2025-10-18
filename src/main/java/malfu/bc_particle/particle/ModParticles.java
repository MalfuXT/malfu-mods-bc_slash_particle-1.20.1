package malfu.bc_particle.particle;


import com.mojang.serialization.Codec;
import malfu.bc_particle.BetterCombatParticleMods;
import malfu.bc_particle.particle.custom.*;
import malfu.bc_particle.particle.parameters.SlashParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticles {

    public static final ParticleType<SlashParticleEffect> STAB = new ParticleType<SlashParticleEffect>(false, SlashParticleEffect.PARAMETERS_FACTORY) {
        public Codec<SlashParticleEffect> getCodec() {return SlashParticleEffect.CODEC(STAB);}
    };
    public static final ParticleType<SlashParticleEffect> SLASH45 = new ParticleType<SlashParticleEffect>(false, SlashParticleEffect.PARAMETERS_FACTORY) {
        public Codec<SlashParticleEffect> getCodec() {return SlashParticleEffect.CODEC(SLASH45);}
    };
    public static final ParticleType<SlashParticleEffect> SLASH90 = new ParticleType<SlashParticleEffect>(false, SlashParticleEffect.PARAMETERS_FACTORY) {
        public Codec<SlashParticleEffect> getCodec() {return SlashParticleEffect.CODEC(SLASH90);}
    };
    public static final ParticleType<SlashParticleEffect> SLASH180 = new ParticleType<SlashParticleEffect>(false, SlashParticleEffect.PARAMETERS_FACTORY) {
        public Codec<SlashParticleEffect> getCodec() {return SlashParticleEffect.CODEC(SLASH180);}
    };
    public static final ParticleType<SlashParticleEffect> SLASH270 = new ParticleType<SlashParticleEffect>(false, SlashParticleEffect.PARAMETERS_FACTORY) {
        public Codec<SlashParticleEffect> getCodec() {return SlashParticleEffect.CODEC(SLASH270);}
    };
    public static final ParticleType<SlashParticleEffect> SLASH360 = new ParticleType<SlashParticleEffect>(false, SlashParticleEffect.PARAMETERS_FACTORY) {
        public Codec<SlashParticleEffect> getCodec() {return SlashParticleEffect.CODEC(SLASH360);}
    };



    public static void registerParticles () {
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(BetterCombatParticleMods.MOD_ID, "stab"),
                STAB);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(BetterCombatParticleMods.MOD_ID, "slash45"),
                SLASH45);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(BetterCombatParticleMods.MOD_ID, "slash90"),
                SLASH90);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(BetterCombatParticleMods.MOD_ID, "slash180"),
                SLASH180);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(BetterCombatParticleMods.MOD_ID, "slash270"),
                SLASH270);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(BetterCombatParticleMods.MOD_ID, "slash360"),
                SLASH360);
    }
}
