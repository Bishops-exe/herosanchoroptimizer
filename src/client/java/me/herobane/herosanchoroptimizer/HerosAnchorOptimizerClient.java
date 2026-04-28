package me.herobane.herosanchoroptimizer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.state.BlockState;

public class HerosAnchorOptimizerClient implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    FakeAnchor.init();
    // Register an event for right-clicking a respawn anchor
    UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
      BlockPos pos = hitResult.getBlockPos();
      BlockState state = level.getBlockState(pos);

      if (!level.isClientSide() || !state.is(Blocks.RESPAWN_ANCHOR)) {
        return InteractionResult.PASS;
      }

      boolean isSingleplayer = Minecraft.getInstance().isLocalServer();
      boolean isNotEmpty = state.getValue(RespawnAnchorBlock.CHARGE) <= 0;
      boolean holdingGlowstoneMainHand = player.getItemBySlot(hand.asEquipmentSlot())
          .is(Items.GLOWSTONE);
      boolean holdingGlowstoneOffHand = player.getOffhandItem().is(Items.GLOWSTONE);
      boolean wouldExplode = !level.environmentAttributes()
          .getValue(EnvironmentAttributes.RESPAWN_ANCHOR_WORKS, pos);

      if (player.isShiftKeyDown() || holdingGlowstoneMainHand || holdingGlowstoneOffHand
          || isSingleplayer || isNotEmpty || !wouldExplode) {
        return InteractionResult.PASS;
      }

      level.setBlockAndUpdate(pos, FakeAnchor.BLOCK.defaultBlockState());
      return InteractionResult.SUCCESS;
    });
  }

}
