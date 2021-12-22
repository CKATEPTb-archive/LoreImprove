package ru.ckateptb.loreimprove.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.springframework.stereotype.Component;
import ru.ckateptb.loreimprove.LoreImprove;
import ru.ckateptb.loreimprove.config.LoreImproveConfig;
import ru.ckateptb.tablecloth.gui.anvil.AnvilGUI;

@Component
public class AnvilUtils {
    private final LoreImproveConfig config;

    public AnvilUtils(LoreImproveConfig config) {
        this.config = config;
    }

    public void requestItemRename(Player player, ItemStack item, CompleteTextHandler completeHandler, CloseAnvilHandler closeAnvilHandler) {
        ItemMeta itemMeta = Utils.getItemMeta(item);
        new AnvilGUI.Builder()
                .text(Utils.decodeColors(itemMeta.getDisplayName()))
                .itemLeft(item)
                .itemRight(new ItemStack(Material.AIR))
                .title(config.getRename())
                .plugin(LoreImprove.getInstance())
                .onClose(closeAnvilHandler::handle)
                .onComplete((player1, text) -> {
                    text = Utils.encodeColors(text.trim());
                    if (StringUtils.isNotBlank(text)) {
                        itemMeta.setDisplayName(text);
                        item.setItemMeta(itemMeta);
                    }
                    completeHandler.handle(player1, text);
                    return AnvilGUI.Response.close();
                }).open(player);
    }

    public void requestColoredText(Player player, CompleteTextHandler completeHandler, CloseAnvilHandler closeAnvilHandler) {
        new AnvilGUI.Builder()
                .text("")
                .itemLeft(new ItemStack(Material.NETHER_STAR))
                .itemRight(new ItemStack(Material.AIR))
                .title(config.getEnterText())
                .plugin(LoreImprove.getInstance())
                .onClose(closeAnvilHandler::handle)
                .onComplete((player1, text) -> {
                    text = Utils.encodeColors(text.trim());
                    if (StringUtils.isNotBlank(text)) {
                        completeHandler.handle(player, text);
                    }
                    return AnvilGUI.Response.close();
                }).open(player);
    }

    public void requestNumber(Player player, CompleteNumberHandler completeHandler, CloseAnvilHandler closeAnvilHandler) {
        new AnvilGUI.Builder()
                .text(config.getEnterNumber())
                .itemLeft(new ItemStack(Material.NETHER_STAR))
                .itemRight(new ItemStack(Material.AIR))
                .title(config.getEnterNumber() + ": ")
                .plugin(LoreImprove.getInstance())
                .onClose(closeAnvilHandler::handle)
                .onComplete((player1, text) -> {
                    text = text.trim();
                    if (!NumberUtils.isNumber(text)) {
                        return AnvilGUI.Response.text(config.getEnterNumber() + "!");
                    }
                    completeHandler.handle(player1, NumberUtils.toDouble(text, 0));
                    return AnvilGUI.Response.close();
                }).open(player);
    }

    public interface CompleteTextHandler {
        void handle(Player player, String result);
    }

    public interface CompleteNumberHandler {
        void handle(Player player, Double result);
    }

    public interface CloseAnvilHandler {
        void handle(Player player);
    }
}
