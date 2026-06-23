package me.coddrago.heroku;

import org.drinkless.tdlib.TdApi;

public class HerokuDispatcher {
    private final HerokuMod mod;
    private HerokuConfig config;

    public HerokuDispatcher(HerokuMod mod, HerokuConfig config) {
        this.mod = mod;
        this.config = config;
    }

    public void dispatchMessage(TdApi.Message message) {
        if (message.content instanceof TdApi.MessageText) {
            String text = ((TdApi.MessageText) message.content).text.text;
            if (text.startsWith(config.command_prefix)) {
                handleCommand(message, text.substring(config.command_prefix.length()));
            }
        }
    }

    private void handleCommand(TdApi.Message message, String commandLine) {
        String[] parts = commandLine.split("\\s+", 2);
        String commandName = parts[0].toLowerCase();
        String args = parts.length > 1 ? parts[1] : "";

        HerokuMod.LOGGER.info("Handling command: " + commandName + " with args: " + args);

        me.coddrago.heroku.commands.CommandBase command = me.coddrago.heroku.commands.CommandRegistry.get(commandName);
        if (command != null) {
            command.execute(message, args, HerokuMod.getTelegramManager());
        }
    }
}
