package com.lordraygon.immersive_apothic;

import com.lordraygon.immersive_apothic.config.Config;
import com.lordraygon.immersive_apothic.config.EnchantmentFilter;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;

@Mod(ImmersiveApothic.MOD_ID)
public class ImmersiveApothic {
    public static final String MOD_ID = "immersive_apothic";

    public ImmersiveApothic(IEventBus modEventBus, ModContainer container) {
        container.registerConfig(ModConfig.Type.SERVER, Config.SPEC);
        modEventBus.addListener(ModConfigEvent.Loading.class, EnchantmentFilter::onConfigChanged);
        modEventBus.addListener(ModConfigEvent.Reloading.class, EnchantmentFilter::onConfigChanged);
    }
}