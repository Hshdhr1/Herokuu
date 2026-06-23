package me.coddrago.heroku;

import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;

public class AuthHandler {
    private final Client client;

    public AuthHandler(Client client) {
        this.client = client;
    }

    public void setAuthenticationPhoneNumber(String phoneNumber) {
        client.send(new TdApi.SetAuthenticationPhoneNumber(phoneNumber, null), result -> {
            if (result instanceof TdApi.Error) {
                HerokuMod.LOGGER.error("Phone number error: " + ((TdApi.Error) result).message);
            }
        });
    }

    public void checkAuthenticationCode(String code) {
        client.send(new TdApi.CheckAuthenticationCode(code), result -> {
            if (result instanceof TdApi.Error) {
                HerokuMod.LOGGER.error("Auth code error: " + ((TdApi.Error) result).message);
            }
        });
    }

    public void checkAuthenticationPassword(String password) {
        client.send(new TdApi.CheckAuthenticationPassword(password), result -> {
            if (result instanceof TdApi.Error) {
                HerokuMod.LOGGER.error("2FA password error: " + ((TdApi.Error) result).message);
            }
        });
    }
}
