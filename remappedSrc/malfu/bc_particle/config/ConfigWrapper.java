package malfu.bc_particle.config;

import malfu.bc_particle.BetterCombatParticleMods;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(name = BetterCombatParticleMods.MOD_ID)
public class ConfigWrapper extends PartitioningSerializer.GlobalData {
    @ConfigEntry.Category("config")
    @ConfigEntry.Gui.Excluded
    public malfu.bc_particle.config.Config config = new malfu.bc_particle.config.Config();
}
