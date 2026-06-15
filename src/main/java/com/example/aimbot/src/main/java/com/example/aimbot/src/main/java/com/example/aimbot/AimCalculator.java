package com.example.aimbot;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class AimCalculator {
    
    public static float[] calculateAngles(MinecraftClient client, Entity target) {
        if (client.player == null) return null;
        
        Vec3d targetPos = target.getBoundingBox().getCenter();
        Vec3d playerPos = client.player.getEyePos();
        Vec3d delta = targetPos.subtract(playerPos);
        
        double distance = delta.horizontalLength();
        float yaw = (float) (Math.atan2(delta.z, delta.x) * (180 / Math.PI)) - 90;
        float pitch = (float) (-Math.atan2(delta.y, distance) * (180 / Math.PI));
        
        return new float[]{yaw, pitch};
    }
    
    public static void smoothAim(MinecraftClient client, float targetYaw, float targetPitch, float smoothFactor) {
        if (client.player == null) return;
        
        float currentYaw = client.player.getYaw();
        float currentPitch = client.player.getPitch();
        
        float newYaw = currentYaw + (targetYaw - currentYaw) * smoothFactor;
        float newPitch = currentPitch + (targetPitch - currentPitch) * smoothFactor;
        newPitch = MathHelper.clamp(newPitch, -90, 90);
        
        client.player.setYaw(newYaw);
        client.player.setPitch(newPitch);
    }
    
    public static void autoAttack(MinecraftClient client, Entity target) {
        if (client.player == null) return;
        double distance = client.player.distanceTo(target);
        client.options.attackKey.setPressed(distance < 4.0);
    }
}
