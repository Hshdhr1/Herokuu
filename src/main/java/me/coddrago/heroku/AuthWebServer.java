package me.coddrago.heroku;

import fi.iki.elonen.NanoHTTPD;
import java.io.IOException;
import java.util.Map;

public class AuthWebServer extends NanoHTTPD {
    private final TelegramManager manager;

    public AuthWebServer(int port, TelegramManager manager) {
        super(port);
        this.manager = manager;
    }

    @Override
    public Response serve(IHTTPSession session) {
        String msg = "<html><body><h1>Heroku Telegram Login</h1>" +
                     "<form action='/login' method='get'>" +
                     "Phone: <input type='text' name='phone'><br>" +
                     "<input type='submit' value='Login'>" +
                     "</form></body></html>";

        Map<String, String> parms = session.getParms();
        if (parms.get("phone") != null) {
            String phone = parms.get("phone");
            HerokuMod.LOGGER.info("Web Login attempt with phone: " + phone);
            // In real app, call manager.handlePhone(phone)
            msg = "<html><body><h1>Verification Code Sent</h1>" +
                  "<form action='/verify' method='get'>" +
                  "Code: <input type='text' name='code'><br>" +
                  "<input type='submit' value='Verify'>" +
                  "</form></body></html>";
        }

        return newFixedLengthResponse(msg);
    }
}
