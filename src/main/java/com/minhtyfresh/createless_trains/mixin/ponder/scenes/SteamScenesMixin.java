package com.minhtyfresh.createless_trains.mixin.ponder.scenes;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.decoration.steamWhistle.WhistleBlock;
import com.simibubi.create.content.decoration.steamWhistle.WhistleExtenderBlock;
import com.simibubi.create.foundation.ponder.ElementLink;
import com.simibubi.create.foundation.ponder.PonderPalette;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.Selection;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.ponder.element.WorldSectionElement;
import com.simibubi.create.foundation.utility.Pointing;
import com.simibubi.create.infrastructure.ponder.scenes.SteamScenes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SteamScenes.class)
public class SteamScenesMixin {
	@Inject(
			method = "whistle",
			at = @At("HEAD"),
			cancellable = true,
			remap = false
	)
	private static void whistle(SceneBuilder scene, SceneBuildingUtil util, CallbackInfo ci) {
		scene.title("steam_whistle", "Setting up Steam Whistles");
		scene.configureBasePlate(0, 0, 5);
		scene.showBasePlate();
		Selection tank = util.select.fromTo(3, 1, 2, 3, 2, 2);
		Selection boiler = util.select.fromTo(2, 2, 2, 2, 3, 2);
		BlockPos leverPos = util.grid.at(1, 3, 2);
		Selection lever = util.select.position(leverPos);
		Selection whistleArea = util.select.fromTo(2, 3, 1, 2, 7, 1);
		BlockPos whistlePos = util.grid.at(2, 3, 1);
		Selection campfire = util.select.position(2, 1, 2);
//		scene.idle(15);
		ElementLink<WorldSectionElement> tankElement = scene.world.showIndependentSection(tank, Direction.DOWN);
		scene.world.moveSection(tankElement, util.vector.of(-1.0, 0.0, 0.0), 0);
//		scene.idle(10);
		ElementLink<WorldSectionElement> whistleElement = scene.world.showIndependentSection(whistleArea, Direction.SOUTH);
		scene.world.moveSection(whistleElement, util.vector.of(0.0, -1.0, 0.0), 0);
//		scene.idle(15);
		scene.world.moveSection(tankElement, util.vector.of(0.0, -1000.0, 0.0), 0);
		scene.world.hideIndependentSection(tankElement, (Direction)null);
		ElementLink<WorldSectionElement> boilerElement = scene.world.showIndependentSectionImmediately(boiler);
		scene.world.moveSection(boilerElement, util.vector.of(0.0, -1.0, 0.0), 0);
		scene.effects.indicateSuccess(util.grid.at(2, 1, 2));
//		scene.idle(25);
//		scene.overlay.showText(70).attachKeyFrame().text("Steam Whistles can be placed on a Fluid Tank").pointAt(util.vector.blockSurface(util.grid.at(2, 2, 2), Direction.NORTH)).placeNearTarget();
//		scene.idle(60);
		scene.world.moveSection(boilerElement, util.vector.of(0.0, 1.0, 0.0), 15);
		scene.world.moveSection(whistleElement, util.vector.of(0.0, 1.0, 0.0), 15);
//		scene.idle(10);
		scene.world.showSection(campfire, Direction.NORTH);
//		scene.idle(15);
//		scene.overlay.showText(50).attachKeyFrame().text("If the tank receives sufficient heat...").pointAt(util.vector.blockSurface(util.grid.at(2, 1, 2), Direction.WEST)).placeNearTarget();
		scene.idle(40);
		scene.world.showSection(lever, Direction.DOWN);
		scene.idle(20);
		scene.world.toggleRedstonePower(whistleArea);
		scene.world.toggleRedstonePower(lever);
		scene.effects.indicateRedstone(leverPos);
		scene.idle(10);
		scene.overlay.showText(70).attachKeyFrame().text("The Whistle will play a note when activated").pointAt(util.vector.blockSurface(util.grid.at(2, 3, 2), Direction.NORTH)).placeNearTarget();
		scene.idle(10);
		scene.world.toggleRedstonePower(whistleArea);
		scene.world.toggleRedstonePower(lever);
		scene.idle(20);
		scene.world.toggleRedstonePower(whistleArea);
		scene.world.toggleRedstonePower(lever);
		scene.effects.indicateRedstone(leverPos);
		scene.idle(20);
		scene.world.toggleRedstonePower(whistleArea);
		scene.world.toggleRedstonePower(lever);
		scene.idle(40);
		InputWindowElement rightClick = (new InputWindowElement(util.vector.blockSurface(util.grid.at(2, 3, 1), Direction.EAST), Pointing.RIGHT)).withItem(AllBlocks.STEAM_WHISTLE.asStack()).rightClick();
		scene.overlay.showControls(rightClick, 50);
		scene.idle(6);
		BlockState extension = AllBlocks.STEAM_WHISTLE_EXTENSION.getDefaultState();
		scene.world.setBlock(whistlePos.above(), extension, false);
		scene.idle(20);
		scene.overlay.showText(70).attachKeyFrame().text("Use a Whistle item on the block to lower its pitch").pointAt(util.vector.blockSurface(util.grid.at(2, 3, 2), Direction.NORTH)).placeNearTarget();
		scene.idle(40);
		scene.overlay.showControls(rightClick, 2);
		scene.idle(6);
		scene.world.cycleBlockProperty(whistlePos.above(), WhistleExtenderBlock.SHAPE);
		scene.idle(4);
		scene.overlay.showControls(rightClick, 2);
		scene.idle(6);
		scene.world.setBlock(whistlePos.above(2), extension, false);
		scene.world.cycleBlockProperty(whistlePos.above(), WhistleExtenderBlock.SHAPE);
		scene.idle(4);
		scene.overlay.showControls(rightClick, 2);
		scene.idle(6);
		scene.world.cycleBlockProperty(whistlePos.above(2), WhistleExtenderBlock.SHAPE);
		scene.idle(4);
		scene.overlay.showControls(rightClick, 2);
		scene.idle(6);
		scene.world.cycleBlockProperty(whistlePos.above(2), WhistleExtenderBlock.SHAPE);
		scene.world.setBlock(whistlePos.above(3), extension, false);
		scene.idle(20);
		rightClick = (new InputWindowElement(util.vector.blockSurface(util.grid.at(2, 3, 1), Direction.EAST), Pointing.RIGHT)).withWrench().rightClick();
		scene.overlay.showControls(rightClick, 50);
		scene.idle(6);

		int i;
		for(i = 0; i < 4; ++i) {
			scene.world.cycleBlockProperty(whistlePos.above(i), WhistleBlock.SIZE);
			scene.idle(1);
		}

		scene.idle(20);
		scene.overlay.showText(70).attachKeyFrame().text("Cycle between three different octaves using a Wrench").pointAt(util.vector.blockSurface(util.grid.at(2, 3, 2), Direction.NORTH)).placeNearTarget();
		scene.idle(40);
		scene.overlay.showControls(rightClick, 4);
		scene.idle(6);

		for(i = 0; i < 4; ++i) {
			scene.world.cycleBlockProperty(whistlePos.above(i), WhistleBlock.SIZE);
			scene.idle(1);
		}

		scene.idle(20);
		scene.world.toggleRedstonePower(whistleArea);
		scene.world.toggleRedstonePower(lever);
		scene.effects.indicateRedstone(leverPos);
		scene.idle(20);
		scene.world.toggleRedstonePower(whistleArea);
		scene.world.toggleRedstonePower(lever);
		scene.idle(20);
		// EDIT: remove engineer goggles
//		scene.overlay.showControls((new InputWindowElement(util.vector.blockSurface(util.grid.at(2, 3, 1), Direction.DOWN), Pointing.UP)).withItem(AllItems.GOGGLES.asStack()), 80);
//		scene.idle(6);
//		scene.overlay.showText(70).attachKeyFrame().colored(PonderPalette.BLUE).text("Engineer's Goggles can help to find out the current pitch of a Whistle").pointAt(util.vector.blockSurface(util.grid.at(2, 3, 2), Direction.NORTH)).placeNearTarget();
//		scene.idle(40);

		ci.cancel();
	}

}
