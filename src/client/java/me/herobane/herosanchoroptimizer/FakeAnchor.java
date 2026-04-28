package me.herobane.herosanchoroptimizer;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class FakeAnchor {

  public static ResourceKey<Block> RESOURCE_KEY = ResourceKey.create(
      Registries.BLOCK,
      Identifier.fromNamespaceAndPath("herosanchoroptimizer", "fake_anchor")
  );
  public static final Block BLOCK = register(
      new Block(
          BlockBehaviour.Properties.of()
              .noOcclusion() // block is translucent
              .strength(-1.0f) // can't Break
              .noLootTable() // Does not drop items when broken
              .replaceable() // Replaceable like a fern
              .sound(SoundType.POWDER_SNOW) //cuz uh IDK, doesn't really need a sound
              .setId(RESOURCE_KEY)
      )
  );

  // Helper methods for adding blocks
  private static Block register(Block block) {
    return Registry.register(BuiltInRegistries.BLOCK, RESOURCE_KEY, block);
  }

  public static void init() {
  }
}
