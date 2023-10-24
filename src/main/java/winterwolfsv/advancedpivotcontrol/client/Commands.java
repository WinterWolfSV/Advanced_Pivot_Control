package winterwolfsv.advancedpivotcontrol.client;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import winterwolfsv.config.Config;

import static winterwolfsv.advancedpivotcontrol.client.AdvancedPivotControlClient.MOD_ID;

public class Commands {

    public static void register() {
        final String[] nameList = new String[]{"advancedpivotcontrol", "apc"};

        for (int i = 0; i < nameList.length; i++) {
            int finalI = i;

            ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal(nameList[finalI])
                    .then(ClientCommandManager.literal("pitch")
                            .then(ClientCommandManager.argument("pitch", DoubleArgumentType.doubleArg(-90, 90))
                                    .executes(context -> commandSetPitch((float) DoubleArgumentType.getDouble(context, "pitch")))))

                    .then(ClientCommandManager.literal("yaw")
                            .then(ClientCommandManager.argument("yaw", DoubleArgumentType.doubleArg(-180, 180))
                                    .executes(context -> commandSetYaw((float) DoubleArgumentType.getDouble(context, "yaw")))))


                    .then(ClientCommandManager.literal("angle")
                            .then(ClientCommandManager.argument("pitch", DoubleArgumentType.doubleArg(-90, 90))
                                    .then(ClientCommandManager.argument("yaw", DoubleArgumentType.doubleArg(-180, 180))
                                            .executes(context -> commandSetAngle((float) DoubleArgumentType.getDouble(context, "pitch"), (float) DoubleArgumentType.getDouble(context, "yaw"))))))
                    .then(ClientCommandManager.literal("config")
                            .then(ClientCommandManager.literal("setpitchsteps")
                                    .then(ClientCommandManager.argument("pitch", DoubleArgumentType.doubleArg(1, 90))
                                            .executes(context -> commandSetPitchSteps((int) DoubleArgumentType.getDouble(context, "pitch"))))
                                    .executes(context -> {
                                        context.getSource().sendFeedback(Text.of("Pitch Steps is set to: " + AutoConfig.getConfigHolder(Config.class).getConfig().pitchSteps));
                                        return 1;
                                    }))
                            .then(ClientCommandManager.literal("setyawsteps")
                                    .then(ClientCommandManager.argument("yaw", DoubleArgumentType.doubleArg(1, 180))
                                            .executes(context -> commandSetYawSteps((int) DoubleArgumentType.getDouble(context, "yaw"))))
                                    .executes(context -> {
                                        context.getSource().sendFeedback(Text.of("Yaw Steps is set to: " + AutoConfig.getConfigHolder(Config.class).getConfig().yawSteps));
                                        return 1;
                                    }))

                            .then(ClientCommandManager.literal("docommandfeedback")
                                    .then(ClientCommandManager.argument("doCommandFeedback", BoolArgumentType.bool())
                                            .executes(context -> commandDoCommandFeedback(BoolArgumentType.getBool(context, "doCommandFeedback"))))
                                    .executes(context -> {
                                        context.getSource().sendFeedback(Text.of("Do Command Feedback is set to: " + AutoConfig.getConfigHolder(Config.class).getConfig().messageFeedback));
                                        return 1;
                                    }))
                            .then(ClientCommandManager.literal("lockyaw")
                                    .then(ClientCommandManager.argument("lockYaw", BoolArgumentType.bool())
                                            .executes(context -> commandLockYaw(BoolArgumentType.getBool(context, "lockYaw"))))
                                    .executes(context -> {
                                        context.getSource().sendFeedback(Text.of("Yaw Lock is set to: " + AutoConfig.getConfigHolder(Config.class).getConfig().lockYaw));
                                        return 1;
                                    }))
                            .then(ClientCommandManager.literal("lockPitch")
                                    .then(ClientCommandManager.argument("lockPitch", BoolArgumentType.bool())
                                            .executes(context -> commandLockPitch(BoolArgumentType.getBool(context, "lockPitch"))))
                                    .executes(context -> {
                                        context.getSource().sendFeedback(Text.of("Pitch Lock is set to: " + AutoConfig.getConfigHolder(Config.class).getConfig().lockPitch));
                                        return 1;
                                    })))));


            ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("pitch")
                    .then(ClientCommandManager.argument("pitch", DoubleArgumentType.doubleArg(-90, 90))
                            .executes(context -> commandSetPitch((float) DoubleArgumentType.getDouble(context, "pitch"))))));

            ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("yaw")
                    .then(ClientCommandManager.argument("yaw", DoubleArgumentType.doubleArg(-180, 180))
                            .executes(context -> commandSetYaw((float) DoubleArgumentType.getDouble(context, "yaw")))))));

            ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("lockyaw")
                    .then(ClientCommandManager.argument("lockYaw", BoolArgumentType.bool())
                            .executes(context -> commandLockYaw(BoolArgumentType.getBool(context, "lockYaw"))))
                    .executes(context -> {
                        context.getSource().sendFeedback(Text.of("Lock Yaw is set to: " + AutoConfig.getConfigHolder(Config.class).getConfig().lockYaw));
                        return 1;
                    }))));

            ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("lockpitch")
                    .then(ClientCommandManager.argument("lockPitch", BoolArgumentType.bool())
                            .executes(context -> commandLockPitch(BoolArgumentType.getBool(context, "lockPitch"))))
                    .executes(context -> {
                        context.getSource().sendFeedback(Text.of("Lock Pitch is set to: " + AutoConfig.getConfigHolder(Config.class).getConfig().lockPitch));
                        return 1;
                    }))));

            ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("lockangle")
                    .then(ClientCommandManager.argument("lockAngle", BoolArgumentType.bool())
                            .executes(context -> commandLockAngle(BoolArgumentType.getBool(context, "lockAngle"))))
                    .executes(context -> {
                        boolean lockAngle = AutoConfig.getConfigHolder(Config.class).getConfig().lockYaw && AutoConfig.getConfigHolder(Config.class).getConfig().lockPitch;
                        context.getSource().sendFeedback(Text.of("Lock Angle is set to: " + lockAngle));
                        return 1;
                    }))));

            ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("angle")
                    .then(ClientCommandManager.argument("yaw", DoubleArgumentType.doubleArg(-180, 180))
                            .then(ClientCommandManager.argument("pitch", DoubleArgumentType.doubleArg(-90, 90))
                                    .executes(context -> commandSetAngle((float) DoubleArgumentType.getDouble(context, "pitch"), (float) DoubleArgumentType.getDouble(context, "yaw"))))))));

        }
    }

    private static int commandDoCommandFeedback(boolean doCommandFeedback) {
        AutoConfig.getConfigHolder(Config.class).getConfig().messageFeedback = doCommandFeedback;
        AutoConfig.getConfigHolder(Config.class).save();
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            player.sendMessage(Text.translatable(MOD_ID + ".command.doCommandFeedback", doCommandFeedback), true);
        } else {
            System.out.println(Text.translatable(MOD_ID + ".command.doCommandFeedback", doCommandFeedback));
        }
        return 1;
    }

    private static int commandSetPitch(float value) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) {
            System.out.println(Text.translatable(MOD_ID + ".command.player_null"));
            return 0;
        }
        player.setPitch(value);
        AdvancedPivotControlClient.currentPitch = value;
        sendCommandFeedback(Text.translatable(MOD_ID + ".command.pitch_set", value));
        return 1;

    }

    private static int commandSetPitchSteps(int value) {
        AutoConfig.getConfigHolder(Config.class).getConfig().pitchSteps = value;
        AutoConfig.getConfigHolder(Config.class).save();
        sendCommandFeedback(Text.translatable(MOD_ID + ".command.pitch_steps_set", value));
        return 1;
    }

    private static int commandSetYaw(float value) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) {
            System.out.println(Text.translatable(MOD_ID + ".command.player_null"));
            return 0;
        }
        player.setYaw(value);
        AdvancedPivotControlClient.currentYaw = value;
        sendCommandFeedback(Text.translatable(MOD_ID + ".command.yaw_set", value));
        return 1;

    }

    private static int commandSetYawSteps(int value) {
        AutoConfig.getConfigHolder(Config.class).getConfig().yawSteps = value;
        AutoConfig.getConfigHolder(Config.class).save();
        sendCommandFeedback(Text.translatable(MOD_ID + ".command.yaw_steps_set", value));
        return 1;
    }

    private static int commandSetAngle(float pitch, float yaw) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) {
            System.out.println(Text.translatable(MOD_ID + ".command.player_null"));
            return 0;
        }
        player.setPitch(pitch);
        player.setYaw(yaw);
        AdvancedPivotControlClient.currentPitch = pitch;
        AdvancedPivotControlClient.currentYaw = yaw;
        sendCommandFeedback(Text.translatable(MOD_ID + ".command.angle_set", pitch, yaw));
        return 1;

    }

    private static int commandLockYaw(boolean lockYaw) {
        AutoConfig.getConfigHolder(Config.class).getConfig().lockYaw = lockYaw;
        AutoConfig.getConfigHolder(Config.class).save();
        sendCommandFeedback(Text.translatable(MOD_ID + ".command.yaw_lock", AutoConfig.getConfigHolder(Config.class).getConfig().lockYaw));
        return 1;
    }

    private static int commandLockPitch(boolean lockPitch) {
        AutoConfig.getConfigHolder(Config.class).getConfig().lockPitch = lockPitch;
        AutoConfig.getConfigHolder(Config.class).save();
        sendCommandFeedback(Text.translatable(MOD_ID + ".command.pitch_lock", AutoConfig.getConfigHolder(Config.class).getConfig().lockPitch));
        return 1;
    }

    private static int commandLockAngle(boolean lockAngle) {
        AutoConfig.getConfigHolder(Config.class).getConfig().lockYaw = lockAngle;
        AutoConfig.getConfigHolder(Config.class).getConfig().lockPitch = lockAngle;
        AutoConfig.getConfigHolder(Config.class).save();
        sendCommandFeedback(Text.translatable(MOD_ID + ".command.angle_lock", lockAngle));
        return 1;
    }

    public static void sendCommandFeedback(String message) {
        if (!AutoConfig.getConfigHolder(Config.class).getConfig().messageFeedback) return;
        PlayerEntity player = MinecraftClient.getInstance().player;

        if (player == null) {
            System.out.println(message);
            return;
        }
        player.sendMessage(Text.literal(message), true);

    }

    public static void sendCommandFeedback(Text message) {
        if (!AutoConfig.getConfigHolder(Config.class).getConfig().messageFeedback) return;
        PlayerEntity player = MinecraftClient.getInstance().player;

        if (player == null) {
            System.out.println(message);
            return;
        }
        player.sendMessage(message, true);

    }
}
