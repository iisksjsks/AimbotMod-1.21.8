package com.example.aimbot;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class TargetFinder {
    
    public static Entity findBestTarget(MinecraftClient client, double maxDistance, double maxAngle) {
        Entity bestTarget = null;
        double bestScore = Double.MAX_VALUE;
        
        if (client.player == null || client.world == null) return null;
        
        for (Entity entity : client.world.getEntities()) {
            if (entity == client.player) continue;
            if (FriendManager.isFriend(entity)) continue;
            if (!(entity instanceof LivingEntity living)) continue;
            if (living.isDead()) continue;
            if (living.getHealth() <= 0) continue;
            
            double distance = client.player.distanceTo(entity);
            if (distance > maxDistance) continue;
            
            Vec3d lookVec = client.player.getRotationVec(1.0F);
            Vec3d toTarget = entity.getPos().subtract(client.player.getPos()).normalize();
            double angle = Math.acos(lookVec.dotProduct(toTarget)) * (180 / Math.PI);
            if (angle > maxAngle) continue;
            
            double score = distance * 0.5 + angle * 1.5;
            if (score < bestScore) {
                bestScore = score;
                bestTarget = entity;
            }
        }
        return bestTarget;
    }
}
