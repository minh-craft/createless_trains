package com.minhtyfresh.createless_trains.mixin.block;

import com.simibubi.create.content.decoration.steamWhistle.WhistleBlockEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(WhistleBlockEntity.class)
public abstract class WhistleBlockEntityMixin {

	@Shadow(remap = false)
	protected abstract boolean isPowered();

	@ModifyVariable(
			method = "tick",
			at = @At(value = "STORE", ordinal = 0),
			remap = false
	)
	// whistle powered state no longer relies on fluid tanks or heat sources underneath
	private boolean injected(boolean value) {
		return this.isPowered();
	}
}
