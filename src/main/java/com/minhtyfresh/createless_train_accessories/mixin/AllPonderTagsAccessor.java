package com.minhtyfresh.createless_train_accessories.mixin;

import com.simibubi.create.foundation.ponder.PonderTag;
import com.simibubi.create.infrastructure.ponder.AllPonderTags;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AllPonderTags.class)
public interface AllPonderTagsAccessor {
	@Accessor(value = "TRAIN_RELATED", remap = false)
	public static void setTrainRelated(PonderTag tag) {
		throw new AssertionError();
	}
	@Accessor(value = "DISPLAY_SOURCES", remap = false)
	public static void setDisplaySources(PonderTag tag) {
		throw new AssertionError();
	}
	@Accessor(value = "DISPLAY_TARGETS", remap = false)
	public static void setDisplayTargets(PonderTag tag) {
		throw new AssertionError();
	}
}
