package ru.ckateptb.loreimprove.headpicture;

import org.bukkit.ChatColor;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class AbstractHeadPicture implements HeadPicture{
    @Override
    public String loadToMineDown() {
        BufferedImage bufferedImage = getBufferedImage();
        Color[][] colors = new Color[bufferedImage.getWidth()][bufferedImage.getHeight()];
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                int rgb = bufferedImage.getRGB(x, y);
                colors[x][y] = new Color(rgb, true);
            }
        }
        StringBuilder builder = new StringBuilder();
        for (int y = 0; y < colors[0].length; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < colors.length; x++) {
                Color color = colors[x][y];
                // convert to minedown-styled color string
                if (color != null) {
                    line.append("&")
                            .append(String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()))
                            .append("&")
                            .append("\u2588");
                } else {
                    line.append(' ');
                }
            }
            builder.append(line).append(ChatColor.RESET).append("\n");
        }
        return builder.toString();
    }
}
