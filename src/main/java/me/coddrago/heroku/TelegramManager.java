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

    private AuthWebServer webServer;

    public void start(boolean qrOnly) {
        HerokuMod.LOGGER.info("Starting Telegram Manager... QR Only: " + qrOnly);

        if (!qrOnly) {
            startWebServer();
        } else {
            // Logic for QR auth
            HerokuMod.LOGGER.info("QR Auth requested");
            QRCodeGenerator.generateConsoleQR("https://t.me/placeholder_login_link");
        }

        // client = Client.create(new UpdateHandler(), null, null);
    }

    private void startWebServer() {
        try {
            webServer = new AuthWebServer(8080, this);
            webServer.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
            HerokuMod.LOGGER.info("Auth Web Server started at http://localhost:8080");
        } catch (IOException e) {
            HerokuMod.LOGGER.error("Failed to start Web Server", e);
        }
    }

    public void stop() {
        if (webServer != null) {
            webServer.stop();
            webServer = null;
            HerokuMod.LOGGER.info("Auth Web Server stopped.");
        }
        if (client != null) {
            client.send(new TdApi.Close(), result -> HerokuMod.LOGGER.info("Telegram client closed"));
            client = null;
        }
        isAuthorized = false;
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
