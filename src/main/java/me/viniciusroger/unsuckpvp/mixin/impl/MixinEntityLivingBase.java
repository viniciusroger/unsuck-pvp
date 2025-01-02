package me.viniciusroger.unsuckpvp.mixin.impl;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends Entity {
	
	public MixinEntityLivingBase(World worldIn) {
		super(worldIn);
	}

	@Overwrite
	public Vec3 getLook(float partialTicks) {
		return this.getVectorForRotation(this.rotationPitch, this.rotationYaw);
	}
}
