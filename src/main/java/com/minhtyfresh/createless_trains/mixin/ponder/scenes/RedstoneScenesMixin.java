package com.minhtyfresh.createless_trains.mixin.ponder.scenes;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.redstone.nixieTube.NixieTubeBlock;
import com.simibubi.create.content.redstone.nixieTube.NixieTubeBlockEntity;
import com.simibubi.create.foundation.ponder.PonderPalette;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.Selection;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.ponder.element.ParrotElement;
import com.simibubi.create.foundation.utility.Components;
import com.simibubi.create.foundation.utility.Pointing;
import com.simibubi.create.infrastructure.ponder.scenes.RedstoneScenes;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RedstoneScenes.class)
public class RedstoneScenesMixin {
	@Inject(
			method = "nixieTube",
			at = @At("HEAD"),
			cancellable = true,
			remap = false
	)
	private static void nixieTube(SceneBuilder scene, SceneBuildingUtil util, CallbackInfo ci) {
		scene.title("nixie_tube", "Using Nixie Tubes");
		scene.configureBasePlate(0, 0, 5);
		scene.world.showSection(util.select.layer(0).add(util.select.fromTo(2, 1, 1, 2, 1, 2)), Direction.UP);
		scene.idle(10);
		scene.world.showSection(util.select.position(2, 1, 3), Direction.DOWN);
		scene.idle(20);
		Selection tubes = util.select.fromTo(3, 1, 3, 1, 1, 3);
		scene.effects.indicateRedstone(util.grid.at(2, 1, 1));
		// EDIT: replace analog lever with a regular lever
//		scene.world.modifyBlockEntityNBT(util.select.position(2, 1, 1), AnalogLeverBlockEntity.class, (nbt) -> {
//			nbt.putInt("State", 15);
//		});
		scene.world.modifyBlock(util.grid.at(2, 1, 2), (s) -> {
			return (BlockState)s.setValue(RedStoneWireBlock.POWER, 15);
		}, false);
		scene.world.toggleRedstonePower(util.select.position(2,1,1));
		scene.world.modifyBlockEntityNBT(tubes, NixieTubeBlockEntity.class, (nbt) -> {
			nbt.putInt("RedstoneStrength", 15);
		});
		scene.idle(20);
		Vec3 centerTube = util.vector.centerOf(2, 1, 3);
		scene.overlay.showText(60).attachKeyFrame().text("When powered by Redstone, Nixie Tubes will display the signal strength").placeNearTarget().pointAt(util.vector.blockSurface(util.grid.at(2, 1, 3), Direction.WEST));
		scene.idle(70);
		scene.world.hideSection(util.select.position(2, 1, 3), Direction.UP);
		scene.idle(5);
		scene.world.hideSection(util.select.fromTo(2, 1, 1, 2, 1, 2), Direction.NORTH);
		scene.idle(10);
		scene.world.modifyBlockEntityNBT(tubes, NixieTubeBlockEntity.class, (nbt) -> {
			nbt.putInt("RedstoneStrength", 0);
		});
		scene.world.showSection(tubes, Direction.DOWN);
		scene.idle(20);
		// EDIT: replace clipboard with nametag
//		ItemStack clipboard = AllBlocks.CLIPBOARD.asStack();
//		ClipboardOverrides.switchTo(ClipboardOverrides.ClipboardType.WRITTEN, clipboard);
		ItemStack nameTag = new ItemStack(Items.NAME_TAG);
		scene.overlay.showControls((new InputWindowElement(centerTube.add(1.0, 0.35, 0.0), Pointing.DOWN)).rightClick().withItem(nameTag), 40);
		scene.idle(7);
		Component component = Components.literal("CREATE");

		for(int i = 0; i < 3; ++i) {
			int index = i;
			scene.world.modifyBlockEntityNBT(util.select.position(3 - i, 1, 3), NixieTubeBlockEntity.class, (nbt) -> {
				nbt.putString("RawCustomText", component.getString());
				nbt.putString("CustomText", Component.Serializer.toJson(component));
				nbt.putInt("CustomTextIndex", index);
			});
		}

		scene.idle(10);
		scene.world.showSection(util.select.position(4, 1, 3), Direction.DOWN);
		scene.idle(10);
		scene.special.createBirb(util.vector.topOf(util.grid.at(0, 0, 3)), ParrotElement.DancePose::new);
		scene.idle(20);
		scene.overlay.showText(80).attachKeyFrame().placeNearTarget().text("Using Name Tags, custom text can be displayed").pointAt(util.vector.topOf(util.grid.at(3, 1, 3)).add(-0.75, -0.05000000074505806, 0.0));
		scene.idle(90);
		InputWindowElement input = (new InputWindowElement(util.vector.blockSurface(util.grid.at(3, 1, 3), Direction.UP), Pointing.DOWN)).withItem(new ItemStack(Items.BLUE_DYE));
		scene.overlay.showControls(input, 30);
		scene.idle(7);
		scene.world.setBlocks(util.select.fromTo(1, 1, 3, 3, 1, 3), (BlockState)AllBlocks.NIXIE_TUBES.get(DyeColor.BLUE).getDefaultState().setValue(NixieTubeBlock.FACING, Direction.WEST), false);
		scene.idle(10);
		scene.overlay.showText(80).colored(PonderPalette.BLUE).text("Right-Click with Dye to change their display colour").attachKeyFrame().pointAt(util.vector.topOf(util.grid.at(3, 1, 3)).add(-0.75, -0.05000000074505806, 0.0)).placeNearTarget();
		scene.idle(60);

		ci.cancel();
	}

}
