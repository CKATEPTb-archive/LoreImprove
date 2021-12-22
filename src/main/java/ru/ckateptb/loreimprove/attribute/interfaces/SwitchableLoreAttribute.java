package ru.ckateptb.loreimprove.attribute.interfaces;

import org.bukkit.inventory.ItemStack;
import ru.ckateptb.loreimprove.attribute.interfaces.LoreAttribute;

public interface SwitchableLoreAttribute extends LoreAttribute {
    boolean isActive(ItemStack itemStack);
    void setActive(boolean value, ItemStack itemStack);
}
