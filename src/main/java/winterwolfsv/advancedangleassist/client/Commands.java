package winterwolfsv.advancedangleassist.client;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import winterwolfsv.config.Config;

public class Commands {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("angleassist")
                .then(CommandManager.literal("pitch")
                        .then(CommandManager.argument("pitch", IntegerArgumentType.integer(-90, 90))
                                .executes(context -> commandSetPitch(IntegerArgumentType.getInteger(context, "pitch")))))
                .then(CommandManager.literal("yaw")
                        .then(CommandManager.argument("yaw", IntegerArgumentType.integer(-180, 180))
                                .executes(context -> commandSetYaw(IntegerArgumentType.getInteger(context, "yaw")))))
                .then(CommandManager.literal("angle")
                        .then(CommandManager.argument("pitch", IntegerArgumentType.integer(-90, 90))
                                .then(CommandManager.argument("yaw", IntegerArgumentType.integer(-180, 180))
                                        .executes(context -> commandSetAngle(IntegerArgumentType.getInteger(context, "pitch"), IntegerArgumentType.getInteger(context, "yaw"))))))
                .then(CommandManager.literal("config")
                        .then(CommandManager.literal("setPitchSteps")
                                .then(CommandManager.argument("pitch", IntegerArgumentType.integer(1, 90))
                                        .executes(context -> commandSetPitchSteps(IntegerArgumentType.getInteger(context, "pitch")))))
                        .then(CommandManager.literal("setYawSteps")
                                .then(CommandManager.argument("yaw", IntegerArgumentType.integer(1, 180))
                                        .executes(context -> commandSetYawSteps(IntegerArgumentType.getInteger(context, "yaw")))))
                        .then(CommandManager.literal("doCommandFeedback")
                                .then(CommandManager.argument("doCommandFeedback", BoolArgumentType.bool())
                                        .executes(context -> commandDoCommandFeedback(BoolArgumentType.getBool(context, "doCommandFeedback"))))))));

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("pitch")
                .then(CommandManager.argument("pitch", IntegerArgumentType.integer(-90, 90))
                        .executes(context -> commandSetPitch(IntegerArgumentType.getInteger(context, "pitch"))))));

        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("yaw")
                .then(CommandManager.argument("yaw", IntegerArgumentType.integer(-180, 180))
                        .executes(context -> commandSetYaw(IntegerArgumentType.getInteger(context, "yaw")))))));

        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("setangle")
                .then(CommandManager.argument("yaw", IntegerArgumentType.integer(-180, 180))
                        .then(CommandManager.argument("pitch", IntegerArgumentType.integer(-90, 90))
                                .executes(context -> commandSetAngle(IntegerArgumentType.getInteger(context, "pitch"), IntegerArgumentType.getInteger(context, "yaw"))))))));
    }

    private static int commandDoCommandFeedback(boolean doCommandFeedback) {
        AutoConfig.getConfigHolder(Config.class).getConfig().messageFeedback = doCommandFeedback;
        AutoConfig.getConfigHolder(Config.class).save();
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            player.sendMessage(Text.literal("Command feedback set to " + doCommandFeedback), true);
        } else {
            System.out.println("Command feedback set to " + doCommandFeedback);
        }
        return 1;
    }

    private static int commandSetPitch(int value) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            player.setPitch(value);
            sendCommandFeedback("Pitch set to " + value);
            return 1;
        } else {
            System.out.println("Player is null.");
            return 0;
        }
    }

    private static int commandSetPitchSteps(int value) {
        AutoConfig.getConfigHolder(Config.class).getConfig().pitchSteps = value;
        AutoConfig.getConfigHolder(Config.class).save();
        sendCommandFeedback("Pitch steps set to " + value);
        return 1;
    }

    private static int commandSetYaw(int value) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            player.setYaw(value);
            sendCommandFeedback("Yaw set to " + value);
            return 1;
        } else {
            System.out.println("Player is null.");
            return 0;
        }
    }

    private static int commandSetYawSteps(int value) {
        AutoConfig.getConfigHolder(Config.class).getConfig().yawSteps = value;
        AutoConfig.getConfigHolder(Config.class).save();
        sendCommandFeedback("Yaw steps set to " + value);
        return 1;
    }

    private static int commandSetAngle(int pitch, int yaw) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            player.setPitch(pitch);
            player.setYaw(yaw);
            sendCommandFeedback("Yaw set to " + yaw + " and pitch set to " + pitch);
            return 1;
        } else {
            System.out.println("Player is null.");
            return 0;
        }
    }

    public static void sendCommandFeedback(String message) {
        if (AutoConfig.getConfigHolder(Config.class).getConfig().messageFeedback) {
            PlayerEntity player = MinecraftClient.getInstance().player;
            if (player != null) {
                player.sendMessage(Text.literal(message), true);
            } else {
                System.out.println(message);
            }
        }
    }


}