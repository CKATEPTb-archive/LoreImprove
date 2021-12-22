package ru.ckateptb.loreimprove.attribute.interfaces;

import org.bukkit.inventory.ItemStack;
import ru.ckateptb.loreimprove.attribute.interfaces.LoreAttribute;

public interface ScalableLoreAttribute extends LoreAttribute {
    double getAmount(ItemStack itemStack);
    double getDefault();
    void setAmount(double value, ItemStack itemStack);
}
