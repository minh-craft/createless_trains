package com.minhtyfresh.createless_trains.mixin.block;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.minhtyfresh.createless_trains.mixin.block.helper.StationBlockEntityAccessor;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.logistics.depot.SharedDepotBlockMethods;
import com.simibubi.create.content.trains.station.StationBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Function;

@Mixin(StationBlock.class)
public class StationBlockMixin {
	@WrapOperation(
			method = "use",
			at = @At(
					value = "INVOKE",
					target = "Lcom/simibubi/create/content/trains/station/StationBlock;onBlockEntityUse(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Ljava/util/function/Function;)Lnet/minecraft/world/InteractionResult;"
			)
	)
	// Allow directly inserting schedules into train station blocks by right-clicking with it in hand
	private InteractionResult use(
			StationBlock instance,
			BlockGetter blockGetter,
			BlockPos blockPos,
			Function function,
			Operation<InteractionResult> original,
			// enclosing method parameters
			BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit)
	{
		return instance.onBlockEntityUse(blockGetter, blockPos, (station) -> {
			ItemStack autoSchedule = station.getAutoSchedule();
			if (autoSchedule.isEmpty()) {
				if( AllItems.SCHEDULE.isIn(pPlayer.getItemInHand(pHand))) {
					SharedDepotBlockMethods.onUse(pState, pLevel, pPos, pPlayer, pHand, pHit);
					// janky workaround to force trains already present at the station look newly arrived so that the schedule gets auto applied
					((StationBlockEntityAccessor)station).setTrainPresent(false);
					station.notifyUpdate();
					return InteractionResult.SUCCESS;
				}
				return InteractionResult.PASS;
			} else {
				return original.call(instance, blockGetter, blockPos, function);
			}
		});
	}

}
