package me.coddrago.heroku.commands;

import org.drinkless.tdlib.TdApi;

public abstract class CommandBase {
    public abstract String getName();
    public abstract String getDescription();
    public abstract void execute(TdApi.Message message, String args, me.coddrago.heroku.TelegramManager manager);
}
