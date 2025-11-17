package malfu.bc_particle.config;

public record ColorLightSettings(String colorHex, String colorHexSec, boolean light) {
    public static final ColorLightSettings DEFAULT = new ColorLightSettings("FFFFFF", "D9D9D9" ,false);
}
