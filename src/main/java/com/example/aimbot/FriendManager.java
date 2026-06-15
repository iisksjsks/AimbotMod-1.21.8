package com.example.aimbot;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import java.io.*;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FriendManager {
    private static final Set<UUID> friendList = new HashSet<>();
    private static final Path CONFIG_PATH = Paths.get("config/aimbotmod_friends.json");
    
    public static void addFriend(UUID uuid) {
        friendList.add(uuid);
        saveFriends();
    }
    
    public static void removeFriend(UUID uuid) {
        friendList.remove(uuid);
        saveFriends();
    }
    
    public static boolean isFriend(Entity entity) {
        if (entity instanceof PlayerEntity player) {
            return friendList.contains(player.getUuid());
        }
        return false;
    }
    
    public static Set<UUID> getFriendList() {
        return new HashSet<>(friendList);
    }
    
    public static void saveFriends() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (BufferedWriter writer = Files.newBufferedWriter(CONFIG_PATH)) {
                for (UUID uuid : friendList) {
                    writer.write(uuid.toString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            AimbotMod.LOGGER.error("Failed to save friends: " + e.getMessage());
        }
    }
    
    public static void loadFriends() {
        if (!Files.exists(CONFIG_PATH)) return;
        try (BufferedReader reader = Files.newBufferedReader(CONFIG_PATH)) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    friendList.add(UUID.fromString(line));
                } catch (IllegalArgumentException ignored) {}
            }
        } catch (IOException e) {
            AimbotMod.LOGGER.error("Failed to load friends: " + e.getMessage());
        }
    }
}
