package dev.JustRed23.paint;

import dev.JustRed23.paint.providers.PaintRecipesProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Paint.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PaintDataGatherer {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        event.getGenerator().addProvider(true, new PaintRecipesProvider(event.getGenerator().getPackOutput()));
    }
}
