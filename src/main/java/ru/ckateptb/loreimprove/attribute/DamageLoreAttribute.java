package ru.ckateptb.loreimprove.attribute;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.springframework.stereotype.Component;
import ru.ckateptb.loreimprove.LoreImprove;
import ru.ckateptb.loreimprove.attribute.implement.AbstractScalableLoreAttribute;
import ru.ckateptb.loreimprove.attribute.service.LoreAttributeService;
import ru.ckateptb.tablecloth.spring.SpringContext;

@Component
public class DamageLoreAttribute extends AbstractScalableLoreAttribute implements Listener {
    @Override
    public String getName() {
        return config.getDamageName();
    }

    @Override
    public String getDescription() {
        return config.getDamageDescription();
    }

    @Override
    public Material getIcon() {
        return Material.IRON_SWORD;
    }

    @Override
    public void setAmount(double value, ItemStack itemStack) {
        setAmountForMinecraftAttribute(value, itemStack, Attribute.GENERIC_ATTACK_DAMAGE);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void on(ProjectileLaunchEvent event) {
        Projectile projectile = event.getEntity();
        if (projectile.getShooter() instanceof Player player) {
            double damage = SpringContext.getInstance().getBean(LoreAttributeService.class).getAttributeAmount(player, this);
            projectile.setMetadata("damage_lore_attribute", new FixedMetadataValue(LoreImprove.getInstance(), damage));
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void on(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Projectile projectile) {
            if (projectile.hasMetadata("damage_lore_attribute")) {
                event.setDamage(projectile.getMetadata("damage_lore_attribute").get(0).asDouble());
            }
        }
    }
}
