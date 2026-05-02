package com.lordraygon.immersive_apothic.mixin;

import dev.shadowsoffire.apothic_enchanting.table.ApothEnchantingTableBlock;
import me.alfie.immersiveenchanting.gui.EnchantingTableMenu;
import me.alfie.immersiveenchanting.util.BookshelfChecker;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ApothEnchantingTableBlock.class, remap = false)
public abstract class EnchantingTableOverrideMixin {

    @Inject(
            method = "getMenuProvider",
            at = @At("HEAD"),
            cancellable = true
    )
    // mixin doing mixin things
    private void useImmersiveMenuProvider(BlockState state, Level world, BlockPos pos,
                                          CallbackInfoReturnable<MenuProvider> cir) {
        BlockEntity be = world.getBlockEntity(pos);
        Component title = (be instanceof Nameable nameable)
                ? nameable.getDisplayName()
                : Component.translatable("container.enchant");

        cir.setReturnValue(new SimpleMenuProvider(
                (id, inventory, player) -> {
                    if (player instanceof ServerPlayer serverPlayer) {
                        BookshelfChecker.checkBookshelves(pos, world, serverPlayer);
                    }

                    return new EnchantingTableMenu(id, inventory, world, pos);
                },
                title
        ));
    }
}