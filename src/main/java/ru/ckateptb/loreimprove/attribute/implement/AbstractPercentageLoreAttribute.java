package ru.ckateptb.loreimprove.attribute.implement;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.ckateptb.loreimprove.attribute.interfaces.ScalableLoreAttribute;
import ru.ckateptb.loreimprove.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractPercentageLoreAttribute extends AbstractScalableLoreAttribute implements ScalableLoreAttribute {

    @Override
    public String getLore() {
        return getName() + ": %s%%";
    }

    @Override
    public double getAmount(ItemStack itemStack) {
        ItemMeta itemMeta = Utils.getItemMeta(itemStack);
        List<String> itemLore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
        assert itemLore != null;
        for (String lore : itemLore) {
            Matcher matcher = Pattern.compile(getName() + ": (.+)%").matcher(lore);
            if (matcher.find()) {
                return Double.parseDouble(matcher.group(1));
            }
        }
        return 0;
    }

    @Override
    public void setAmount(double value, ItemStack itemStack) {
        ItemMeta itemMeta = Utils.getItemMeta(itemStack);
        List<String> itemLore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
        List<String> newItemLore = new ArrayList<>();
        assert itemLore != null;
        for (String lore : itemLore) {
            if (!lore.startsWith(getName())) {
                newItemLore.add(lore);
            }
        }
        if (value != 0) {
            newItemLore.add(String.format(getLore(), value));
        }
        itemMeta.setLore(newItemLore);
        itemStack.setItemMeta(itemMeta);
    }
}
