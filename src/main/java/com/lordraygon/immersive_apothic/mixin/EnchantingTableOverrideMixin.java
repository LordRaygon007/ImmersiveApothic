package com.lordraygon.immersive_apothic.mixin;

import dev.shadowsoffire.apothic_enchanting.table.ApothEnchantingTableBlock;
import me.alfie.immersiveenchanting.gui.EnchantingTableMenu;
import me.alfie.immersiveenchanting.util.BookshelfChecker;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
// A mixin doing it's mixin things - essentially making sure IE's enchanting table ui is used over Apothic's
@Mixin(value = ApothEnchantingTableBlock.class, remap = false)
public abstract class EnchantingTableOverrideMixin {

    @Inject(
            method = "getMenuProvider",
            at = @At("HEAD"),
            cancellable = true
    )
    private void useImmersiveMenuProvider(BlockState state, Level world, BlockPos pos,
                                          CallbackInfoReturnable<MenuProvider> cir) {
        BlockEntity be = world.getBlockEntity(pos);
        Component title = (be instanceof Nameable nameable)
                ? nameable.getDisplayName()
                : Component.translatable("container.enchant");

        cir.setReturnValue(new SimpleMenuProvider(
                (id, inventory, player) -> new EnchantingTableMenu(id, inventory, world, pos),
                title
        ));
    }

    @Inject(
            method = "useWithoutItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;openMenu(Lnet/minecraft/world/MenuProvider;Lnet/minecraft/core/BlockPos;)Ljava/util/OptionalInt;",
                    shift = At.Shift.AFTER
            )
    )
    private void afterOpenMenu(BlockState state, Level level, BlockPos pos, Player player,
                               BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            BookshelfChecker.checkBookshelves(pos, level, serverPlayer);
        }
    }
}