package ru.ckateptb.loreimprove.attribute.implement;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.ckateptb.loreimprove.attribute.interfaces.SwitchableLoreAttribute;
import ru.ckateptb.loreimprove.util.Utils;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSwitchableLoreAttribute extends AbstractLoreAttribute implements SwitchableLoreAttribute {

    @Override
    public boolean isActive(ItemStack itemStack) {
        ItemMeta itemMeta = Utils.getItemMeta(itemStack);
        List<String> itemLore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
        assert itemLore != null;
        return itemLore.contains(getLore());
    }

    @Override
    public void setActive(boolean value, ItemStack itemStack) {
        ItemMeta itemMeta = Utils.getItemMeta(itemStack);
        List<String> itemLore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
        assert itemLore != null;
        if (value) itemLore.add(getLore());
        else itemLore.remove(getLore());
        itemMeta.setLore(itemLore);
        itemStack.setItemMeta(itemMeta);
    }

    @Override
    public String getLore() {
        return getName();
    }
}
