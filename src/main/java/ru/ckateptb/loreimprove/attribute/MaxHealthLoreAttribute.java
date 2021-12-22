package ru.ckateptb.loreimprove.attribute;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.ItemStack;
import org.springframework.stereotype.Component;
import ru.ckateptb.loreimprove.attribute.implement.AbstractScalableLoreAttribute;

@Component
public class MaxHealthLoreAttribute extends AbstractScalableLoreAttribute {
    @Override
    public String getName() {
        return config.getMaxHealthName();
    }

    @Override
    public String getDescription() {
        return config.getMaxHealthDescription();
    }

    @Override
    public Material getIcon() {
        return Material.REDSTONE;
    }

    @Override
    public void setAmount(double value, ItemStack itemStack) {
        setAmountForMinecraftAttribute(value, itemStack, Attribute.GENERIC_MAX_HEALTH);
    }
}
