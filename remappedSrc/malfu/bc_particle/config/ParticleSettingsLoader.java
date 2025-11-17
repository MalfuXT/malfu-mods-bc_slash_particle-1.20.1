package malfu.bc_particle.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class ParticleSettingsLoader implements SimpleResourceReloadListener<Map<String, List<ParticleSettings>>> {

    public static final ParticleSettingsLoader INSTANCE = new ParticleSettingsLoader();
    private Map<String, List<ParticleSettings>> settingsMap = new HashMap<>();

    @Override
    public CompletableFuture<Map<String, List<ParticleSettings>>> load(ResourceManager manager, Profiler profiler, Executor executor) {
        return CompletableFuture.supplyAsync(() -> this.prepare(manager, profiler), executor);
    }

    @Override
    public CompletableFuture<Void> apply(Map<String, List<ParticleSettings>> data, ResourceManager manager, Profiler profiler, Executor executor) {
        return CompletableFuture.runAsync(() -> this.applySettings(data), executor);
    }

    private Map<String, List<ParticleSettings>> prepare(ResourceManager resourceManager, Profiler profiler) {
        Map<String, List<ParticleSettings>> combinedMap = new HashMap<>();

        Map<Identifier, Resource> resourceMap = resourceManager.findResources("bc_particle", path -> path.getPath().endsWith(".json"));

        for (Identifier resourceId : resourceMap.keySet()) {
            String path = resourceId.getPath();
            if (!path.endsWith("particle_settings.json")) {
                continue;
            }

            try {
                Resource resource = resourceMap.get(resourceId);
                try (InputStream stream = resource.getInputStream();
                     Reader reader = new InputStreamReader(stream)) {

                    JsonObject rootObject = JsonHelper.deserialize(reader);
                    JsonObject valuesObject = JsonHelper.getObject(rootObject, "values");

                    for (String animationName : valuesObject.keySet()) {
                        JsonElement element = valuesObject.get(animationName);
                        List<ParticleSettings> settingsList = new ArrayList<>();

                        if (element.isJsonArray()) {
                            // Multiple particle definitions in array
                            JsonArray settingsArray = element.getAsJsonArray();
                            for (JsonElement settingElement : settingsArray) {
                                JsonObject settingsObject = settingElement.getAsJsonObject();
                                ParticleSettings settings = parseSettings(settingsObject);
                                settingsList.add(settings);
                            }
                        } else if (element.isJsonObject()) {
                            // Single particle definition (backwards compatibility)
                            JsonObject settingsObject = element.getAsJsonObject();
                            ParticleSettings settings = parseSettings(settingsObject);
                            settingsList.add(settings);
                        }

                        // Later resources override earlier ones for the same animation name
                        combinedMap.put(animationName, settingsList);
                    }
                } catch (Exception e) {
                    System.err.println("Failed to parse particle settings from: " + resourceId);
                    e.printStackTrace();
                }
            } catch (Exception e) {
                System.err.println("Failed to load particle settings from: " + resourceId);
                e.printStackTrace();
            }
        }
        return combinedMap;
    }

    private ParticleSettings parseSettings(JsonObject json) {
        return new ParticleSettings(
                JsonHelper.getString(json, "particle_type", "none"),
                JsonHelper.getFloat(json, "z_addition", 0.0F),
                JsonHelper.getFloat(json, "x_addition", 0.0F),
                JsonHelper.getFloat(json, "y_addition", 0.0F),
                JsonHelper.getFloat(json, "local_yaw", 0.0F),
                JsonHelper.getFloat(json, "pitch_addition", 0.0F),
                JsonHelper.getFloat(json, "roll_set", 0.0F)
        );
    }

    private void applySettings(Map<String, List<ParticleSettings>> newSettingsMap) {
        this.settingsMap = newSettingsMap;
        System.out.println("[BC Particles] Loaded settings for " + newSettingsMap.size() + " animations.");
    }

    @Override
    public Identifier getFabricId() {
        return new Identifier("bc_particle", "settings_loader");
    }

    public List<ParticleSettings> getSettings(String animationName) {
        // Return the list of settings for the animation, or a default list if not found
        return this.settingsMap.getOrDefault(animationName, List.of(ParticleSettings.DEFAULT));
    }
}
