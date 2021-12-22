package ru.ckateptb.loreimprove.attribute;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.springframework.stereotype.Component;
import ru.ckateptb.loreimprove.attribute.implement.AbstractSwitchableLoreAttribute;

import java.util.*;

@Component
public class UndroppableLoreAttribute extends AbstractSwitchableLoreAttribute implements Listener {
    private final Map<UUID, List<ItemStack>> deathItems = new HashMap<>();

    @Override
    public String getName() {
        return config.getUndroppableName();
    }

    @Override
    public String getDescription() {
        return config.getUndroppableDescription();
    }

    @Override
    public Material getIcon() {
        return Material.TOTEM_OF_UNDYING;
    }

    @EventHandler
    public void on(PlayerDeathEvent event) {
        if (event.getKeepInventory()) return;
        List<ItemStack> itemsToSave = new ArrayList<>();
        event.getDrops().removeIf(itemStack -> {
            if (isActive(itemStack)) {
                itemsToSave.add(itemStack);
                return true;
            }
            return false;
        });
        deathItems.put(event.getEntity().getUniqueId(), itemsToSave);
    }

    @EventHandler
    public void on(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        if (deathItems.containsKey(playerUUID)) {
            player.getInventory().addItem(deathItems.get(playerUUID).toArray(new ItemStack[0]));
            deathItems.remove(playerUUID);
        }
    }
}
