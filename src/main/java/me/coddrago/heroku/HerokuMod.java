package me.coddrago.heroku;

import me.coddrago.heroku.commands.CommandRegistry;
import me.coddrago.heroku.commands.HelpCommand;
import me.coddrago.heroku.commands.EvalCommand;
import me.coddrago.heroku.commands.InfoCommand;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HerokuMod implements ModInitializer {
    public static final String MOD_ID = "heroku";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static HerokuConfig config;
    private static TelegramManager telegramManager;
    private static HerokuDispatcher dispatcher;
    private static ChatBridge chatBridge;

    @Override
    public void onInitialize() {
        LOGGER.info("Heroku Mod Initializing...");

        net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.SERVER_STARTING.register(ChatBridge::setServer);

        config = HerokuConfig.load();
        config.save(); // Ensure file exists

        dispatcher = new HerokuDispatcher(this, config);
        telegramManager = new TelegramManager(config, dispatcher);
        chatBridge = new ChatBridge(telegramManager);

        // Register commands
        CommandRegistry.register(new HelpCommand());
        CommandRegistry.register(new InfoCommand());
        CommandRegistry.register(new EvalCommand());

        // Minecraft Commands
        HerokuCommand.register();

        // Chat bridge init
        chatBridge.init();

        LOGGER.info("Heroku Mod Initialized successfully!");
    }

    public static HerokuConfig getConfig() { return config; }
    public static TelegramManager getTelegramManager() { return telegramManager; }
}
