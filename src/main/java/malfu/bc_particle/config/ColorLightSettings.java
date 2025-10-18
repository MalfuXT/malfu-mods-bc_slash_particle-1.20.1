package malfu.bc_particle.config;

public record ColorLightSettings(String colorHex, boolean light) {
    public static final ColorLightSettings DEFAULT = new ColorLightSettings("FFFFFF", false);
}
