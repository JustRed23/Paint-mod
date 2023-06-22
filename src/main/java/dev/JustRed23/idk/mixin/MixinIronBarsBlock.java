package dev.JustRed23.idk.mixin;

import dev.JustRed23.idk.blocks.paintedblockvariants.PaintedGlassPane;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IronBarsBlock.class)
public class MixinIronBarsBlock {

    @Inject(method = "attachsTo", at = @At("HEAD"), cancellable = true)
    public void attachsTo(BlockState p_54218_, boolean p_54219_, CallbackInfoReturnable<Boolean> cir) {
        if (p_54218_.getBlock() instanceof PaintedGlassPane)
            cir.setReturnValue(true);
    }
}
