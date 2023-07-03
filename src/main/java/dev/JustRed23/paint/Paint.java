package dev.JustRed23.paint;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Paint.MOD_ID)
public class Paint {

    public static final String MOD_ID = "paint";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Paint() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlockEntities.register(bus);
        ModBlocks.register(bus);
        ModItems.register(bus);
        ModParticles.register(bus);
        ModRecipeSerializers.register(bus);
        ModSoundEvents.register(bus);

        bus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Loaded!");
    }
}
