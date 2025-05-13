package com.minhtyfresh.createless_train_accessories.mixin;

import com.simibubi.create.content.decoration.steamWhistle.WhistleBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WhistleBlock.class)
public class WhistleBlockMixin {

	@Inject(
			method = "canSurvive",
			at = @At("RETURN"),
			cancellable = true,
			remap = false)
	// allow placing the whistle block anywhere, no longer tied to the fluid tank
	public void canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(true);
	}
}
