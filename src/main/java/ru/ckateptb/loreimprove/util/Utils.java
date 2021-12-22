package ru.ckateptb.loreimprove.util;

import net.minecraft.world.item.ItemArmor;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utils {
    public static ItemMeta getItemMeta(ItemStack itemStack) {
        return itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
    }

    public static String encodeColors(String string) {
        return string.replaceAll("&", "§");
    }

    public static boolean isArmor(ItemStack item) {
        return (CraftItemStack.asNMSCopy(item).getItem() instanceof ItemArmor);
    }

    public static String decodeColors(String string) {
        return string.replaceAll("§", "&");
    }
}
