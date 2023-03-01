package winterwolfsv.pitch_and_yaw_control;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;

import static com.mojang.brigadier.builder.RequiredArgumentBuilder.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class Pitch_And_Yaw_Control implements ModInitializer {
    @Override
    public void onInitialize() {
        PlayerEntity player = MinecraftClient.getInstance().player;
        assert player != null;
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("pitch")
                    .then(CommandManager.argument("angle", IntegerArgumentType.integer(-90, 90))
                            .executes(context -> {
                                int angle = IntegerArgumentType.getInteger(context, "angle");
                                player.setPitch(angle);
                                return 1;
                            })));
        });
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("yaw")
                    .then(CommandManager.argument("angle", IntegerArgumentType.integer(-180, 180))
                            .executes(context -> {
                                int angle = IntegerArgumentType.getInteger(context, "angle");
                                System.out.println(angle);
                                MinecraftClient.getInstance().player.setYaw(angle);
                                return 1;
                            })
                    )
            );
        });
    }
}
