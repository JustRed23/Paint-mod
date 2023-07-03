package dev.JustRed23.paint.particle;

import dev.JustRed23.paint.particle.paint.PaintParticleData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class PaintParticle extends TextureSheetParticle {

    private static final float colorMultiplier = 0.6F;

    private final float uo;
    private final float vo;

    protected PaintParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd, BlockState state, BlockPos pos, int color) {
        super(level, x, y, z, xd, yd, zd);
        this.gravity = 1.0F;

        this.setSprite(Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getParticleIcon(state));

        this.rCol = colorMultiplier * (float) (color >> 16 & 255) / 255.0F;
        this.gCol = colorMultiplier * (float) (color >> 8  & 255) / 255.0F;
        this.bCol = colorMultiplier * (float) (color       & 255) / 255.0F;

        this.quadSize /= 2.0F;
        this.uo = this.random.nextFloat() * 3.0F;
        this.vo = this.random.nextFloat() * 3.0F;
    }

    protected float getU0() {
        return this.sprite.getU((this.uo + 1.0F) / 4.0F * 16.0F);
    }

    protected float getU1() {
        return this.sprite.getU(this.uo / 4.0F * 16.0F);
    }

    protected float getV0() {
        return this.sprite.getV(this.vo / 4.0F * 16.0F);
    }

    protected float getV1() {
        return this.sprite.getV((this.vo + 1.0F) / 4.0F * 16.0F);
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.TERRAIN_SHEET;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<PaintParticleData> {
        public PaintParticle createParticle(@NotNull PaintParticleData data, @NotNull ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
            BlockState blockstate = data.getBlockstate();
            return !blockstate.isAir() && !blockstate.is(Blocks.MOVING_PISTON) ? new PaintParticle(level, x, y, z, xd, yd, zd, blockstate, data.getPos(), data.getColor()).updateSprite(blockstate, data.getPos()) : null;
        }
    }

    public PaintParticle updateSprite(BlockState state, BlockPos pos) { //FORGE: we cannot assume that the x y z of the particles match the block pos of the block.
        if (pos != null) // There are cases where we are not able to obtain the correct source pos, and need to fallback to the non-model data version
            this.setSprite(Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getTexture(state, level, pos));
        return this;
    }
}
