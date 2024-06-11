package dev.JustRed23.paint.blocks.paintedblockvariants;

import dev.JustRed23.paint.ModBlockEntities;
import dev.JustRed23.paint.blocks.PaintedBlock;
import dev.JustRed23.paint.blocks.blockentities.PaintedBlockEntity;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class PaintedGlassPane extends PaintedBlock {

    public static final BooleanProperty NORTH = PipeBlock.NORTH;
    public static final BooleanProperty EAST = PipeBlock.EAST;
    public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
    public static final BooleanProperty WEST = PipeBlock.WEST;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION.entrySet().stream().filter((p_52346_) -> {
        return p_52346_.getKey().getAxis().isHorizontal();
    }).collect(Util.toMap());

    protected final VoxelShape[] shape;
    private final Object2IntMap<BlockState> stateToIndex = new Object2IntOpenHashMap<>();

    public PaintedGlassPane() {
        super(BlockBehaviour.Properties.copy(Blocks.GLASS_PANE).noParticlesOnBreak());

        this.shape = this.makeShapes();

        for(BlockState blockstate : this.stateDefinition.getPossibleStates()) {
            this.getAABBIndex(blockstate);
        }

        this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, Boolean.valueOf(false)).setValue(EAST, Boolean.valueOf(false)).setValue(SOUTH, Boolean.valueOf(false)).setValue(WEST, Boolean.valueOf(false)).setValue(WATERLOGGED, Boolean.valueOf(false)));
    }

    public BlockEntityType<PaintedBlockEntity> getBlockEntityType() {
        return ModBlockEntities.PAINTED_GLASS_PANE_ENTITY.get();
    }

    protected VoxelShape[] makeShapes() {
        float f = 8.0F - (float) 1.0;
        float f1 = 8.0F + (float) 1.0;
        float f2 = 8.0F - (float) 1.0;
        float f3 = 8.0F + (float) 1.0;
        VoxelShape voxelshape = Block.box((double)f, 0.0D, (double)f, (double)f1, (double) (float) 16.0, (double)f1);
        VoxelShape voxelshape1 = Block.box((double)f2, (double) (float) 0.0, 0.0D, (double)f3, (double) (float) 16.0, (double)f3);
        VoxelShape voxelshape2 = Block.box((double)f2, (double) (float) 0.0, (double)f2, (double)f3, (double) (float) 16.0, 16.0D);
        VoxelShape voxelshape3 = Block.box(0.0D, (double) (float) 0.0, (double)f2, (double)f3, (double) (float) 16.0, (double)f3);
        VoxelShape voxelshape4 = Block.box((double)f2, (double) (float) 0.0, (double)f2, 16.0D, (double) (float) 16.0, (double)f3);
        VoxelShape voxelshape5 = Shapes.or(voxelshape1, voxelshape4);
        VoxelShape voxelshape6 = Shapes.or(voxelshape2, voxelshape3);
        VoxelShape[] avoxelshape = new VoxelShape[]{Shapes.empty(), voxelshape2, voxelshape3, voxelshape6, voxelshape1, Shapes.or(voxelshape2, voxelshape1), Shapes.or(voxelshape3, voxelshape1), Shapes.or(voxelshape6, voxelshape1), voxelshape4, Shapes.or(voxelshape2, voxelshape4), Shapes.or(voxelshape3, voxelshape4), Shapes.or(voxelshape6, voxelshape4), voxelshape5, Shapes.or(voxelshape2, voxelshape5), Shapes.or(voxelshape3, voxelshape5), Shapes.or(voxelshape6, voxelshape5)};

        for(int i = 0; i < 16; ++i) {
            avoxelshape[i] = Shapes.or(voxelshape, avoxelshape[i]);
        }

        return avoxelshape;
    }

    public float @Nullable [] getBeaconColorMultiplier(BlockState state, LevelReader level, BlockPos pos, BlockPos beaconPos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof PaintedBlockEntity paintedBlockEntity) {
            int color = paintedBlockEntity.getColor();
            float[] rgb = new float[3];
            rgb[0] = (float) (color >> 16 & 255) / 255.0F;
            rgb[1] = (float) (color >> 8 & 255) / 255.0F;
            rgb[2] = (float) (color & 255) / 255.0F;
            return rgb;
        }
        return null;
    }

    public BlockState getStateForPlacement(BlockPlaceContext p_54200_) {
        BlockGetter blockgetter = p_54200_.getLevel();
        BlockPos blockpos = p_54200_.getClickedPos();
        FluidState fluidstate = p_54200_.getLevel().getFluidState(p_54200_.getClickedPos());
        BlockPos blockpos1 = blockpos.north();
        BlockPos blockpos2 = blockpos.south();
        BlockPos blockpos3 = blockpos.west();
        BlockPos blockpos4 = blockpos.east();
        BlockState blockstate = blockgetter.getBlockState(blockpos1);
        BlockState blockstate1 = blockgetter.getBlockState(blockpos2);
        BlockState blockstate2 = blockgetter.getBlockState(blockpos3);
        BlockState blockstate3 = blockgetter.getBlockState(blockpos4);
        return this.defaultBlockState().setValue(NORTH, Boolean.valueOf(this.attachsTo(blockstate, blockstate.isFaceSturdy(blockgetter, blockpos1, Direction.SOUTH)))).setValue(SOUTH, Boolean.valueOf(this.attachsTo(blockstate1, blockstate1.isFaceSturdy(blockgetter, blockpos2, Direction.NORTH)))).setValue(WEST, Boolean.valueOf(this.attachsTo(blockstate2, blockstate2.isFaceSturdy(blockgetter, blockpos3, Direction.EAST)))).setValue(EAST, Boolean.valueOf(this.attachsTo(blockstate3, blockstate3.isFaceSturdy(blockgetter, blockpos4, Direction.WEST)))).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
    }

    public BlockState updateShape(BlockState p_54211_, Direction p_54212_, BlockState p_54213_, LevelAccessor p_54214_, BlockPos p_54215_, BlockPos p_54216_) {
        if (p_54211_.getValue(WATERLOGGED)) {
            p_54214_.scheduleTick(p_54215_, Fluids.WATER, Fluids.WATER.getTickDelay(p_54214_));
        }

        return p_54212_.getAxis().isHorizontal() ? p_54211_.setValue(PROPERTY_BY_DIRECTION.get(p_54212_), Boolean.valueOf(this.attachsTo(p_54213_, p_54213_.isFaceSturdy(p_54214_, p_54216_, p_54212_.getOpposite())))) : super.updateShape(p_54211_, p_54212_, p_54213_, p_54214_, p_54215_, p_54216_);
    }

    public VoxelShape getVisualShape(BlockState p_54202_, BlockGetter p_54203_, BlockPos p_54204_, CollisionContext p_54205_) {
        return Shapes.empty();
    }

    public boolean skipRendering(BlockState p_54207_, BlockState p_54208_, Direction p_54209_) {
        if (p_54208_.is(this)) {
            if (!p_54209_.getAxis().isHorizontal()) {
                return true;
            }

            if (p_54207_.getValue(PROPERTY_BY_DIRECTION.get(p_54209_)) && p_54208_.getValue(PROPERTY_BY_DIRECTION.get(p_54209_.getOpposite()))) {
                return true;
            }
        }

        return super.skipRendering(p_54207_, p_54208_, p_54209_);
    }

    public final boolean attachsTo(BlockState p_54218_, boolean p_54219_) {
        return !isExceptionForConnection(p_54218_) && p_54219_ ||
                p_54218_.getBlock() instanceof PaintedGlassPane ||
                p_54218_.getBlock() instanceof IronBarsBlock ||
                p_54218_.is(BlockTags.WALLS);
    }

    public boolean propagatesSkylightDown(BlockState p_52348_, BlockGetter p_52349_, BlockPos p_52350_) {
        return !p_52348_.getValue(WATERLOGGED);
    }

    public VoxelShape getShape(BlockState p_52352_, BlockGetter p_52353_, BlockPos p_52354_, CollisionContext p_52355_) {
        return this.shape[this.getAABBIndex(p_52352_)];
    }

    public VoxelShape getCollisionShape(BlockState p_52357_, BlockGetter p_52358_, BlockPos p_52359_, CollisionContext p_52360_) {
        return this.shape[this.getAABBIndex(p_52357_)];
    }

    public float getShadeBrightness(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos) {
        return 1.0F;
    }

    private static int indexFor(Direction p_52344_) {
        return 1 << p_52344_.get2DDataValue();
    }

    protected int getAABBIndex(BlockState p_52364_) {
        return this.stateToIndex.computeIntIfAbsent(p_52364_, (p_52366_) -> {
            int i = 0;
            if (p_52366_.getValue(NORTH)) {
                i |= indexFor(Direction.NORTH);
            }

            if (p_52366_.getValue(EAST)) {
                i |= indexFor(Direction.EAST);
            }

            if (p_52366_.getValue(SOUTH)) {
                i |= indexFor(Direction.SOUTH);
            }

            if (p_52366_.getValue(WEST)) {
                i |= indexFor(Direction.WEST);
            }

            return i;
        });
    }

    public FluidState getFluidState(BlockState p_52362_) {
        return p_52362_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_52362_);
    }

    public boolean isPathfindable(BlockState p_52333_, BlockGetter p_52334_, BlockPos p_52335_, PathComputationType p_52336_) {
        return false;
    }

    public BlockState rotate(BlockState p_52341_, Rotation p_52342_) {
        switch (p_52342_) {
            case CLOCKWISE_180:
                return p_52341_.setValue(NORTH, p_52341_.getValue(SOUTH)).setValue(EAST, p_52341_.getValue(WEST)).setValue(SOUTH, p_52341_.getValue(NORTH)).setValue(WEST, p_52341_.getValue(EAST));
            case COUNTERCLOCKWISE_90:
                return p_52341_.setValue(NORTH, p_52341_.getValue(EAST)).setValue(EAST, p_52341_.getValue(SOUTH)).setValue(SOUTH, p_52341_.getValue(WEST)).setValue(WEST, p_52341_.getValue(NORTH));
            case CLOCKWISE_90:
                return p_52341_.setValue(NORTH, p_52341_.getValue(WEST)).setValue(EAST, p_52341_.getValue(NORTH)).setValue(SOUTH, p_52341_.getValue(EAST)).setValue(WEST, p_52341_.getValue(SOUTH));
            default:
                return p_52341_;
        }
    }

    public BlockState mirror(BlockState p_52338_, Mirror p_52339_) {
        switch (p_52339_) {
            case LEFT_RIGHT:
                return p_52338_.setValue(NORTH, p_52338_.getValue(SOUTH)).setValue(SOUTH, p_52338_.getValue(NORTH));
            case FRONT_BACK:
                return p_52338_.setValue(EAST, p_52338_.getValue(WEST)).setValue(WEST, p_52338_.getValue(EAST));
            default:
                return super.mirror(p_52338_, p_52339_);
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_54221_) {
        p_54221_.add(NORTH, EAST, WEST, SOUTH, WATERLOGGED);
    }
}
