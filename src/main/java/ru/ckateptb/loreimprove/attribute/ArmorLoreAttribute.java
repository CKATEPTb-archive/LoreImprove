package ru.ckateptb.loreimprove.attribute;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.ItemStack;
import org.springframework.stereotype.Component;
import ru.ckateptb.loreimprove.attribute.implement.AbstractScalableLoreAttribute;

@Component
public class ArmorLoreAttribute extends AbstractScalableLoreAttribute {
    @Override
    public String getName() {
        return config.getArmorName();
    }

    @Override
    public String getDescription() {
        return config.getArmorDescription();
    }

    @Override
    public Material getIcon() {
        return Material.SHIELD;
    }

    @Override
    public void setAmount(double value, ItemStack itemStack) {
        setAmountForMinecraftAttribute(value, itemStack, Attribute.GENERIC_ARMOR);
    }
}
