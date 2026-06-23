package me.coddrago.heroku;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class QRCodeGenerator {
    public static void generateConsoleQR(String data) {
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 40, 40);
            StringBuilder sb = new StringBuilder("\n");
            for (int y = 0; y < matrix.getHeight(); y++) {
                for (int x = 0; x < matrix.getWidth(); x++) {
                    sb.append(matrix.get(x, y) ? "██" : "  ");
                }
                sb.append("\n");
            }
            HerokuMod.LOGGER.info("QR Code for Telegram Login:" + sb.toString());
        } catch (Exception e) {
            HerokuMod.LOGGER.error("Failed to generate QR Code", e);
        }
    }
}
