package com.example.modid.mixin;

import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.trains.display.FlapDisplayBlock;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlapDisplayBlock.class)
public class FlapDisplayBlockMixin {

	@Inject(
			method = "getMinimumRequiredSpeedLevel",
			at = @At("RETURN"),
			cancellable = true,
			remap = false)
	// make display not require power
	public void getMinimumRequiredSpeedLevel(CallbackInfoReturnable<IRotate.SpeedLevel> cir) {
		cir.setReturnValue(IRotate.SpeedLevel.NONE);
	}
}
