package com.lordraygon.immersive_apothic.config;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

// THE CONFIG
// This is internal stuff controlled by me
public class Config {
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.ConfigValue<List<? extends String>> DISABLED_ENCHANTMENTS;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> MOD_GATE;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("enchantments");

        DISABLED_ENCHANTMENTS = builder
                .comment(
                        "Enchantments that are always disabled, regardless of installed mods.",
                        "Accepts a specific enchantment id (\"apothic_enchanting:berserkers_fury\")",
                        "or a whole namespace with a wildcard (\"apothic_enchanting:*\") etc etc etc..."
                )
                .defineListAllowEmpty("disabledEnchantments", List.of(), () -> "modid:enchantment_id", Config::isEnchantmentEntry);

        // gamer gate???
        MOD_GATE = builder
                .comment(
                        "Enchantments that only make sense with another mod installed.",
                        "Format: \"required_mod_id=enchantment_1,enchantment_2,...\"",
                        "If required_mod_id isn't loaded, every listed enchantment is disabled automatically.",
                        "Example: \"spartan_weaponry=apothic_enchanting:polearm_mastery,apothic_enchanting:reach blah blah blah...\""
                )
                .defineListAllowEmpty("modGatedEnchantments", List.of(), () -> "modid=modid:enchantment_id", Config::isGatedEntry);

        builder.pop();
        SPEC = builder.build();
    }

    private static boolean isEnchantmentEntry(Object obj) {
        return obj instanceof String s && s.contains(":");
    }

    private static boolean isGatedEntry(Object obj) {
        if (!(obj instanceof String s) || !s.contains("=")) return false;
        String[] parts = s.split("=", 2);
        return parts.length == 2 && !parts[0].isBlank() && parts[1].contains(":");
    }
}
