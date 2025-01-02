package me.viniciusroger.unsuckpvp.mixin.impl;

import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;

@Mixin(Minecraft.class)
public class MixinMinecraft {
	@Shadow
	private int leftClickCounter;
	
	@Shadow
	private EntityPlayerSP thePlayer;
	
	@Shadow
	public MovingObjectPosition objectMouseOver;
	
	@Shadow
	public PlayerControllerMP playerController;
	
	@Shadow
	public WorldClient theWorld;
	
	@Shadow
	@Final
	private static Logger logger;
	
	/*
	 * @author real
	 * @reason real
	 */
	@Inject(method = "clickMouse", at = @At("HEAD"), cancellable = true)
	private void clickMouse(CallbackInfo ci) {
		this.leftClickCounter = 0;
		this.thePlayer.swingItem();

		if (this.objectMouseOver != null) {
			switch (this.objectMouseOver.typeOfHit) {
				case ENTITY:
					this.playerController.attackEntity(this.thePlayer, this.objectMouseOver.entityHit);
					break;
				case BLOCK:
					BlockPos blockpos = this.objectMouseOver.getBlockPos();

					if (this.theWorld.getBlockState(blockpos).getBlock().getMaterial() != Material.air) {
                            this.playerController.clickBlock(blockpos, this.objectMouseOver.sideHit);
                            break;
					}
				default:
					break;
			}
        }
		
		ci.cancel();
    }
}
