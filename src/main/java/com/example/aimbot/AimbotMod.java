package com.example.aimbot;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AimbotMod implements ModInitializer {
    public static final String MOD_ID = "aimbotmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static boolean aimbotEnabled = false;
    
    @Override
    public void onInitialize() {
        LOGGER.info("[AimbotMod] Initializing for Minecraft 1.21.8");
        
        KeyBindings.register();
        FriendManager.loadFriends();
        
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.world == null) return;
            
            if (KeyBindings.aimbotKey.wasPressed()) {
                aimbotEnabled = !aimbotEnabled;
                LOGGER.info("Aimbot: " + (aimbotEnabled ? "ON" : "OFF"));
            }
            
            if (KeyBindings.addFriendKey.wasPressed()) {
                if (client.targetedEntity instanceof net.minecraft.entity.player.PlayerEntity target) {
                    FriendManager.addFriend(target.getUuid());
                    LOGGER.info("Added friend: " + target.getName().getString());
                }
            }
            
            if (aimbotEnabled) {
                updateAimbot(client);
            }
        });
    }
    
    private void updateAimbot(MinecraftClient client) {
        var target = TargetFinder.findBestTarget(client, 50.0, 90.0);
        if (target == null) return;
        
        var angles = AimCalculator.calculateAngles(client, target);
        if (angles == null) return;
        
        AimCalculator.smoothAim(client, angles[0], angles[1], 0.3f);
        AimCalculator.autoAttack(client, target);
    }
    
    public static boolean isAimbotEnabled() { return aimbotEnabled; }
}
