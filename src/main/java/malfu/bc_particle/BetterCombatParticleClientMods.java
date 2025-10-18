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
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.List;

public class BetterCombatParticleClientMods implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        //Particle
        ParticleFactoryRegistry.getInstance().register(ModParticles.STAB, StabParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SLASH45, SlashParticle45.Provider::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SLASH90, SlashParticle90.Provider::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SLASH180, SlashParticle180.Provider::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SLASH270, SlashParticle270.Provider::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SLASH360, SlashParticle360.Provider::new);


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
                System.out.println(animationName);


                // 2. Spawn the particle
                if(BetterCombatParticleMods.config.triggerParticle){
                    BCParticleUtil.spawnParticleForSettings(player, hand, settings, (float) hand.attributes().attackRange(),
                            colorLightSettings.light(), colorLightSettings.colorHex());
                }

            }
        }));

        // Register client packet handler
        ClientNetworkHandler.register();
    }
}
