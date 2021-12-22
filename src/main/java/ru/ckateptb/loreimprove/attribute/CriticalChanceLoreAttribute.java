package ru.ckateptb.loreimprove.attribute;

import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.RandomUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ru.ckateptb.loreimprove.attribute.implement.AbstractPercentageLoreAttribute;
import ru.ckateptb.loreimprove.attribute.service.LoreAttributeService;
import ru.ckateptb.tablecloth.spring.SpringContext;

@Component
public class CriticalChanceLoreAttribute extends AbstractPercentageLoreAttribute implements Listener {
    @Override
    public String getName() {
        return config.getCriticalChanceName();
    }

    @Override
    public double getDefault() {
        return config.getCriticalChanceDefault();
    }

    @Override
    public String getDescription() {
        return config.getCriticalChanceDescription();
    }

    @Override
    public Material getIcon() {
        return Material.BLAZE_ROD;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void on(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        if(damager instanceof Projectile projectile) {
            damager = (Entity) projectile.getShooter();
        }
        if (damager instanceof Player player) {
            AnnotationConfigApplicationContext context = SpringContext.getInstance();
            LoreAttributeService attributeService = context.getBean(LoreAttributeService.class);
            if (RandomUtils.nextDouble(1, 100) < attributeService.getAttributeAmount(player, this)) {
                CriticalDamageLoreAttribute criticalDamage = context.getBean(CriticalDamageLoreAttribute.class);
                double damageMultiplier = attributeService.getAttributeAmount(player, criticalDamage) / 100;
                event.setDamage(event.getDamage() * damageMultiplier);
            }
        }
    }
}