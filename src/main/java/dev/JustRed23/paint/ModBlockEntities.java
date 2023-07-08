package dev.JustRed23.paint;

import dev.JustRed23.paint.blocks.blockentities.PaintBucketBlockEntity;
import dev.JustRed23.paint.blocks.blockentities.PaintedBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class ModBlockEntities {

    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Paint.MOD_ID);

    static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }

    public static void sync(BlockEntity be) {
        final Level level = be.getLevel();
        if (level instanceof ServerLevel serverLevel)
            serverLevel.getChunkSource().blockChanged(be.getBlockPos());
        else if (level != null) Paint.LOGGER.warn("Attempted to sync BlockEntity on invalid level: " + level.getClass().getName());
    }

    //BLOCK ENTITIES
    public static final RegistryObject<BlockEntityType<PaintedBlockEntity>> PAINTED_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("painted_block", () -> BlockEntityType.Builder.of((pos, state) -> new PaintedBlockEntity(ModBlockEntities.PAINTED_BLOCK_ENTITY.get(), pos, state), ModBlocks.PAINTED_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<PaintedBlockEntity>> PAINTED_STAIR_ENTITY =
            BLOCK_ENTITIES.register("painted_stairs", () -> BlockEntityType.Builder.of((pos, state) -> new PaintedBlockEntity(ModBlockEntities.PAINTED_STAIR_ENTITY.get(), pos, state), ModBlocks.PAINTED_STAIRS.get()).build(null));
    public static final RegistryObject<BlockEntityType<PaintedBlockEntity>> PAINTED_SLAB_ENTITY =
            BLOCK_ENTITIES.register("painted_slab", () -> BlockEntityType.Builder.of((pos, state) -> new PaintedBlockEntity(ModBlockEntities.PAINTED_SLAB_ENTITY.get(), pos, state), ModBlocks.PAINTED_SLAB.get()).build(null));
    public static final RegistryObject<BlockEntityType<PaintedBlockEntity>> PAINTED_GLASS_ENTITY =
            BLOCK_ENTITIES.register("painted_glass", () -> BlockEntityType.Builder.of((pos, state) -> new PaintedBlockEntity(ModBlockEntities.PAINTED_GLASS_ENTITY.get(), pos, state), ModBlocks.PAINTED_GLASS.get()).build(null));
    public static final RegistryObject<BlockEntityType<PaintedBlockEntity>> PAINTED_GLASS_PANE_ENTITY =
            BLOCK_ENTITIES.register("painted_glass_pane", () -> BlockEntityType.Builder.of((pos, state) -> new PaintedBlockEntity(ModBlockEntities.PAINTED_GLASS_PANE_ENTITY.get(), pos, state), ModBlocks.PAINTED_GLASS_PANE.get()).build(null));
    public static final RegistryObject<BlockEntityType<PaintedBlockEntity>> PAINTED_BED_ENTITY =
            BLOCK_ENTITIES.register("painted_bed", () -> BlockEntityType.Builder.of((pos, state) -> new PaintedBlockEntity(ModBlockEntities.PAINTED_BED_ENTITY.get(), pos, state), ModBlocks.PAINTED_BED.get()).build(null));

    public static final RegistryObject<BlockEntityType<PaintBucketBlockEntity>> PAINT_BUCKET_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("paint_bucket", () -> BlockEntityType.Builder.of(PaintBucketBlockEntity::new, ModBlocks.PAINT_BUCKET.get()).build(null));
}
