package com.minhtyfresh.createless_train_accessories.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.simibubi.create.content.decoration.steamWhistle.WhistleBlockEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WhistleBlockEntity.class)
public abstract class WhistleBlockEntityMixin {

	@Shadow(remap = false)
	protected abstract boolean isPowered();

	@Inject(
			method = "tick",
			at = @At(
					value = "INVOKE",
					target = "Lcom/simibubi/create/foundation/utility/animation/LerpedFloat;chase(DDLcom/simibubi/create/foundation/utility/animation/LerpedFloat$Chaser;)Lcom/simibubi/create/foundation/utility/animation/LerpedFloat;"),
			remap = false
	)
	// whistle powered state no relies on fluid tanks or heat sources underneath
	private void injected(CallbackInfo ci, @Local LocalBooleanRef localRef) {
		localRef.set(this.isPowered());
	}
}
