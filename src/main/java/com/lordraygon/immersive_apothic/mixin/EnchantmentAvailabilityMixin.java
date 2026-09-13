package com.lordraygon.immersive_apothic.mixin;

import com.lordraygon.immersive_apothic.config.EnchantmentFilter;
import dev.shadowsoffire.apothic_enchanting.table.ApothEnchantmentHelper;
import dev.shadowsoffire.apothic_enchanting.table.EnchantmentTableStats;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.stream.Stream;

/*
Filters the enchanting table's possible enchantments against config,
covers both the manual disabled list and THE LIST.
*/
@Mixin(value = ApothEnchantmentHelper.class, remap = false)
public abstract class EnchantmentAvailabilityMixin {

    @Inject(method = "getPossibleEnchantments", at = @At("RETURN"), cancellable = true)
    private static void filterDisabledEnchantments(HolderLookup.RegistryLookup<Enchantment> reg, ItemStack stack,
                                                     EnchantmentTableStats stats,
                                                     CallbackInfoReturnable<Stream<Holder<Enchantment>>> cir) {
        cir.setReturnValue(cir.getReturnValue().filter(h -> !EnchantmentFilter.isDisabled(h)));
    }
}
