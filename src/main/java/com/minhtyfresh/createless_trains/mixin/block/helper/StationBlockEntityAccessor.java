package com.minhtyfresh.createless_trains.mixin.block.helper;

import com.simibubi.create.content.trains.station.StationBlockEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(StationBlockEntity.class)
public interface StationBlockEntityAccessor {

	@Accessor(value = "trainPresent", remap = false)
	void setTrainPresent(boolean value);
}
