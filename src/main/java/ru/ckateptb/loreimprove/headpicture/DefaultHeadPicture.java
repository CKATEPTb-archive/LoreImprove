package ru.ckateptb.loreimprove.headpicture;

import lombok.SneakyThrows;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.plugin.Plugin;
import ru.ckateptb.loreimprove.LoreImprove;
import ru.ckateptb.tablecloth.minedown.MineDown;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class DefaultHeadPicture extends AbstractHeadPicture {
    private final String mineDown;

    public DefaultHeadPicture() {
        this.mineDown = loadToMineDown();
    }

    @Override
    public BaseComponent[] toBaseComponent() {
        return MineDown.parse(mineDown);
    }

    @Override
    @SneakyThrows
    public BufferedImage getBufferedImage() {
        Plugin plugin = LoreImprove.getInstance();
        File steve = new File(plugin.getDataFolder(), "Steve.png");
        if (!steve.exists()) {
            plugin.saveResource("Steve.png", false);
        }
        return ImageIO.read(steve);
    }
}
