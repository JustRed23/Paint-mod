package dev.JustRed23.idk;

import dev.JustRed23.idk.blocks.blockentities.template.ColoredBlockEntity;
import dev.JustRed23.idk.items.PaintBucketItem;
import dev.JustRed23.idk.items.PaintbrushItem;
import dev.JustRed23.idk.particle.PaintParticle;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = IDK.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class IDKClient {

    private static final ResourceLocation EMPTY = new ResourceLocation(IDK.MOD_ID, "empty");

    @SubscribeEvent
    public static void onSetup(FMLClientSetupEvent event) {
        final PaintbrushItem paintbrush = (PaintbrushItem) ModItems.PAINTBRUSH.get();
        ItemProperties.register(paintbrush, EMPTY, (stack, world, entity, seed) -> paintbrush.canUse(stack) ? 0.0F : 1.0F);

        final PaintBucketItem paintBucket = (PaintBucketItem) ModItems.getBlockItem("paint_bucket").get();
        ItemProperties.register(paintBucket, EMPTY, (stack, world, entity, seed) -> paintBucket.canUse(stack) ? 0.0F : 1.0F);
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> {
            if (tintIndex == 1 && stack.hasTag() && stack.getTag().contains("paintColor"))
                return stack.getTag().getInt("paintColor");
            return -1;
        }, ModItems.PAINTBRUSH.get(), ModItems.getBlockItem("paint_bucket").get());

        event.register((stack, tintIndex) -> {
            if (tintIndex == 0 && stack.hasTag() && stack.getTag().contains("paintColor"))
                return stack.getTag().getInt("paintColor");
            return -1;
        }, ModItems.getBlockItem("painted_block").get());
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((state, world, pos, tintIndex) -> {
            if (world != null && pos != null && tintIndex == 0) {
                ColoredBlockEntity blockEntity = (ColoredBlockEntity) world.getBlockEntity(pos);
                if (blockEntity != null)
                    return blockEntity.getColor();
            }
            return -1;
        }, ModBlocks.PAINTED_BLOCK.get(), ModBlocks.PAINT_BUCKET.get());
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpecial(ModParticles.PAINT_PARTICLE.get(), new PaintParticle.Provider());
    }
}
