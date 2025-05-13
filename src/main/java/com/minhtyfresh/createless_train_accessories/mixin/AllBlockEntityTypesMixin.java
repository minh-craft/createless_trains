package com.minhtyfresh.createless_train_accessories.mixin;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstance;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.AllBlockEntityTypes;

import com.simibubi.create.content.kinetics.crafter.ShaftlessCogwheelInstance;
import com.simibubi.create.content.trains.display.FlapDisplayBlockEntity;
import com.simibubi.create.foundation.data.CreateBlockEntityBuilder;

import com.tterrag.registrate.util.nullness.NonNullSupplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.function.BiFunction;

@Mixin(AllBlockEntityTypes.class)
public class AllBlockEntityTypesMixin {

	@WrapOperation(
			method = "<clinit>",
			at = @At(
					value = "INVOKE",
					target = "Lcom/simibubi/create/foundation/data/CreateBlockEntityBuilder;instance(Lcom/tterrag/registrate/util/nullness/NonNullSupplier;)Lcom/simibubi/create/foundation/data/CreateBlockEntityBuilder;"),
			slice = @Slice(
					from = @At(
							value = "FIELD",
							target = "Lcom/simibubi/create/AllBlockEntityTypes;COPYCAT:Lcom/tterrag/registrate/util/entry/BlockEntityEntry;"),
					to = @At(
							value = "FIELD",
							target = "Lcom/simibubi/create/AllBlockEntityTypes;FLAP_DISPLAY:Lcom/tterrag/registrate/util/entry/BlockEntityEntry;")
			),
			remap = false
	)
	// Remove cogs from Display Board by removing ".instance(() -> { return ShaftlessCogwheelInstance::new; })"
	private static CreateBlockEntityBuilder<FlapDisplayBlockEntity, ShaftlessCogwheelInstance> clinit(
			CreateBlockEntityBuilder instance,
			NonNullSupplier<BiFunction<MaterialManager, FlapDisplayBlockEntity, BlockEntityInstance<? super FlapDisplayBlockEntity>>> instanceFactory,
			Operation<CreateBlockEntityBuilder<FlapDisplayBlockEntity, ShaftlessCogwheelInstance>> original) {
		return instance;
	}
}
