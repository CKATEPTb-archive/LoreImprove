package ru.ckateptb.loreimprove.attribute;


import org.bukkit.Material;
import org.springframework.stereotype.Component;
import ru.ckateptb.loreimprove.attribute.implement.AbstractPercentageLoreAttribute;

@Component
public class CriticalDamageLoreAttribute extends AbstractPercentageLoreAttribute {
    @Override
    public String getName() {
        return config.getCriticalDamageName();
    }

    @Override
    public double getDefault() {
        return config.getCriticalDamageDefault();
    }

    @Override
    public String getDescription() {
        return config.getCriticalDamageDescription();
    }

    @Override
    public Material getIcon() {
        return Material.BLAZE_POWDER;
    }

}