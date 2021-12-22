package ru.ckateptb.loreimprove.attribute;

import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.RandomUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;
import org.springframework.stereotype.Component;
import ru.ckateptb.loreimprove.attribute.implement.AbstractPercentageLoreAttribute;
import ru.ckateptb.loreimprove.attribute.service.LoreAttributeService;
import ru.ckateptb.tablecloth.spring.SpringContext;

@Component
public class DodgeLoreAttribute extends AbstractPercentageLoreAttribute implements Listener {
    @Override
    public String getName() {
        return config.getDodgeName();
    }

    @Override
    public double getDefault() {
        return config.getDodgeDefault();
    }

    @Override
    public String getDescription() {
        return config.getDodgeDescription();
    }

    @Override
    public Material getIcon() {
        return Material.FEATHER;
    }

    @EventHandler
    public void on(EntityDamageEvent event) {
        EntityDamageEvent.DamageCause cause = event.getCause();
        if (cause == EntityDamageEvent.DamageCause.ENTITY_ATTACK || cause == EntityDamageEvent.DamageCause.PROJECTILE) {
            Entity entity = event.getEntity();
            if (entity instanceof Player player) {
                if(player.hasMetadata("tablecloth:paralyze")) return;
                if (RandomUtils.nextDouble(1, 100) < SpringContext.getInstance().getBean(LoreAttributeService.class).getAttributeAmount(player, this)) {
                    event.setCancelled(true);
                    Vector evadeVelocity = player.getLocation().getDirection().normalize().multiply(-1);
                    evadeVelocity.setY(0.3);
                    player.setVelocity(evadeVelocity);
                }
            }
        }
    }
}
