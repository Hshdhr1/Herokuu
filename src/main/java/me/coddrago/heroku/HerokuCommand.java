package me.coddrago.heroku;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class HerokuCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("heroku")
                .executes(context -> {
                    context.getSource().sendFeedback(() -> Text.literal("Starting Heroku with Web Auth..."), false);
                    HerokuMod.getTelegramManager().start(false);
                    return 1;
                })
                .then(literal("no-web")
                    .executes(context -> {
                        context.getSource().sendFeedback(() -> Text.literal("Starting Heroku with QR Auth..."), false);
                        HerokuMod.getTelegramManager().start(true);
                        return 1;
                    })
                )
                .then(literal("stop")
                    .executes(context -> {
                        context.getSource().sendFeedback(() -> Text.literal("Stopping Heroku..."), false);
                        HerokuMod.getTelegramManager().stop();
                        return 1;
                    })
                )
            );
        });
    }
}
