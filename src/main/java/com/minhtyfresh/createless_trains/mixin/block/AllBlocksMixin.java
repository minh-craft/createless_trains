package com.minhtyfresh.createless_trains.mixin.block;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.AllBlocks;

import com.simibubi.create.foundation.item.ItemDescription;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.Builder;
import com.tterrag.registrate.util.nullness.NonNullConsumer;

import net.minecraft.resources.ResourceKey;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AllBlocks.class)
public class AllBlocksMixin {
	@WrapOperation(
			method = "lambda$static$376",
			at = @At(
				value = "INVOKE",
					target = "Lcom/tterrag/registrate/builders/BlockBuilder;onRegisterAfter(Lnet/minecraft/resources/ResourceKey;Lcom/tterrag/registrate/util/nullness/NonNullConsumer;)Lcom/tterrag/registrate/builders/Builder;"
			),
			remap = false
	)
	// Don't register seat item tooltips
	private static Builder test(BlockBuilder instance, ResourceKey resourceKey, NonNullConsumer nonNullConsumer, Operation<Builder> original) {
		return original.call(instance, resourceKey, NonNullConsumer.noop());
	}
}
