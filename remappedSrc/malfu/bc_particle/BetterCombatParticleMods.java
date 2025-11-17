package malfu.bc_particle;

import malfu.bc_particle.config.*;
import malfu.bc_particle.network.NetworkHandler;
import malfu.bc_particle.particle.ModParticles;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetterCombatParticleMods implements ModInitializer {
	public static final String MOD_ID = "bc_particle";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static Config config;

	@Override
	public void onInitialize() {
		AutoConfig.register(ConfigWrapper.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
		// Intuitive way to load a config :)
		config = AutoConfig.getConfigHolder(ConfigWrapper.class).getConfig().config;

		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(ParticleSettingsLoader.INSTANCE);
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(WeaponSettingsLoader.INSTANCE);
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(EnchantmentSettingsLoader.INSTANCE);
		ModParticles.registerParticles();
		NetworkHandler.register();
	}
}