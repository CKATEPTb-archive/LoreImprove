package ru.ckateptb.loreimprove.attribute.implement;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.ckateptb.loreimprove.attribute.interfaces.ScalableLoreAttribute;
import ru.ckateptb.loreimprove.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractScalableLoreAttribute extends AbstractLoreAttribute implements ScalableLoreAttribute {
    @Override
    public double getDefault() {
        return 0;
    }

    @Override
    public String getLore() {
        return getName() + ": %s";
    }

    @Override
    public double getAmount(ItemStack itemStack) {
        ItemMeta itemMeta = Utils.getItemMeta(itemStack);
        List<String> itemLore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
        assert itemLore != null;
        for (String lore : itemLore) {
            Matcher matcher = Pattern.compile(getName() + ": (.+)$").matcher(lore);
            if (matcher.find()) {
                return Double.parseDouble(matcher.group(1));
            }
//            if (lore.startsWith(getLore())) {
//                return Double.parseDouble(lore.substring(getLore().length() - 1));
//            }
        }
        return 0;
    }

    public void setAmountForMinecraftAttribute(double value, ItemStack itemStack, Attribute attribute) {
        ItemMeta itemMeta = Utils.getItemMeta(itemStack);
        List<String> itemLore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
        List<String> newItemLore = new ArrayList<>();
        assert itemLore != null;
        for (String lore : itemLore) {
            if (!lore.startsWith(getName())) {
                newItemLore.add(lore);
            }
        }
        itemMeta.removeAttributeModifier(attribute);
        if (value != 0) {
            newItemLore.add(String.format(getLore(), value));
            if (itemStack.getType().name().toLowerCase().contains("_helmet"))
                itemMeta.addAttributeModifier(attribute, new AttributeModifier(UUID.randomUUID(), attribute.name(), value, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
            else if (itemStack.getType().name().toLowerCase().contains("_chestplate"))
                itemMeta.addAttributeModifier(attribute, new AttributeModifier(UUID.randomUUID(), attribute.name(), value, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            else if (itemStack.getType().name().toLowerCase().contains("_leggings"))
                itemMeta.addAttributeModifier(attribute, new AttributeModifier(UUID.randomUUID(), attribute.name(), value, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
            else if (itemStack.getType().name().toLowerCase().contains("_boots"))
                itemMeta.addAttributeModifier(attribute, new AttributeModifier(UUID.randomUUID(), attribute.name(), value, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
            else
                itemMeta.addAttributeModifier(attribute, new AttributeModifier(UUID.randomUUID(), attribute.name(), value, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        }
        itemMeta.setLore(newItemLore);
        itemStack.setItemMeta(itemMeta);
    }
}
