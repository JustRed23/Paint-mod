package dev.JustRed23.paint.providers;

import dev.JustRed23.paint.ModBlocks;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class PaintLootProvider extends LootTableProvider {

    public PaintLootProvider(PackOutput packOutput) {
        super(packOutput, Set.of(), List.of(new SubProviderEntry(Blocks::new, LootContextParamSets.BLOCK)));
    }

    static class Blocks extends BlockLootSubProvider {

        public Blocks() {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags());
        }

        protected void generate() {
            this.add(ModBlocks.PAINTED_BED.get(), (block) -> this.dropPainted(block, BedBlock.PART, BedPart.HEAD));
        }

        protected <T extends Comparable<T> & StringRepresentable> LootTable.Builder dropPainted(Block p_252154_, Property<T> p_250272_, T p_250292_) {
            return LootTable.lootTable().withPool(this.applyExplosionCondition(p_252154_, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(p_252154_).apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("color", "paintColor"))).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_252154_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(p_250272_, p_250292_)))));
        }

        protected @NotNull Iterable<Block> getKnownBlocks() {
            return List.of(ModBlocks.PAINTED_BED.get());
        }
    }
}
