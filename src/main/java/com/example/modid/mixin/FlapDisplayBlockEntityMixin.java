package com.example.modid.mixin;

import com.simibubi.create.content.trains.display.FlapDisplayBlockEntity;

import com.simibubi.create.content.trains.display.FlapDisplayLayout;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(FlapDisplayBlockEntity.class)
public class FlapDisplayBlockEntityMixin {

	@Shadow(remap = false)
	public List<FlapDisplayLayout> lines;

	// Fix edge case where tick crashes when flap display is first placed because lines = null
	// and the iterator doesn't properly check for that
	@Inject(method = "tick",
			at = @At(
					value = "INVOKE",
					target = "Ljava/util/List;iterator()Ljava/util/Iterator;"),
			cancellable = true,
			remap = false)
	private void injected(CallbackInfo ci) {
		if (this.lines == null) {
			ci.cancel();
		}
	}
}
