package me.coddrago.heroku.commands;

import org.drinkless.tdlib.TdApi;
import me.coddrago.heroku.TelegramManager;

public class InfoCommand extends CommandBase {
    @Override
    public String getName() { return "info"; }

    @Override
    public String getDescription() { return "Shows information about the mod"; }

    @Override
    public void execute(TdApi.Message message, String args, TelegramManager manager) {
        String info = "<b>Heroku Mod v1.0.0</b>\n" +
                      "Running on Minecraft 1.21.11\n" +
                      "Telegram Userbot Integration via TDLib";
        manager.sendText(message.chatId, info);
    }
}
