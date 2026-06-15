package com.example.aimbot;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    public static KeyBinding aimbotKey;
    public static KeyBinding addFriendKey;
    
    public static void register() {
        aimbotKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.aimbot.toggle",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            "category.aimbot"
        ));
        
        addFriendKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.aimbot.addfriend",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_F,
            "category.aimbot"
        ));
    }
}
