package ru.ckateptb.loreimprove.attribute;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.springframework.stereotype.Component;
import ru.ckateptb.loreimprove.attribute.implement.AbstractSwitchableLoreAttribute;
import ru.ckateptb.loreimprove.util.Utils;

@Component
public class UnbreakableLoreAttribute extends AbstractSwitchableLoreAttribute {
    @Override
    public String getName() {
        return config.getUnbreakableName();
    }

    @Override
    public String getDescription() {
        return config.getUnbreakableDescription();
    }

    @Override
    public Material getIcon() {
        return Material.TURTLE_HELMET;
    }

    @Override
    public boolean isActive(ItemStack itemStack) {
        ItemMeta itemMeta = Utils.getItemMeta(itemStack);
        return itemMeta.isUnbreakable();
    }

    @Override
    public void setActive(boolean value, ItemStack itemStack) {
        super.setActive(value, itemStack);
        ItemMeta itemMeta = Utils.getItemMeta(itemStack);
        itemMeta.setUnbreakable(value);
        itemStack.setItemMeta(itemMeta);
    }
}
