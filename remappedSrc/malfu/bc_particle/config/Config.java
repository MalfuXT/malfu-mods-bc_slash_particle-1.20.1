package malfu.bc_particle.config;

import me.shedaniel.autoconfig.ConfigData;

@me.shedaniel.autoconfig.annotation.Config(name = "config")
public class Config implements ConfigData {
    public boolean triggerParticle = true;
    public boolean showOtherPlayerParticle = true;
    public float particle_alpha = 0.5f;
    public float light_particle_alpha = 0.8f;
}
