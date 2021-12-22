package ru.ckateptb.loreimprove.headpicture;

import lombok.SneakyThrows;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.OfflinePlayer;
import ru.ckateptb.tablecloth.minedown.MineDown;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.HttpURLConnection;
import java.net.URL;

public class PlayerHeadPicture extends AbstractHeadPicture {
    private final OfflinePlayer player;
    private final String mineDown;

    @SneakyThrows
    public PlayerHeadPicture(OfflinePlayer player) {
        this.player = player;
        this.mineDown = loadToMineDown();
    }

    @Override
    public BaseComponent[] toBaseComponent() {
        return MineDown.parse(mineDown);
    }

    @Override
    @SneakyThrows
    public BufferedImage getBufferedImage() {
        URL url = new URL("https://minepic.org/avatar/8/" + player.getName());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        return ImageIO.read(connection.getInputStream());
    }
}
