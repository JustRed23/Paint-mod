package dev.JustRed23.paint;

import dev.JustRed23.paint.blocks.blockentities.template.ColoredBlockEntity;
import dev.JustRed23.paint.items.EyeDropperItem;
import dev.JustRed23.paint.items.PaintBucketItem;
import dev.JustRed23.paint.items.PaintbrushItem;
import dev.JustRed23.paint.particle.PaintParticle;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Paint.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class PaintClient {

    private static final ResourceLocation EMPTY = new ResourceLocation(Paint.MOD_ID, "empty");

    @SubscribeEvent
    public static void onSetup(FMLClientSetupEvent event) {
        final PaintbrushItem paintbrush = (PaintbrushItem) ModItems.PAINTBRUSH.get();
        ItemProperties.register(paintbrush, EMPTY, (stack, world, entity, seed) -> paintbrush.canUse(stack) ? 0.0F : 1.0F);

        final EyeDropperItem eyeDropper = (EyeDropperItem) ModItems.EYEDROPPER.get();
        ItemProperties.register(eyeDropper, EMPTY, (stack, world, entity, seed) -> eyeDropper.hasColor(stack) ? 0.0F : 1.0F);

        final PaintBucketItem paintBucket = (PaintBucketItem) ModItems.getBlockItem("paint_bucket").get();
        ItemProperties.register(paintBucket, EMPTY, (stack, world, entity, seed) -> paintBucket.canUse(stack) ? 0.0F : 1.0F);
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> {
            if (tintIndex == 1 && stack.hasTag() && stack.getTag().contains("paintColor"))
                return stack.getTag().getInt("paintColor");
            return -1;
        }, ModItems.PAINTBRUSH.get(), ModItems.EYEDROPPER.get(), ModItems.getBlockItem("paint_bucket").get());

        for (RegistryObject<Item> item : ModItems.getPaintedBlockItems()) {
            event.register((stack, tintIndex) -> {
                if (tintIndex == 0 && stack.hasTag() && stack.getTag().contains("paintColor"))
                    return stack.getTag().getInt("paintColor");
                return -1;
            }, item.get());
        }
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        registerColored(event, ModBlocks.PAINT_BUCKET.get());

        for (RegistryObject<Block> block : ModBlocks.getPaintedBlocks()) {
            registerColored(event, block.get());
        }
    }

    private static void registerColored(RegisterColorHandlersEvent.Block event, Block block) {
        event.register((state, world, pos, tintIndex) -> {
            if (world != null && pos != null && tintIndex == 0) {
                ColoredBlockEntity blockEntity = (ColoredBlockEntity) world.getBlockEntity(pos);
                if (blockEntity != null)
                    return blockEntity.getColor();
            }
            return -1;
        }, block);
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpecial(ModParticles.PAINT_PARTICLE.get(), new PaintParticle.Provider());
    }
}
