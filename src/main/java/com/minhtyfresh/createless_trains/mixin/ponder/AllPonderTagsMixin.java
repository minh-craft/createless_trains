package com.minhtyfresh.createless_trains.mixin.ponder;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderTag;
import com.simibubi.create.infrastructure.ponder.AllPonderTags;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AllPonderTags.class)
public class AllPonderTagsMixin {
//	@Final
//	@Mutable
//	@Shadow(remap = false)
//	public static PonderTag DECORATION;

	@Final
	@Mutable
	@Shadow(remap = false)
	public static PonderTag TRAIN_RELATED;

	@Final
	@Mutable
	@Shadow(remap = false)
	public static PonderTag DISPLAY_SOURCES;

	@Final
	@Mutable
	@Shadow(remap = false)
	public static PonderTag DISPLAY_TARGETS;

	@Inject(
			method = "register",
			at = @At("HEAD"),
			cancellable = true,
			remap = false
	)
	// register only the ponder indexes related to trains, skip registering other ponders
	private static void register(CallbackInfo ci) {
		//PonderRegistry.TAGS.forTag(DECORATION).add(AllBlocks.ORANGE_NIXIE_TUBE).add(AllBlocks.DISPLAY_LINK).add(AllBlocks.DISPLAY_BOARD).add(AllBlocks.TRAIN_DOOR).add(AllBlocks.TRAIN_TRAPDOOR).add(AllBlocks.COPYCAT_PANEL);
		PonderRegistry.TAGS.forTag(TRAIN_RELATED)
				.add(AllBlocks.RAILWAY_CASING)
				.add(AllBlocks.TRACK).add(AllBlocks.TRACK_STATION)
				.add(AllItems.SUPER_GLUE)
				.add(AllBlocks.TRAIN_CONTROLS)
				.add(AllBlocks.STEAM_WHISTLE)
				//.add(AllBlocks.TRAIN_DOOR)
				.add(AllItems.SCHEDULE)
				.add(AllBlocks.TRACK_SIGNAL).add(AllBlocks.ORANGE_NIXIE_TUBE)
				.add(AllBlocks.TRACK_OBSERVER)
		        .add(AllBlocks.DISPLAY_LINK).add(AllBlocks.DISPLAY_BOARD);
		PonderRegistry.TAGS.forTag(DISPLAY_SOURCES).add(AllBlocks.SEATS.get(DyeColor.WHITE)).add(AllBlocks.ORANGE_NIXIE_TUBE).add(AllBlocks.TRACK_OBSERVER).add(AllBlocks.TRACK_STATION).add(AllBlocks.DISPLAY_LINK).add(Blocks.TARGET);
		PonderRegistry.TAGS.forTag(DISPLAY_TARGETS).add(AllBlocks.ORANGE_NIXIE_TUBE).add(AllBlocks.DISPLAY_BOARD).add(AllBlocks.DISPLAY_LINK).add(Blocks.OAK_SIGN).add(Blocks.LECTERN);
		ci.cancel();
	}

	@Unique
	private static PonderTag create(String id) {
		return new PonderTag(Create.asResource(id));
	}

	@Inject(
			method = "<clinit>",
			at = @At("HEAD"),
			cancellable = true,
			remap = false
	)
	private static void clinit(CallbackInfo ci){
		TRAIN_RELATED = create("train_related").item(AllBlocks.TRACK.get()).defaultLang("Railway Equipment", "Components used in the construction or management of Trains").addToIndex();
		//DECORATION = create("decoration").item(AllBlocks.ORANGE_NIXIE_TUBE).defaultLang("Aesthetics", "Components used mostly for decorative purposes").addToIndex();
		DISPLAY_SOURCES = create("display_sources").item(AllBlocks.DISPLAY_LINK.get(), true, true).defaultLang("Sources for Display Links", "Components or Blocks which offer some data that can be read with a Display Link");
		DISPLAY_TARGETS = create("display_targets").item(AllBlocks.DISPLAY_LINK.get(), true, true).defaultLang("Targets for Display Links", "Components or Blocks which can process and display the data received from a Display Link");

		ci.cancel();
	}
}
