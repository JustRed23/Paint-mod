package dev.JustRed23.idk;

import dev.JustRed23.idk.blocks.PaintBucketBlock;
import dev.JustRed23.idk.blocks.PaintedBlock;
import dev.JustRed23.idk.items.PaintBucketItem;
import dev.JustRed23.idk.items.PaintedBlockItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.Supplier;

public final class ModBlocks {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, IDK.MOD_ID);

    static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }

    private static RegistryObject<Block> registerBlock(String name, Supplier<Block> block) {
        RegistryObject<Block> registryObject = BLOCKS.register(name, block);
        ModItems.registerBlockItem(registryObject, item -> new BlockItem(item, new Item.Properties()));
        return registryObject;
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, Function<T, BlockItem> blockItem) {
        RegistryObject<T> registryObject = BLOCKS.register(name, block);
        ModItems.registerBlockItem(registryObject, blockItem);
        return registryObject;
    }

    //BLOCKS
    public static final RegistryObject<Block> PAINTED_BLOCK = registerBlock("painted_block", PaintedBlock::new, block -> new PaintedBlockItem((PaintedBlock) block));
    public static final RegistryObject<Block> PAINT_BUCKET = registerBlock("paint_bucket", PaintBucketBlock::new, block -> new PaintBucketItem((PaintBucketBlock) block));
}
