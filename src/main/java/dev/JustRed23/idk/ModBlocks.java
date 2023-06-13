package dev.JustRed23.idk;

import dev.JustRed23.idk.blocks.PaintBucketBlock;
import dev.JustRed23.idk.blocks.PaintedBlock;
import dev.JustRed23.idk.blocks.paintedblockvariants.PaintedGlass;
import dev.JustRed23.idk.blocks.paintedblockvariants.PaintedSlab;
import dev.JustRed23.idk.blocks.paintedblockvariants.PaintedStair;
import dev.JustRed23.idk.items.PaintBucketItem;
import dev.JustRed23.idk.items.PaintedBlockItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public final class ModBlocks {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, IDK.MOD_ID);
    private static final List<RegistryObject<Block>> PAINTED_BLOCKS = new ArrayList<>();

    static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }

    private static RegistryObject<Block> registerBlock(String name, Supplier<Block> block) {
        return registerBlock(name, block, item -> new BlockItem(item, new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, Function<T, BlockItem> blockItem) {
        return registerBlock(name, block, blockItem, false);
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, Function<T, BlockItem> blockItem, boolean painted) {
        RegistryObject<T> registryObject = BLOCKS.register(name, block);
        ModItems.registerBlockItem(registryObject, blockItem, painted);
        if (painted)
            PAINTED_BLOCKS.add((RegistryObject<Block>) registryObject);
        return registryObject;
    }

    public static List<RegistryObject<Block>> getPaintedBlocks() {
        return PAINTED_BLOCKS;
    }

    //BLOCKS
    public static final RegistryObject<Block> PAINTED_BLOCK = registerBlock("painted_block", PaintedBlock::new, PaintedBlockItem::new, true);
    public static final RegistryObject<Block> PAINTED_STAIRS = registerBlock("painted_stairs", PaintedStair::new, PaintedBlockItem::new, true);
    public static final RegistryObject<Block> PAINTED_SLAB = registerBlock("painted_slab", PaintedSlab::new, PaintedBlockItem::new, true);
    public static final RegistryObject<Block> PAINTED_GLASS = registerBlock("painted_glass", PaintedGlass::new, PaintedBlockItem::new, true);

    public static final RegistryObject<Block> PAINT_BUCKET = registerBlock("paint_bucket", PaintBucketBlock::new, block -> new PaintBucketItem((PaintBucketBlock) block));
}
