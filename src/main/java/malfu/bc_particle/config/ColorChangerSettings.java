package malfu.bc_particle.config;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.Map;

public class ColorChangerSettings {
    // Default colors
    public static final ColorLightSettings DEFAULT_ENCHANTED = new ColorLightSettings("D3C4FF", false);
    public static final ColorLightSettings DEFAULT_NORMAL = new ColorLightSettings("FFFFFF", false);

    public static ColorLightSettings resolveWeaponEffects(ItemStack itemStack) {
        // 1. Check for specific weapon settings (highest priority)
        Identifier itemId = Registries.ITEM.getId(itemStack.getItem());
        ColorLightSettings colorLightSettings = WeaponSettingsLoader.INSTANCE.getSettings(itemId.toString());

        if (colorLightSettings != null) {
            return colorLightSettings;
        }

        // 2. Check enchantments using EnchantmentHelper
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(itemStack);
        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            Identifier enchantmentId = Registries.ENCHANTMENT.getId(entry.getKey());
            if(enchantmentId != null) {
                ColorLightSettings enchantmentSettings = EnchantmentSettingsLoader.INSTANCE.getSettings(enchantmentId.toString());
                if (enchantmentSettings != null) {
                    return enchantmentSettings;
                }
            }
        }

        // 3. Default behavior: enchanted weapons get purple glow, others get white
        if (!enchantments.isEmpty()) {
            return DEFAULT_ENCHANTED;
        } else {
            return DEFAULT_NORMAL;
        }
    }
}