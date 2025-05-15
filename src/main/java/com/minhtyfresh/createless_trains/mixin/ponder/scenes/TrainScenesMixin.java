package com.minhtyfresh.createless_trains.mixin.ponder.scenes;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.trains.station.StationBlock;
import com.simibubi.create.foundation.ponder.ElementLink;
import com.simibubi.create.foundation.ponder.PonderPalette;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.Selection;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.ponder.element.ParrotElement;
import com.simibubi.create.foundation.ponder.element.WorldSectionElement;
import com.simibubi.create.foundation.utility.Pointing;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.simibubi.create.infrastructure.ponder.scenes.trains.TrainScenes;


@Mixin(TrainScenes.class)
public class TrainScenesMixin {

	@Inject(method = "schedule",
			at = @At("HEAD"),
			cancellable = true,
			remap = false)
	private static void schedule(SceneBuilder scene, SceneBuildingUtil util, CallbackInfo ci) {
		scene.title("train_schedule", "Using Train Schedules");
		scene.configureBasePlate(1, 0, 9);
		scene.scaleSceneView(0.75F);
		scene.setSceneOffsetY(-1.0F);
		scene.showBasePlate();
		// EDIT: remove blaze
		//scene.world.cycleBlockProperty(util.grid.at(3, 3, 4), BlazeBurnerBlock.HEAT_LEVEL);

		for(int i = 10; i >= 0; --i) {
			scene.world.showSection(util.select.position(i, 1, 4), Direction.DOWN);
			scene.idle(1);
		}

		scene.world.toggleControls(util.grid.at(4, 3, 4));
		scene.world.toggleControls(util.grid.at(4, 3, 7));
		BlockPos stationPos = util.grid.at(5, 1, 1);
		Selection train1 = util.select.fromTo(6, 2, 3, 2, 3, 5);
		Selection train2 = util.select.fromTo(6, 2, 6, 2, 3, 8);
		scene.idle(10);
		scene.world.showSection(util.select.position(stationPos), Direction.DOWN);
		scene.idle(5);
		ElementLink<WorldSectionElement> trainElement1 = scene.world.showIndependentSection(train1, Direction.DOWN);

		// EDIT: add parrot conductor
		ElementLink<ParrotElement> birbConductor = scene.special.createBirb(util.vector.centerOf(3, 3, 4), ParrotElement.FacePointOfInterestPose::new);


		scene.idle(10);
		scene.world.animateTrainStation(stationPos, true);
		scene.idle(10);
		scene.overlay.showText(70).pointAt(util.vector.blockSurface(util.grid.at(3, 3, 4), Direction.WEST)).placeNearTarget().attachKeyFrame().text("Schedules allow Trains to be controlled by other Drivers");
		scene.idle(80);
		Vec3 target = util.vector.topOf(util.grid.at(4, 0, 2));
		scene.overlay.showControls((new InputWindowElement(target, Pointing.RIGHT)).rightClick().withItem(AllItems.SCHEDULE.asStack()), 80);
		scene.overlay.showText(80).pointAt(target).placeNearTarget().attachKeyFrame().colored(PonderPalette.BLUE).text("Right-click with the item in hand to open its Interface");
		scene.idle(100);
		scene.overlay.showControls((new InputWindowElement(util.vector.topOf(util.grid.at(3, 3, 4)), Pointing.DOWN)).rightClick().withItem(AllItems.SCHEDULE.asStack()), 80);
		scene.idle(6);
		// EDIT: parrot instead of blaze
		scene.special.conductorBirb(birbConductor, true);
		// scene.world.conductorBlaze(util.grid.at(3, 3, 4), true);

		scene.overlay.showText(70).pointAt(util.vector.blockSurface(util.grid.at(3, 3, 4), Direction.WEST)).placeNearTarget().attachKeyFrame().text("Once programmed, the Schedule can be handed off to a Train Driver");
		scene.idle(80);
		scene.world.moveSection(trainElement1, util.vector.of(12.0, 0.0, 0.0), 60);
		// EDIT: move conductor
		scene.special.moveParrot(birbConductor, util.vector.of(12.0, 0.0, 0.0), 60);
		scene.world.animateBogey(util.grid.at(4, 2, 4), -12.0F, 60);
		scene.world.animateTrainStation(stationPos, false);
		scene.idle(20);
		scene.world.hideIndependentSection(trainElement1, (Direction)null);
		// EDIT: hide conductor
		scene.special.hideElement(birbConductor, (Direction)null);
		scene.idle(25);
		ElementLink<WorldSectionElement> trainElement2 = scene.world.showIndependentSection(train2, Direction.DOWN);
		scene.world.moveSection(trainElement2, util.vector.of(0.0, 0.0, -3.0), 0);
		scene.idle(10);
		Vec3 birbVec = util.vector.topOf(util.grid.at(3, 0, 7));
		ElementLink<com.simibubi.create.foundation.ponder.element.ParrotElement> birb = scene.special.createBirb(birbVec, ParrotElement.FacePointOfInterestPose::new);
		scene.world.animateTrainStation(stationPos, true);
		// EDIT: remove reference to blaze
		scene.overlay.showText(110).pointAt(birbVec).placeNearTarget().attachKeyFrame().text("Any mob sitting in front of Train Controls is an eligible conductor");
		scene.idle(80);
		scene.overlay.showControls((new InputWindowElement(util.vector.centerOf(util.grid.at(3, 1, 7)), Pointing.DOWN)).withItem(new ItemStack(Items.LEAD)), 30);
		scene.idle(40);
		target = util.vector.centerOf(util.grid.at(3, 3, 4));
		scene.overlay.showControls((new InputWindowElement(target.add(0.5, 0.0, 0.0), Pointing.RIGHT)).rightClick().withItem(new ItemStack(Items.LEAD)), 30);
		scene.idle(6);
		scene.special.moveParrot(birb, target.subtract(birbVec), 5);
		scene.effects.indicateSuccess(util.grid.at(3, 3, 4));
		scene.idle(15);
		scene.overlay.showText(70).pointAt(target).placeNearTarget().colored(PonderPalette.BLUE).attachKeyFrame().text("Creatures on a lead can be given their seat more conveniently");
		scene.idle(80);
		scene.overlay.showControls((new InputWindowElement(util.vector.topOf(util.grid.at(3, 3, 4)), Pointing.DOWN)).withItem(AllItems.SCHEDULE.asStack()), 15);
		scene.idle(6);
		scene.special.conductorBirb(birb, true);
		scene.special.movePointOfInterest(util.grid.at(16, 4, 4));
		scene.idle(14);
		scene.world.moveSection(trainElement2, util.vector.of(3.0, 0.0, 0.0), 30);
		scene.world.animateBogey(util.grid.at(4, 2, 7), -3.0F, 30);
		scene.special.moveParrot(birb, util.vector.of(3.0, 0.0, 0.0), 30);
		scene.idle(40);
		scene.overlay.showControls((new InputWindowElement(util.vector.topOf(util.grid.at(6, 3, 4)), Pointing.DOWN)).rightClick(), 70);
		scene.idle(6);
		scene.special.conductorBirb(birb, false);
		scene.special.movePointOfInterest(util.grid.at(3, 4, 1));
		scene.idle(19);
		scene.overlay.showText(70).pointAt(target.add(3.0, 0.0, 0.0)).placeNearTarget().colored(PonderPalette.BLUE).attachKeyFrame().text("Schedules can be retrieved from Drivers at any moment");
		scene.idle(80);

		ci.cancel();
	}

}
