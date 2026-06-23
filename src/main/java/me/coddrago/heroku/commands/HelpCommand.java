package me.coddrago.heroku.commands;

import org.drinkless.tdlib.TdApi;
import me.coddrago.heroku.TelegramManager;

public class HelpCommand extends CommandBase {
    @Override
    public String getName() { return "help"; }

    @Override
    public String getDescription() { return "Shows this help message"; }

    @Override
    public void execute(TdApi.Message message, String args, TelegramManager manager) {
        StringBuilder sb = new StringBuilder("<b>Available Commands:</b>\n");
        CommandRegistry.getAll().values().forEach(cmd ->
            sb.append(".").append(cmd.getName()).append(" - ").append(cmd.getDescription()).append("\n")
        );
        manager.sendText(message.chatId, sb.toString());
    }
}
