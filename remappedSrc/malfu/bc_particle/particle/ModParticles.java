package malfu.bc_particle.particle;


import com.mojang.serialization.Codec;
import malfu.bc_particle.BetterCombatParticleMods;
import malfu.bc_particle.particle.parameters.SlashParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticles {

    public static final ParticleType<SlashParticleEffect> BOTSTAB = new ParticleType<SlashParticleEffect>(false, SlashParticleEffect.PARAMETERS_FACTORY) {
        public Codec<SlashParticleEffect> getCodec() {return SlashParticleEffect.CODEC(BOTSTAB);}
    };
    public static final ParticleType<SlashParticleEffect> BOTSLASH45 = new ParticleType<SlashParticleEffect>(false, SlashParticleEffect.PARAMETERS_FACTORY) {
        public Codec<SlashParticleEffect> getCodec() {return SlashParticleEffect.CODEC(BOTSLASH45);}
    };
    public static final ParticleType<SlashParticleEffect> BOTSLASH90 = new ParticleType<SlashParticleEffect>(false, SlashParticleEffect.PARAMETERS_FACTORY) {
        public Codec<SlashParticleEffect> getCodec() {return SlashParticleEffect.CODEC(BOTSLASH90);}
    };
    public static final ParticleType<SlashParticleEffect> BOTSLASH180 = new ParticleType<SlashParticleEffect>(false, SlashParticleEffect.PARAMETERS_FACTORY) {
        public Codec<SlashParticleEffect> getCodec() {return SlashParticleEffect.CODEC(BOTSLASH180);}
    };
    public static final ParticleType<SlashParticleEffect> BOTSLASH270 = new ParticleType<SlashParticleEffect>(false, SlashParticleEffect.PARAMETERS_FACTORY) {
        public Codec<SlashParticleEffect> getCodec() {return SlashParticleEffect.CODEC(BOTSLASH270);}
    };
    public static final ParticleType<SlashParticleEffect> BOTSLASH360 = new ParticleType<SlashParticleEffect>(false, SlashParticleEffect.PARAMETERS_FACTORY) {
        public Codec<SlashParticleEffect> getCodec() {return SlashParticleEffect.CODEC(BOTSLASH360);}
    };

    public static final ParticleType<SlashParticleEffect> TOPSTAB = new ParticleType<SlashParticleEffect>(false, SlashParticleEffect.PARAMETERS_FACTORY) {
        public Codec<SlashParticleEffect> getCodec() {return SlashParticleEffect.CODEC(TOPSTAB);}
    };
    public static final ParticleType<SlashParticleEffect> TOPSLASH45 = new ParticleType<SlashParticleEffect>(false, SlashParticleEffect.PARAMETERS_FACTORY) {
        public Codec<SlashParticleEffect> getCodec() {return SlashParticleEffect.CODEC(TOPSLASH45);}
    };
    public static final ParticleType<SlashParticleEffect> TOPSLASH90 = new ParticleType<SlashParticleEffect>(false, SlashParticleEffect.PARAMETERS_FACTORY) {
        public Codec<SlashParticleEffect> getCodec() {return SlashParticleEffect.CODEC(TOPSLASH90);}
    };
    public static final ParticleType<SlashParticleEffect> TOPSLASH180 = new ParticleType<SlashParticleEffect>(false, SlashParticleEffect.PARAMETERS_FACTORY) {
        public Codec<SlashParticleEffect> getCodec() {return SlashParticleEffect.CODEC(TOPSLASH180);}
    };
    public static final ParticleType<SlashParticleEffect> TOPSLASH270 = new ParticleType<SlashParticleEffect>(false, SlashParticleEffect.PARAMETERS_FACTORY) {
        public Codec<SlashParticleEffect> getCodec() {return SlashParticleEffect.CODEC(TOPSLASH270);}
    };
    public static final ParticleType<SlashParticleEffect> TOPSLASH360 = new ParticleType<SlashParticleEffect>(false, SlashParticleEffect.PARAMETERS_FACTORY) {
        public Codec<SlashParticleEffect> getCodec() {return SlashParticleEffect.CODEC(TOPSLASH360);}
    };



    public static void registerParticles () {
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(BetterCombatParticleMods.MOD_ID, "botstab"),
                BOTSTAB);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(BetterCombatParticleMods.MOD_ID, "botslash45"),
                BOTSLASH45);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(BetterCombatParticleMods.MOD_ID, "botslash90"),
                BOTSLASH90);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(BetterCombatParticleMods.MOD_ID, "botslash180"),
                BOTSLASH180);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(BetterCombatParticleMods.MOD_ID, "botslash270"),
                BOTSLASH270);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(BetterCombatParticleMods.MOD_ID, "botslash360"),
                BOTSLASH360);

        Registry.register(Registries.PARTICLE_TYPE, new Identifier(BetterCombatParticleMods.MOD_ID, "topstab"),
                TOPSTAB);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(BetterCombatParticleMods.MOD_ID, "topslash45"),
                TOPSLASH45);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(BetterCombatParticleMods.MOD_ID, "topslash90"),
                TOPSLASH90);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(BetterCombatParticleMods.MOD_ID, "topslash180"),
                TOPSLASH180);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(BetterCombatParticleMods.MOD_ID, "topslash270"),
                TOPSLASH270);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(BetterCombatParticleMods.MOD_ID, "topslash360"),
                TOPSLASH360);
    }
}
