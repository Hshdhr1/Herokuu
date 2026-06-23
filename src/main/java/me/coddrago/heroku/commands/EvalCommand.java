package me.coddrago.heroku.commands;

import org.drinkless.tdlib.TdApi;
import me.coddrago.heroku.TelegramManager;

public class EvalCommand extends CommandBase {
    @Override
    public String getName() { return "eval"; }

    @Override
    public String getDescription() { return "Evaluates Java code (Mock)"; }

    @Override
    public void execute(TdApi.Message message, String args, TelegramManager manager) {
        if (args.isEmpty()) {
            manager.sendText(message.chatId, "Please provide code to evaluate.");
            return;
        }
        // In a real implementation, we would use a script engine or compiler API
        manager.sendText(message.chatId, "Result: <code>" + args + "</code> (Eval is restricted in this version)");
    }
}
