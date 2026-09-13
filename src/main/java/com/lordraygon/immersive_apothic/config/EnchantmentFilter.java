package com.lordraygon.immersive_apothic.config;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.fml.ModList;
import net.neoforged.fml.event.config.ModConfigEvent;

import java.util.HashSet;
import java.util.Set;

public class EnchantmentFilter {
    private static Set<ResourceLocation> disabledIds;
    private static Set<String> disabledNamespaces;

    public static boolean isDisabled(Holder<Enchantment> holder) {
        return holder.unwrapKey().map(key -> isDisabled(key.location())).orElse(false);
    }

    public static boolean isDisabled(ResourceLocation id) {
        resolveIfNeeded();
        return disabledNamespaces.contains(id.getNamespace()) || disabledIds.contains(id);
    }

    public static void onConfigChanged(ModConfigEvent event) {
        if (event.getConfig().getSpec() == Config.SPEC) {
            disabledIds = null;
            disabledNamespaces = null;
        }
    }

    private static void resolveIfNeeded() {
        if (disabledIds != null) return;

        Set<ResourceLocation> ids = new HashSet<>();
        Set<String> namespaces = new HashSet<>();

        for (String entry : Config.DISABLED_ENCHANTMENTS.get()) {
            addEntry(entry, ids, namespaces);
        }

        for (String entry : Config.MOD_GATE.get()) {
            String[] parts = entry.split("=", 2);
            if (ModList.get().isLoaded(parts[0].trim())) continue;
            for (String ench : parts[1].split(",")) {
                addEntry(ench.trim(), ids, namespaces);
            }
        }

        disabledIds = ids;
        disabledNamespaces = namespaces;
    }

    private static void addEntry(String entry, Set<ResourceLocation> ids, Set<String> namespaces) {
        if (entry.endsWith(":*")) {
            namespaces.add(entry.substring(0, entry.length() - 2));
        } else {
            ids.add(ResourceLocation.parse(entry));
        }
    }
}
