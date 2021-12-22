package ru.ckateptb.loreimprove.attribute;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.springframework.stereotype.Component;
import ru.ckateptb.loreimprove.attribute.implement.AbstractPercentageLoreAttribute;
import ru.ckateptb.loreimprove.attribute.service.LoreAttributeService;
import ru.ckateptb.tablecloth.spring.SpringContext;

@Component
public class LifeStealLoreAttribute extends AbstractPercentageLoreAttribute implements Listener {
    @Override
    public String getName() {
        return config.getLifeStealName();
    }

    @Override
    public double getDefault() {
        return config.getLifeStealDefault();
    }

    @Override
    public String getDescription() {
        return config.getLifeStealDescription();
    }

    @Override
    public Material getIcon() {
        return Material.REDSTONE_TORCH;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void on(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof LivingEntity livingEntity) {
            Entity damager = event.getDamager();
            if(damager instanceof Projectile projectile) {
                damager = (Entity) projectile.getShooter();
            }
            if (damager instanceof Player player) {
                double finalDamage = Math.min(event.getDamage(), livingEntity.getHealth());
                double lifeSteal = SpringContext.getInstance().getBean(LoreAttributeService.class).getAttributeAmount(player, this);
                double shouldHealth = finalDamage * (lifeSteal / 100);
                if (shouldHealth > 0) {
                    AttributeInstance maxHealthAttribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                    double maxHealth = maxHealthAttribute == null ? 20 : maxHealthAttribute.getValue();
                    player.setHealth(Math.min(maxHealth, player.getHealth() + shouldHealth));
                }
            }
        }
    }
}
