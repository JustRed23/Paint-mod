package dev.JustRed23.idk;

import dev.JustRed23.idk.providers.IDKRecipesProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IDK.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class IDKDataGatherer {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        event.getGenerator().addProvider(true, new IDKRecipesProvider(event.getGenerator().getPackOutput()));
    }
}
