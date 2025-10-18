package malfu.bc_particle.config;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.profiler.Profiler;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class EnchantmentSettingsLoader implements SimpleResourceReloadListener<Map<String, ColorLightSettings>> {

    public static final EnchantmentSettingsLoader INSTANCE = new EnchantmentSettingsLoader();
    private Map<String, ColorLightSettings> settingsMap = new HashMap<>();

    @Override
    public CompletableFuture<Map<String, ColorLightSettings>> load(ResourceManager manager, Profiler profiler, Executor executor) {
        return CompletableFuture.supplyAsync(() -> this.prepare(manager, profiler), executor);
    }

    @Override
    public CompletableFuture<Void> apply(Map<String, ColorLightSettings> data, ResourceManager manager, Profiler profiler, Executor executor) {
        return CompletableFuture.runAsync(() -> this.applySettings(data), executor);
    }

    private Map<String, ColorLightSettings> prepare(ResourceManager resourceManager, Profiler profiler) {
        Map<String, ColorLightSettings> combinedMap = new HashMap<>();

        // FIXED: findResources returns Map<Identifier, Resource>, not Collection<Identifier>
        Map<Identifier, Resource> resourceMap = resourceManager.findResources("bc_particle", path -> path.getPath().endsWith(".json"));

        for (Identifier resourceId : resourceMap.keySet()) {
            // Check if the file is named 'particle_settings.json'
            String path = resourceId.getPath();
            if (!path.endsWith("enchantment_settings.json")) {
                continue; // Skip files that aren't the settings file
            }

            try {
                // Get the resource for this specific path
                Resource resource = resourceMap.get(resourceId);
                try (InputStream stream = resource.getInputStream();
                     Reader reader = new InputStreamReader(stream)) {

                    JsonObject rootObject = JsonHelper.deserialize(reader);
                    JsonObject valuesObject = JsonHelper.getObject(rootObject, "values");

                    for (String enchantmentId : valuesObject.keySet()) {
                        JsonObject settingsObject = JsonHelper.getObject(valuesObject, enchantmentId);
                        ColorLightSettings settings = parseSettings(settingsObject);
                        // Later resources (higher priority datapacks) will override earlier ones
                        combinedMap.put(enchantmentId, settings);
                    }
                } catch (Exception e) {
                    System.err.println("Failed to parse enchantment settings from: " + resourceId);
                    e.printStackTrace();
                }
            } catch (Exception e) {
                System.err.println("Failed to load enchantment settings from: " + resourceId);
                e.printStackTrace();
            }
        }
        return combinedMap;
    }

    private ColorLightSettings parseSettings(JsonObject json) {
        return new ColorLightSettings(
                JsonHelper.getString(json, "color_hex", "FFFFFF"),
                JsonHelper.getBoolean(json, "light", true)
        );
    }

    private void applySettings(Map<String, ColorLightSettings> newSettingsMap) {
        this.settingsMap = newSettingsMap;
        System.out.println("[BC Particles] Loaded enchantment settings for " + newSettingsMap.size() + " enchantment.");
    }

    @Override
    public Identifier getFabricId() {
        return new Identifier("bc_particle", "enchantment_settings_loader");
    }

    public ColorLightSettings getSettings(String enchantmentId) {
        // Return the list of settings for the animation, or a default list if not found
        return this.settingsMap.getOrDefault(enchantmentId, null);
    }
}
