package malfu.bc_particle.config;

public record ParticleSettings(
        String particleType,
        float zAddition,
        float xAddition,
        float yAddition,
        float localYaw,
        float pitchAddition,
        float rollSet
) {
    // A default instance if no settings are found for an animation
    public static final ParticleSettings DEFAULT = new ParticleSettings(
            "none",
            0.0F,
            0.0F,
            0.0F,
            0.0f,
            0.0f,
            0.0f
    );
}
