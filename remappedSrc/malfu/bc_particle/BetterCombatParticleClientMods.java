package malfu.bc_particle;

import malfu.bc_particle.config.*;
import malfu.bc_particle.network.ClientNetworkHandler;
import malfu.bc_particle.particle.BCParticleUtil;
import malfu.bc_particle.particle.ModParticles;
import malfu.bc_particle.particle.custom.*;
import net.bettercombat.api.WeaponAttributes;
import net.bettercombat.api.client.BetterCombatClientEvents;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

import java.util.List;

public class BetterCombatParticleClientMods implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        //Particle
        ParticleFactoryRegistry.getInstance().register(ModParticles.BOTSTAB, StabParticleBot.Provider::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.BOTSLASH45, BotSlashParticle45.Provider::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.BOTSLASH90, BotSlashParticle90.Provider::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.BOTSLASH180, BotSlashParticle180.Provider::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.BOTSLASH270, BotSlashParticle270.Provider::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.BOTSLASH360, BotSlashParticle360.Provider::new);

        ParticleFactoryRegistry.getInstance().register(ModParticles.TOPSTAB, StabParticleTop.Provider::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.TOPSLASH45, TopSlashParticle45.Provider::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.TOPSLASH90, TopSlashParticle90.Provider::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.TOPSLASH180, TopSlashParticle180.Provider::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.TOPSLASH270, TopSlashParticle270.Provider::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.TOPSLASH360, TopSlashParticle360.Provider::new);


        BetterCombatClientEvents.ATTACK_HIT.register(((player, hand, list, entity) -> {
            WeaponAttributes.Attack attack = hand.attack();
            String animationName = attack.animation();

            //hand.itemStack().hasEnchantments();
            //player.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED);
            //Identifier itemId = Registries.ITEM.getId(hand.itemStack().getItem());
            //String fullName = itemId.toString();

            if (animationName != null) {
                // 1. Get the settings for this specific animation
                List<ParticleSettings> settings = ParticleSettingsLoader.INSTANCE.getSettings(animationName);
                ColorLightSettings colorLightSettings = ColorChangerSettings.resolveWeaponEffects(hand.itemStack());


                // 2. Spawn the particle
                if(BetterCombatParticleMods.config.triggerParticle){
                    BCParticleUtil.spawnParticleForSettings(player, hand, settings, (float) hand.attributes().attackRange(),
                            colorLightSettings.light(), colorLightSettings.colorHex(), colorLightSettings.colorHexSec());
                }

            }
        }));

        // Register client packet handler
        ClientNetworkHandler.register();
    }
}
