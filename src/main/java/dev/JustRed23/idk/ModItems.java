package dev.JustRed23.idk;

import dev.JustRed23.idk.items.PaintbrushItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class ModItems {

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, IDK.MOD_ID);
    private static final Map<String, RegistryObject<Item>> BLOCK_ITEMS = new HashMap<>();

    static void register(IEventBus bus) {
        ITEMS.register(bus);
    }

    static <T extends Block> void registerBlockItem(RegistryObject<T> item, Function<T, BlockItem> blockItem) {
        BLOCK_ITEMS.put(item.getId().getPath(), ITEMS.register(item.getId().getPath(), () -> blockItem.apply(item.get())));
    }

    static Map<String, RegistryObject<Item>> getBlockItems() {
        return BLOCK_ITEMS;
    }

    public static Collection<RegistryObject<Item>> getAll() {
        return ITEMS.getEntries();
    }

    static @Nullable RegistryObject<Item> getBlockItem(String name) {
        return BLOCK_ITEMS.get(name);
    }

    //ITEMS
    public static final RegistryObject<Item> PAINTBRUSH = ITEMS.register("paintbrush", PaintbrushItem::new);
}
