package me.coddrago.heroku;

import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class TelegramManager {
    private Client client;
    private final HerokuConfig config;
    private HerokuDatabase database;
    private final HerokuDispatcher dispatcher;
    private boolean isAuthorized = false;

    public TelegramManager(HerokuConfig config, HerokuDispatcher dispatcher) {
        this.config = config;
        this.dispatcher = dispatcher;
    }

    public void start() {
        // In a real implementation, we would initialize TDLib here
        // For this port, we provide the structure
        HerokuMod.LOGGER.info("Starting Telegram Manager...");

        // This is a placeholder for the actual TDLib client initialization
        // client = Client.create(new UpdateHandler(), null, null);
    }

    public void stop() {
        if (client != null) {
            client.send(new TdApi.Close(), result -> HerokuMod.LOGGER.info("Telegram client closed"));
        }
    }

    private class UpdateHandler implements Client.ResultHandler {
        @Override
        public void onResult(TdApi.Object object) {
            if (object instanceof TdApi.UpdateNewMessage) {
                dispatcher.dispatchMessage(((TdApi.UpdateNewMessage) object).message);
            } else if (object instanceof TdApi.UpdateAuthorizationState) {
                handleAuthState(((TdApi.UpdateAuthorizationState) object).authorizationState);
            }
        }
    }

    private void handleAuthState(TdApi.AuthorizationState state) {
        if (state instanceof TdApi.AuthorizationStateReady) {
            isAuthorized = true;
            HerokuMod.LOGGER.info("Telegram authorized!");
            // Initialize database once authorized and we have user ID
            client.send(new TdApi.GetMe(), result -> {
                if (result instanceof TdApi.User) {
                    this.database = new HerokuDatabase(((TdApi.User) result).id);
                }
            });
        }
    }

    public void sendText(long chatId, String text) {
        if (client != null) {
            TdApi.InputMessageContent content = new TdApi.InputMessageText(new TdApi.FormattedText(text, null), null, true);
            client.send(new TdApi.SendMessage(chatId, 0, 0, null, null, content), null);
        }
    }
}
