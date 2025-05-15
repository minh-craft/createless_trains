package com.minhtyfresh.createless_trains.mixin.block.helper;

import com.simibubi.create.content.logistics.depot.DepotBehaviour;
import com.simibubi.create.content.trains.station.StationBlockEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(StationBlockEntity.class)
public interface StationBlockEntityAccessor {

	@Accessor("trainPresent")
	void setTrainPresent(boolean value);
}
