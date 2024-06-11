package dev.JustRed23.paint;

import dev.JustRed23.paint.items.template.CreativeGetterItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Paint.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModTabs {

    private static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Paint.MOD_ID);

    static void register(IEventBus bus) {
        CREATIVE_TABS.register(bus);
    }

    public static RegistryObject<CreativeModeTab> MAIN = CREATIVE_TABS.register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("tabs.main.title"))
            .icon(ModItems.PAINTBRUSH.get()::getDefaultInstance)
            .build());

    @SubscribeEvent
    static void fillTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == MAIN.get()) {
            ModItems.getAll()
                    .stream()
                    .map(RegistryObject::get)
                    .filter(item -> item instanceof CreativeGetterItem)
                    .map(item -> (CreativeGetterItem) item)
                    .forEach(item -> event.accept(item.getCreativeInstance()));
        }
    }
}
