package dev.JustRed23.paint;

import dev.JustRed23.paint.items.EyeDropperItem;
import dev.JustRed23.paint.items.PaintbrushItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

public final class ModItems {

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Paint.MOD_ID);
    private static final Map<String, RegistryObject<Item>> BLOCK_ITEMS = new HashMap<>();
    private static final List<RegistryObject<Item>> PAINTED_BLOCK_ITEMS = new ArrayList<>();

    static void register(IEventBus bus) {
        ITEMS.register(bus);
    }

    static <T extends Block> void registerBlockItem(RegistryObject<T> item, Function<T, BlockItem> blockItem, boolean painted) {
        final RegistryObject<Item> registryObject = ITEMS.register(item.getId().getPath(), () -> blockItem.apply(item.get()));
        BLOCK_ITEMS.put(item.getId().getPath(), registryObject);
        if (painted)
            PAINTED_BLOCK_ITEMS.add(registryObject);
    }

    static Map<String, RegistryObject<Item>> getBlockItems() {
        return BLOCK_ITEMS;
    }

    static List<RegistryObject<Item>> getPaintedBlockItems() {
        return PAINTED_BLOCK_ITEMS;
    }

    public static Collection<RegistryObject<Item>> getAll() {
        return ITEMS.getEntries();
    }

    public static @Nullable RegistryObject<Item> get(String name) {
        return ITEMS.getEntries().stream().filter(item -> item.getId().getPath().equals(name)).findFirst().orElse(null);
    }

    static @Nullable RegistryObject<Item> getBlockItem(String name) {
        return BLOCK_ITEMS.get(name);
    }

    //ITEMS
    public static final RegistryObject<Item> PAINTBRUSH = ITEMS.register("paintbrush", PaintbrushItem::new);
    public static final RegistryObject<Item> EYEDROPPER = ITEMS.register("eyedropper", EyeDropperItem::new);
}
