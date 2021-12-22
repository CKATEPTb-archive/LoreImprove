package ru.ckateptb.loreimprove.headpicture;

import net.md_5.bungee.api.chat.BaseComponent;

import java.awt.image.BufferedImage;

public interface HeadPicture {
    BaseComponent[] toBaseComponent();
    BufferedImage getBufferedImage();
    String loadToMineDown();
}
