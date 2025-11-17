package malfu.bc_particle.particle.custom;


import malfu.bc_particle.BetterCombatParticleMods;
import malfu.bc_particle.particle.parameters.SlashParticleEffect;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

@Environment(EnvType.CLIENT)
public class BotSlashParticle180 extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;
    private final String color; // Stored as 0xRRGGBB
    public final float modelOffset;
    private final float pitch; // Rotation around X axis
    private final float yaw;   // Rotation around Y axis
    private final float localYaw;   // Rotation around Y axis
    private final float roll;
    private final boolean light;

    BotSlashParticle180(ClientWorld world, double x, double y, double z,
                        float scale, float pitch, float yaw, float localYaw, float roll, boolean light, String colorHex,
                        SpriteProvider spriteProvider) {
        super(world, x, y, z, 0.0, 0.0, 0.0);
        this.spriteProvider = spriteProvider;
        this.color = colorHex;
        this.light = light;
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
        this.localYaw = localYaw;
        this.alpha = getAlpha();
        this.maxAge = 6;
        this.modelOffset = setModelOffset();
        this.scale = scale;
        this.setSpriteForAge(spriteProvider);
    }

    @Override
    public void tick() {
        setColor(getRed(), getGreen(), getBlue());
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            this.setSpriteForAge(this.spriteProvider);
        }
    }

    public float getAlpha(){
        if(light) {
           return this.alpha = BetterCombatParticleMods.config.light_particle_alpha;
        } else {
            return this.alpha = BetterCombatParticleMods.config.particle_alpha;
        }

    }

    // Convert hex to RGB floats
    public float getRed() {
        return parseHexColor(color, 16) / 255.0f;
    }

    public float getGreen() {
        return parseHexColor(color, 8) / 255.0f;
    }

    public float getBlue() {
        return parseHexColor(color, 0) / 255.0f;
    }

    private int parseHexColor(String hex, int shift) {
        String cleanHex = hex.startsWith("#") ? hex.substring(1) : hex;
        try {
            int color = Integer.parseInt(cleanHex, 16);
            return (color >> shift) & 0xFF;
        } catch (NumberFormatException e) {
            return 255; // Default to white if invalid
        }
    }

    @Override
    protected int getBrightness(float tint) {
        BlockPos blockPos = BlockPos.ofFloored(this.x, this.y, this.z);

        if(light){
            return 15728880;
        }

        return this.world.isChunkLoaded(blockPos) ? WorldRenderer.getLightmapCoordinates(this.world, blockPos) : 0;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public Particle scale(float scale) {
        this.scale = scale;
        return super.scale(scale);
    }

    public float setModelOffset(){
        return 0;
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        Vec3d cameraPos = camera.getPos();

        float x = (float)(this.prevPosX - cameraPos.getX());
        float y = (float)(this.prevPosY - cameraPos.getY());
        float z = (float)(this.prevPosZ - cameraPos.getZ());

        float size = this.getSize(tickDelta);
        float minU = this.getMinU();
        float maxU = this.getMaxU();
        float minV = this.getMinV();
        float maxV = this.getMaxV();

        Matrix4f rotationMatrix = new Matrix4f();
        rotationMatrix.identity();
        rotationMatrix.rotate((float)Math.toRadians(-this.yaw), new Vector3f(0, 1, 0));   // Global yaw first
        rotationMatrix.rotate((float)Math.toRadians(this.pitch), new Vector3f(1, 0, 0)); // Then global pitch
        rotationMatrix.rotate((float)Math.toRadians(this.roll), new Vector3f(0, 0, 1)); // Then global pitch
        rotationMatrix.rotate((float)Math.toRadians(-this.localYaw), new Vector3f(0, 1, 0));   // Global yaw first

        // Define the four corners of the particle quad (centered at origin)
        Vector4f[] corners = {
                new Vector4f(-size, this.modelOffset, -size, 1), // Bottom-left
                new Vector4f(-size, this.modelOffset,  size, 1), // Bottom-right
                new Vector4f( size, this.modelOffset,  size, 1), // Top-right
                new Vector4f( size, this.modelOffset, -size, 1)  // Top-left
        };

        // Apply rotation to each corner
        for (Vector4f corner : corners) {
            rotationMatrix.transform(corner);
            corner.add(x, y, z, 0); // Translate to world position
        }

        // Build the front face
        vertexConsumer.vertex(corners[0].x(), corners[0].y(), corners[0].z())
                .texture(maxU, maxV)
                .color(this.red, this.green, this.blue, this.alpha)
                .light(this.getBrightness(tickDelta))
                .next();
        vertexConsumer.vertex(corners[1].x(), corners[1].y(), corners[1].z())
                .texture(maxU, minV)
                .color(this.red, this.green, this.blue, this.alpha)
                .light(this.getBrightness(tickDelta))
                .next();
        vertexConsumer.vertex(corners[2].x(), corners[2].y(), corners[2].z())
                .texture(minU, minV)
                .color(this.red, this.green, this.blue, this.alpha)
                .light(this.getBrightness(tickDelta))
                .next();
        vertexConsumer.vertex(corners[3].x(), corners[3].y(), corners[3].z())
                .texture(minU, maxV)
                .color(this.red, this.green, this.blue, this.alpha)
                .light(this.getBrightness(tickDelta))
                .next();

        // Build the back face (reverse winding)
        vertexConsumer.vertex(corners[3].x(), corners[3].y(), corners[3].z())
                .texture(minU, maxV)
                .color(this.red, this.green, this.blue, this.alpha)
                .light(this.getBrightness(tickDelta))
                .next();
        vertexConsumer.vertex(corners[2].x(), corners[2].y(), corners[2].z())
                .texture(minU, minV)
                .color(this.red, this.green, this.blue, this.alpha)
                .light(this.getBrightness(tickDelta))
                .next();
        vertexConsumer.vertex(corners[1].x(), corners[1].y(), corners[1].z())
                .texture(maxU, minV)
                .color(this.red, this.green, this.blue, this.alpha)
                .light(this.getBrightness(tickDelta))
                .next();
        vertexConsumer.vertex(corners[0].x(), corners[0].y(), corners[0].z())
                .texture(maxU, maxV)
                .color(this.red, this.green, this.blue, this.alpha)
                .light(this.getBrightness(tickDelta))
                .next();
    }

    @Environment(EnvType.CLIENT)
    public static class Provider implements ParticleFactory<SlashParticleEffect> {
        private final SpriteProvider spriteProvider;

        public Provider(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SlashParticleEffect settings, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new BotSlashParticle180(clientWorld, d, e, f,
                    settings.getScale(), settings.getPitch(), settings.getYaw(), settings.getLocalYaw() , settings.getRoll(),
                    settings.getLight(), settings.getColorHex(),
                    this.spriteProvider);
        }
    }
}
