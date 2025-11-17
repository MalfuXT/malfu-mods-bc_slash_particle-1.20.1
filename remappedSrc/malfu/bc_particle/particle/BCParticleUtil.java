package malfu.bc_particle.particle;

import malfu.bc_particle.BetterCombatParticleMods;
import malfu.bc_particle.config.ParticleSettings;
import malfu.bc_particle.network.NetworkHandler;
import malfu.bc_particle.particle.parameters.SlashParticleEffect;
import net.bettercombat.api.AttackHand;
import net.bettercombat.api.WeaponAttributes;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class BCParticleUtil {
    public static void spawnParticleForSettings(ClientPlayerEntity player, AttackHand hand, List<ParticleSettings> settingsList, float weaponRange,
                                                boolean light, String colorHex, String colorHexSec) {
        for (ParticleSettings settings : settingsList) {
            boolean isOffhand = hand.isOffHand();
            float offhandRoll = isOffhand ? 180 : 0;
            float offhandFlip = isOffhand ? -1 : 1;

            // Create local coordinate system for left/right positioning
            float yaw = player.getYaw();
            float pitch = player.getPitch();
            Vec3d right = Vec3d.fromPolar(0, yaw + 90).normalize(); // Right vector (always horizontal)
            Vec3d forward = Vec3d.fromPolar(pitch, yaw).normalize();

            // Start from the base position (similar to your second approach)
            double baseX = player.getX();
            double baseY = player.getEyeY() - 0.25f + settings.yAddition();
            double baseZ = player.getZ();

            // Apply left/right offset using the right vector
            Vec3d finalPosition = new Vec3d(baseX, baseY, baseZ)
                    .add(forward.multiply(settings.zAddition()))
                    .add(right.multiply(settings.xAddition()*offhandFlip)); // Add left/right offset

            Vec3d stabFinalPosition = new Vec3d(finalPosition.getX(), finalPosition.getY(), finalPosition.getZ())
                    .add(forward.multiply(weaponRange-1.5));

            double x = finalPosition.getX();
            double y = finalPosition.getY();
            double z = finalPosition.getZ();

            double xStab = stabFinalPosition.getX();
            double yStab = stabFinalPosition.getY();
            double zStab = stabFinalPosition.getZ();

            WeaponAttributes.Attack attack = hand.attack();
            String animationName = attack.animation();

            if(BetterCombatParticleMods.config.showOtherPlayerParticle) {
                NetworkHandler.sendParticlePacket(
                        animationName, // Use particleType as identifier, or you might want to use animationName
                        x, y, z,
                        xStab, yStab, zStab,
                        player.getPitch(),
                        player.getYaw(),
                        weaponRange,
                        isOffhand,
                        light,
                        colorHex,
                        colorHexSec
                );
            }


            // Choose which particle to spawn based on the string
            switch (settings.particleType()) {
                case "stab":
                    player.getWorld().addParticle(
                            new SlashParticleEffect(ModParticles.BOTSTAB,weaponRange, player.getPitch()+settings.pitchAddition(), player.getYaw(),
                                    settings.localYaw()*offhandFlip ,(settings.rollSet() -45 + offhandRoll)*offhandFlip , light, colorHex),
                            xStab, yStab, zStab, 0, 0, 0
                    );
                    player.getWorld().addParticle(
                            new SlashParticleEffect(ModParticles.BOTSTAB,weaponRange, player.getPitch()+settings.pitchAddition(), player.getYaw(),
                                    settings.localYaw()*offhandFlip ,(settings.rollSet() +45 + offhandRoll)*offhandFlip , light, colorHex),
                            xStab, yStab, zStab, 0, 0, 0
                    );

                    player.getWorld().addParticle(
                            new SlashParticleEffect(ModParticles.TOPSTAB,weaponRange, player.getPitch()+settings.pitchAddition(), player.getYaw(),
                                    settings.localYaw()*offhandFlip ,(settings.rollSet() -45 + offhandRoll)*offhandFlip , light, colorHexSec),
                            xStab, yStab, zStab, 0, 0, 0
                    );
                    player.getWorld().addParticle(
                            new SlashParticleEffect(ModParticles.TOPSTAB,weaponRange, player.getPitch()+settings.pitchAddition(), player.getYaw(),
                                    settings.localYaw()*offhandFlip ,(settings.rollSet() +45 + offhandRoll)*offhandFlip , light, colorHexSec),
                            xStab, yStab, zStab, 0, 0, 0
                    );
                    break;
                case "slash45":
                    player.getWorld().addParticle(
                            new SlashParticleEffect(ModParticles.BOTSLASH45,weaponRange, player.getPitch()+settings.pitchAddition(), player.getYaw(),
                                    settings.localYaw()*offhandFlip ,(settings.rollSet() + offhandRoll)*offhandFlip , light, colorHex),
                            x, y, z, 0, 0, 0
                    );

                    player.getWorld().addParticle(
                            new SlashParticleEffect(ModParticles.TOPSLASH45,weaponRange, player.getPitch()+settings.pitchAddition(), player.getYaw(),
                                    settings.localYaw()*offhandFlip ,(settings.rollSet() + offhandRoll)*offhandFlip , light, colorHexSec),
                            x, y, z, 0, 0, 0
                    );
                    break;
                case "slash90":
                    player.getWorld().addParticle(
                            new SlashParticleEffect(ModParticles.BOTSLASH90,weaponRange, player.getPitch()+settings.pitchAddition(), player.getYaw(),
                                    settings.localYaw()*offhandFlip ,(settings.rollSet() + offhandRoll)*offhandFlip , light, colorHex),
                            x, y, z, 0, 0, 0
                    );
                    player.getWorld().addParticle(
                            new SlashParticleEffect(ModParticles.TOPSLASH90,weaponRange, player.getPitch()+settings.pitchAddition(), player.getYaw(),
                                    settings.localYaw()*offhandFlip ,(settings.rollSet() + offhandRoll)*offhandFlip , light, colorHexSec),
                            x, y, z, 0, 0, 0
                    );
                    break;
                case "slash180":
                    player.getWorld().addParticle(
                            new SlashParticleEffect(ModParticles.BOTSLASH180,weaponRange, player.getPitch()+settings.pitchAddition(), player.getYaw(),
                                    settings.localYaw()*offhandFlip ,(settings.rollSet() + offhandRoll)*offhandFlip , light, colorHex),
                            x, y, z, 0, 0, 0
                    );

                    player.getWorld().addParticle(
                            new SlashParticleEffect(ModParticles.TOPSLASH180,weaponRange, player.getPitch()+settings.pitchAddition(), player.getYaw(),
                                    settings.localYaw()*offhandFlip ,(settings.rollSet() + offhandRoll)*offhandFlip , light, colorHexSec),
                            x, y, z, 0, 0, 0
                    );
                    break;
                case "slash270":
                    player.getWorld().addParticle(
                            new SlashParticleEffect(ModParticles.BOTSLASH270,weaponRange, player.getPitch()+settings.pitchAddition(), player.getYaw(),
                                    settings.localYaw()*offhandFlip ,(settings.rollSet() + offhandRoll)*offhandFlip , light, colorHex),
                            x, y, z, 0, 0, 0
                    );

                    player.getWorld().addParticle(
                            new SlashParticleEffect(ModParticles.TOPSLASH270,weaponRange, player.getPitch()+settings.pitchAddition(), player.getYaw(),
                                    settings.localYaw()*offhandFlip ,(settings.rollSet() + offhandRoll)*offhandFlip , light, colorHexSec),
                            x, y, z, 0, 0, 0
                    );
                    break;
                case "slash360":
                    player.getWorld().addParticle(
                            new SlashParticleEffect(ModParticles.BOTSLASH360,weaponRange, player.getPitch()+settings.pitchAddition(), player.getYaw(),
                                    settings.localYaw()*offhandFlip ,(settings.rollSet() + offhandRoll)*offhandFlip , light, colorHex),
                            x, y, z, 0, 0, 0
                    );

                    player.getWorld().addParticle(
                            new SlashParticleEffect(ModParticles.TOPSLASH360,weaponRange, player.getPitch()+settings.pitchAddition(), player.getYaw(),
                                    settings.localYaw()*offhandFlip ,(settings.rollSet() + offhandRoll)*offhandFlip , light, colorHexSec),
                            x, y, z, 0, 0, 0
                    );
                    break;
                default:
                    break;
            }
        }
    }
}
