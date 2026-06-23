package me.coddrago.heroku;

import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.drinkless.tdlib.TdApi;

public class ChatBridge {
    private final TelegramManager telegramManager;
    private long targetChatId = 0; // Configurable target chat ID

    public ChatBridge(TelegramManager telegramManager) {
        this.telegramManager = telegramManager;
    }

    public void init() {
        ServerMessageEvents.CHAT_MESSAGE.register((message, sender, params) -> {
            if (targetChatId != 0) {
                String formatted = String.format("[%s] %s", sender.getName().getString(), message.getContent().getString());
                telegramManager.sendText(targetChatId, formatted);
            }
        });
    }

    public void onTelegramMessage(TdApi.Message message) {
        if (message.content instanceof TdApi.MessageText) {
            String text = ((TdApi.MessageText) message.content).text.text;
            // Broadcast to all Minecraft players
            // This requires access to the Minecraft server instance
        }
    }
}
