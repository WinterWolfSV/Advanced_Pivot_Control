package winterwolfsv.advancedpivotcontrol.client;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigManager;
import net.minecraft.text.Text;
import winterwolfsv.config.Config;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import org.lwjgl.glfw.GLFW;

import java.util.concurrent.atomic.AtomicBoolean;

import static winterwolfsv.advancedpivotcontrol.client.Commands.sendCommandFeedback;

@Environment(EnvType.CLIENT)
public class AdvancedPivotControlClient implements ClientModInitializer {
    public static ConfigManager configManager;
    public static final String MOD_ID = "advanced_pivot_control";
    public static float currentYaw;
    public static float currentPitch;

    private static final KeyBinding yawRight = KeyBindingHelper.registerKeyBinding(new KeyBinding("Turn right", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT, "Pitch and Yaw Control"));
    private static final KeyBinding yawLeft = KeyBindingHelper.registerKeyBinding(new KeyBinding("Turn left", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT, "Pitch and Yaw Control"));
    private static final KeyBinding pitchUp = KeyBindingHelper.registerKeyBinding(new KeyBinding("Look up", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UP, "Pitch and Yaw Control"));
    private static final KeyBinding pitchDown = KeyBindingHelper.registerKeyBinding(new KeyBinding("Look down", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_DOWN, "Pitch and Yaw Control"));
    private static final KeyBinding lockYaw = KeyBindingHelper.registerKeyBinding(new KeyBinding("Lock yaw", InputUtil.Type.KEYSYM, -1, "Pitch and Yaw Control"));
    private static final KeyBinding lockPitch = KeyBindingHelper.registerKeyBinding(new KeyBinding("Lock pitch", InputUtil.Type.KEYSYM, -1, "Pitch and Yaw Control"));

    private static int turnYaw(int direction, int degrees) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        assert player != null;
        currentYaw = roundToClosestValue(direction, degrees, player.getYaw()) % 360;
        if (currentYaw > 180) currentYaw -= 360;
        else if (currentYaw < -180) currentYaw += 360;
        player.setYaw(currentYaw);
        return (int) currentYaw;
    }

    private static float roundToClosestValue(int direction, int degrees, float value) {
        if (value % degrees == 0) return value + direction * degrees;
        if (direction < 0) return (int) Math.floor(value / degrees) * degrees;
        if (direction > 0) return (int) Math.ceil(value / degrees) * degrees;
        return value;
    }

    private static int turnPitch(int direction, int degrees) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        assert player != null;
        currentPitch = roundToClosestValue(direction, degrees, player.getPitch());
        player.setPitch(currentPitch);
        return (int) Math.min(90, Math.max(-90, currentPitch));
    }

    @Override
    public void onInitializeClient() {
        configManager = (ConfigManager) AutoConfig.register(Config.class, GsonConfigSerializer::new);
        Config config = AutoConfig.getConfigHolder(Config.class).getConfig();
        lockView();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (yawRight.wasPressed()) {
                sendCommandFeedback(Text.translatable(MOD_ID + ".keybinding.turn_right", config.yawSteps, turnYaw(1, config.yawSteps)));
            }
            while (yawLeft.wasPressed()) {
                sendCommandFeedback(Text.translatable(MOD_ID + ".keybinding.turn_left", config.yawSteps, turnYaw(-1, config.yawSteps)));
            }
            while (pitchUp.wasPressed()) {
                sendCommandFeedback(Text.translatable(MOD_ID + ".keybinding.turn_up", config.pitchSteps, turnPitch(-1, config.pitchSteps)));
            }
            while (pitchDown.wasPressed()) {
                sendCommandFeedback(Text.translatable(MOD_ID + ".keybinding.turn_down", config.pitchSteps, turnPitch(1, config.pitchSteps)));
            }
            while (lockYaw.wasPressed()) {
                config.lockYaw = !config.lockYaw;
                configManager.save();
                sendCommandFeedback(Text.translatable(MOD_ID + ".keybinding.yaw_lock", config.lockYaw ? "enabled" : "disabled"));
            }
            while (lockPitch.wasPressed()) {
                config.lockPitch = !config.lockPitch;
                configManager.save();
                sendCommandFeedback(Text.translatable(MOD_ID + ".keybinding.pitch_lock", config.lockPitch ? "enabled" : "disabled"));
            }
        });
    }

    private void lockView() {
        Config config = AutoConfig.getConfigHolder(Config.class).getConfig();
        AtomicBoolean oldLockYaw = new AtomicBoolean(config.lockYaw);
        AtomicBoolean oldLockPitch = new AtomicBoolean(config.lockPitch);
        ClientTickEvents.END_WORLD_TICK.register(client -> {
            PlayerEntity player = MinecraftClient.getInstance().player;
            if (oldLockYaw.get() != config.lockYaw || oldLockPitch.get() != config.lockPitch) {
                if (player == null) return;
                currentYaw = player.getYaw();
                currentPitch = player.getPitch();
            }
            if (config.lockYaw && player != null && player.getYaw() != currentYaw) {
                player.setYaw(currentYaw);
            }
            if (config.lockPitch && player != null && player.getPitch() != currentPitch) {
                player.setPitch(currentPitch);
            }
            oldLockPitch.set(config.lockPitch);
            oldLockYaw.set(config.lockYaw);
        });
    }
}

// TODO
// - Implement 4040 trick