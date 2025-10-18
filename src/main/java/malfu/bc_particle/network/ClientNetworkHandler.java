package malfu.bc_particle.network;

import malfu.bc_particle.BetterCombatParticleMods;
import malfu.bc_particle.config.ParticleSettings;
import malfu.bc_particle.config.ParticleSettingsLoader;
import malfu.bc_particle.particle.ModParticles;
import malfu.bc_particle.particle.custom.*;
import malfu.bc_particle.particle.parameters.SlashParticleEffect;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.world.ClientWorld;

import java.util.List;

public class ClientNetworkHandler {
    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(NetworkHandler.SPAWN_ATTACK_PARTICLE_PACKET,
                (client, handler, buf, responseSender) -> {
            // Read data from packet
            String animationName = buf.readString();
            double x = buf.readDouble();
            double y = buf.readDouble();
            double z = buf.readDouble();
            double xStab = buf.readDouble();
            double yStab = buf.readDouble();
            double zStab = buf.readDouble();
            float pitch = buf.readFloat();
            float yaw = buf.readFloat();
            float weaponRange = buf.readFloat();
            boolean isOffhand = buf.readBoolean();
            boolean light = buf.readBoolean();
            String colorHex = buf.readString();

            // Execute on client thread
            client.execute(() -> {
                // Spawn particles for OTHER players' attacks
                List<ParticleSettings> settings = ParticleSettingsLoader.INSTANCE.getSettings(animationName);
                if (client.world != null && BetterCombatParticleMods.config.showOtherPlayerParticle) {
                    spawnParticleFromNetwork(client.world, x, y, z, xStab, yStab, zStab, pitch, yaw, weaponRange, isOffhand, settings, light, colorHex);
                }
            });
        });
    }

    private static void spawnParticleFromNetwork(ClientWorld world, double x, double y, double z, double xStab, double yStab, double zStab,
                                                 float pitch, float yaw, float weaponRange,
                                                 boolean isOffhand, List<ParticleSettings> settingsList, boolean light, String colorHex) {

        for (ParticleSettings settings : settingsList) {
            float offhandRoll = isOffhand ? 180 : 0;
            float offhandFlip = isOffhand ? -1 : 1;

            // Reuse your existing particle spawning logic
            switch (settings.particleType()) {
                case "stab":
                    world.addParticle(
                            new SlashParticleEffect(ModParticles.STAB ,weaponRange, pitch + settings.pitchAddition(), yaw,
                                    settings.localYaw()*offhandFlip, (settings.rollSet() - 45 + offhandRoll)*offhandFlip, light, colorHex),
                            xStab, yStab, zStab, 0, 0, 0
                    );
                    world.addParticle(
                            new SlashParticleEffect(ModParticles.STAB ,weaponRange, pitch + settings.pitchAddition(), yaw,
                                    settings.localYaw()*offhandFlip, (settings.rollSet() + 45 + offhandRoll)*offhandFlip, light, colorHex),
                            xStab, yStab, zStab, 0, 0, 0
                    );
                    break;
                case "slash45":
                    world.addParticle(
                            new SlashParticleEffect(ModParticles.SLASH45 ,weaponRange, pitch + settings.pitchAddition(), yaw,
                                    settings.localYaw()*offhandFlip, (settings.rollSet() + offhandRoll)*offhandFlip, light, colorHex),
                            x, y, z, 0, 0, 0
                    );
                    break;
                case "slash90":
                    world.addParticle(
                            new SlashParticleEffect(ModParticles.SLASH90 ,weaponRange, pitch + settings.pitchAddition(), yaw,
                                    settings.localYaw()*offhandFlip, (settings.rollSet() + offhandRoll)*offhandFlip, light, colorHex),
                            x, y, z,0, 0, 0
                    );
                    break;
                case "slash180":
                    world.addParticle(
                            new SlashParticleEffect(ModParticles.SLASH180 ,weaponRange, pitch + settings.pitchAddition(), yaw,
                                    settings.localYaw()*offhandFlip, (settings.rollSet() + offhandRoll)*offhandFlip, light, colorHex),
                            x, y, z, 0, 0, 0
                    );
                    break;
                case "slash270":
                    world.addParticle(
                            new SlashParticleEffect(ModParticles.SLASH270 ,weaponRange, pitch + settings.pitchAddition(), yaw,
                                    settings.localYaw()*offhandFlip, (settings.rollSet() + offhandRoll)*offhandFlip, light, colorHex),
                            x, y, z, 0, 0, 0
                    );
                    break;
                case "slash360":
                    world.addParticle(
                            new SlashParticleEffect(ModParticles.SLASH360 ,weaponRange, pitch + settings.pitchAddition(), yaw,
                                    settings.localYaw()*offhandFlip, (settings.rollSet() + offhandRoll)*offhandFlip, light, colorHex),
                            x, y, z, 0, 0, 0
                    );
                    break;
                default:
                    break;
            }
        }

    }
}
