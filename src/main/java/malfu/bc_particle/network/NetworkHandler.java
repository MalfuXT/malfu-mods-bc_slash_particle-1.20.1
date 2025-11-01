package malfu.bc_particle.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class NetworkHandler {
    public static final Identifier SPAWN_ATTACK_PARTICLE_PACKET = new Identifier("bc_particle", "spawn_attack_particle");

    public static void register() {
        // Server-side packet receiver
        ServerPlayNetworking.registerGlobalReceiver(SPAWN_ATTACK_PARTICLE_PACKET, (server, player, handler, buf, responseSender) -> {
            // Read all the data needed to recreate the particle
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
            String colorHexSec = buf.readString();

            // Execute on server thread
            server.execute(() -> {
                // Broadcast to all players in the same world (except the original player)
                for (ServerPlayerEntity otherPlayer : player.getServerWorld().getPlayers()) {
                    if (otherPlayer != player) {
                        // Create a new buffer for each player
                        PacketByteBuf newBuf = PacketByteBufs.create();
                        newBuf.writeString(animationName);
                        newBuf.writeDouble(x);
                        newBuf.writeDouble(y);
                        newBuf.writeDouble(z);
                        newBuf.writeDouble(xStab);
                        newBuf.writeDouble(yStab);
                        newBuf.writeDouble(zStab);
                        newBuf.writeFloat(pitch);
                        newBuf.writeFloat(yaw);
                        newBuf.writeFloat(weaponRange);
                        newBuf.writeBoolean(isOffhand);
                        newBuf.writeBoolean(light);
                        newBuf.writeString(colorHex);
                        newBuf.writeString(colorHexSec);

                        ServerPlayNetworking.send(otherPlayer, SPAWN_ATTACK_PARTICLE_PACKET, newBuf);
                    }
                }
            });
        });
    }

    // Client-side method to send the packet
    public static void sendParticlePacket(String animationName, double x, double y, double z, double xStab, double yStab, double zStab,
                                          float pitch, float yaw, float weaponRange, boolean isOffhand, boolean light, String colorHex, String colorHexSec) {
        if (ClientPlayNetworking.canSend(SPAWN_ATTACK_PARTICLE_PACKET)) {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeString(animationName);
            buf.writeDouble(x);
            buf.writeDouble(y);
            buf.writeDouble(z);
            buf.writeDouble(xStab);
            buf.writeDouble(yStab);
            buf.writeDouble(zStab);
            buf.writeFloat(pitch);
            buf.writeFloat(yaw);
            buf.writeFloat(weaponRange);
            buf.writeBoolean(isOffhand);
            buf.writeBoolean(light);
            buf.writeString(colorHex);
            buf.writeString(colorHexSec);

            ClientPlayNetworking.send(SPAWN_ATTACK_PARTICLE_PACKET, buf);
        }
    }
}
