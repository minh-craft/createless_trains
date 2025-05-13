package com.minhtyfresh.createless_trains.mixin.ponder;

import com.minhtyfresh.createless_trains.ponder.TrainStationScenes;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.Create;
import com.simibubi.create.content.trains.track.TrackBlock;
import com.simibubi.create.content.trains.track.TrackMaterial;
import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import com.simibubi.create.infrastructure.ponder.PonderIndex;

import com.simibubi.create.infrastructure.ponder.scenes.DisplayScenes;
import com.simibubi.create.infrastructure.ponder.scenes.trains.TrackObserverScenes;
import com.simibubi.create.infrastructure.ponder.scenes.trains.TrackScenes;
import com.simibubi.create.infrastructure.ponder.scenes.trains.TrainScenes;
import com.simibubi.create.infrastructure.ponder.scenes.trains.TrainSignalScenes;
//import com.simibubi.create.infrastructure.ponder.scenes.trains.TrainStationScenes;
import com.tterrag.registrate.fabric.RegistryObject;
import com.tterrag.registrate.util.entry.BlockEntry;

import net.minecraft.core.registries.BuiltInRegistries;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PonderIndex.class)
public class PonderIndexMixin {
	@Unique
	private static final PonderRegistrationHelper HELPER = new PonderRegistrationHelper("create");

	@Inject(method = "register",
			at = @At("HEAD"),
			cancellable = true,
			remap = false)
	// register only the ponders related to trains, skip all other ponder registrations
	private static void register(CallbackInfo ci){
		// TODO add back later
//		// Redstone
//		HELPER.forComponents(AllBlocks.ORANGE_NIXIE_TUBE)
//				.addStoryBoard("nixie_tube", RedstoneScenes::nixieTube);

		// TODO maybe add back later, would have to remove everything related to fluid tanks
		// Steam
//		HELPER.forComponents(AllBlocks.STEAM_WHISTLE)
//				.addStoryBoard("steam_whistle", SteamScenes::whistle);

		// Trains
		HELPER.forComponents(TrackMaterial.allBlocks()
						.stream()
						.map((trackSupplier) -> new BlockEntry<TrackBlock>(
								// note: these blocks probably WON'T be in the Create Registrate, but a simple
								// code trace reveals the Entry's registrate isn't used
								Create.REGISTRATE,
								RegistryObject.of(BuiltInRegistries.BLOCK.getKey(trackSupplier.get()), BuiltInRegistries.BLOCK)))
						.toList())
				.addStoryBoard("train_track/placement", TrackScenes::placement)
				.addStoryBoard("train_track/portal", TrackScenes::portal)
				.addStoryBoard("train_track/chunks", TrackScenes::chunks);

		HELPER.forComponents(AllBlocks.TRACK_STATION)
				.addStoryBoard("train_station/assembly", TrainStationScenes::assembly)
				.addStoryBoard("train_station/schedule", TrainStationScenes::autoSchedule);

		HELPER.forComponents(AllBlocks.TRACK_SIGNAL)
				.addStoryBoard("train_signal/placement", TrainSignalScenes::placement)
				.addStoryBoard("train_signal/signaling", TrainSignalScenes::signaling)
				.addStoryBoard("train_signal/redstone", TrainSignalScenes::redstone);

		HELPER.forComponents(AllItems.SCHEDULE)
				.addStoryBoard("train_schedule", TrainScenes::schedule);

		HELPER.forComponents(AllBlocks.TRAIN_CONTROLS)
				.addStoryBoard("train_controls", TrainScenes::controls);

		HELPER.forComponents(AllBlocks.TRACK_OBSERVER)
				.addStoryBoard("train_observer", TrackObserverScenes::observe);

		// Display Link
		HELPER.forComponents(AllBlocks.DISPLAY_LINK)
				.addStoryBoard("display_link", DisplayScenes::link)
				.addStoryBoard("display_link_redstone", DisplayScenes::redstone);
		HELPER.forComponents(AllBlocks.DISPLAY_BOARD)
				.addStoryBoard("display_board", DisplayScenes::board);


		ci.cancel();
	}
}
