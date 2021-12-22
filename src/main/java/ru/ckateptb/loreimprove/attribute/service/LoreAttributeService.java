package ru.ckateptb.loreimprove.attribute.service;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.springframework.stereotype.Service;
import ru.ckateptb.loreimprove.attribute.interfaces.LoreAttribute;
import ru.ckateptb.loreimprove.attribute.interfaces.ScalableLoreAttribute;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Getter
public class LoreAttributeService {
    private final List<LoreAttribute> loreAttributes;

    public LoreAttributeService() {
        this.loreAttributes = new ArrayList<>();
    }

    public void register(LoreAttribute loreAttribute) {
        loreAttributes.add(loreAttribute);
        loreAttributes.sort(Comparator.comparing(lAttribute -> ChatColor.stripColor(lAttribute.getName())));
    }

    public void unregister(LoreAttribute loreAttribute) {
        loreAttributes.remove(loreAttribute);
    }

    public double getAttributeAmount(Player player, ScalableLoreAttribute attribute) {
        EntityEquipment equipment = player.getEquipment();
        double result = attribute.getDefault();
        if (equipment != null) {
            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                ItemStack itemStack = equipment.getItem(equipmentSlot);
                if (itemStack != null) {
                    if (itemStack.getType() == Material.AIR) continue;
                    result += attribute.getAmount(itemStack);
                }
            }
        }
        return result;
    }
}
