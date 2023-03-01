package winterwolfsv.pitch_and_yaw_control.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class Pitch_And_Yaw_ControlClient implements ClientModInitializer {
    private static KeyBinding yawRight = KeyBindingHelper.registerKeyBinding(new KeyBinding("Turn right", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT, "Pitch and Yaw Control"));
    private static KeyBinding yawLeft = KeyBindingHelper.registerKeyBinding(new KeyBinding("Turn left", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT, "Pitch and Yaw Control"));
    private static KeyBinding pitchUp = KeyBindingHelper.registerKeyBinding(new KeyBinding("Look up", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UP, "Pitch and Yaw Control"));
    private static KeyBinding pitchDown = KeyBindingHelper.registerKeyBinding(new KeyBinding("Look down", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_DOWN, "Pitch and Yaw Control"));

    private static void turnYaw(int direction, int degrees) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        assert player != null;
        if (player.getYaw() % degrees != 0) {
            if (direction < 0) {
                player.setYaw(player.getYaw() - player.getYaw() % degrees);
            } else {
                player.setYaw(player.getYaw() + degrees - player.getYaw() % degrees);
            }
        } else {
            player.setYaw(player.getYaw() + direction * degrees);
        }
    }

    private static void turnPitch(int direction, int degrees) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        assert player != null;
        if (player.getPitch() % degrees != 0) {
            if (direction < 0) {
                player.setPitch(player.getPitch() - player.getPitch() % degrees);
            } else {
                player.setPitch(player.getPitch() + degrees - player.getPitch() % degrees);
            }
        } else {
            player.setPitch(player.getPitch() + direction * degrees);
        }
    }

    private static void turnRight() {
        PlayerEntity player = MinecraftClient.getInstance().player;
        assert player != null;
        System.out.println(player.getYaw());
        System.out.println(player.getPitch());
        player.setPitch(0);
        player.setYaw(0);
    }

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            int degrees = 10;
            while (yawRight.wasPressed()) {
                client.player.sendMessage(Text.literal("Rotating yaw to your right"), false);
                turnYaw(1, degrees);
            }
            while (yawLeft.wasPressed()) {
                client.player.sendMessage(Text.literal("Rotating yaw to your left"), false);
                turnYaw(-1, degrees);
            }
            while (pitchUp.wasPressed()) {
                client.player.sendMessage(Text.literal("Rotating pitch up"), false);
                turnPitch(-1, degrees);
            }
            while (pitchDown.wasPressed()) {
                client.player.sendMessage(Text.literal("Rotating pitch down"), false);
                turnPitch(1, degrees);
            }
        });
    }
}
