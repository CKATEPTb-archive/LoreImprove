package ru.ckateptb.loreimprove.config;

import lombok.Getter;
import org.bukkit.plugin.Plugin;
import org.springframework.stereotype.Component;
import ru.ckateptb.loreimprove.LoreImprove;
import ru.ckateptb.tablecloth.config.ConfigField;
import ru.ckateptb.tablecloth.config.YamlConfig;

@Getter
@Component
public class LoreImproveConfig extends YamlConfig {
    @ConfigField(name = "attributes.armor.name")
    private String armorName = "§2Armor";
    @ConfigField(name = "attributes.armor.description")
    private String armorDescription = "§7Increases armor level";

    @ConfigField(name = "attributes.damage.name")
    private String damageName = "§2Damage";
    @ConfigField(name = "attributes.damage.description")
    private String damageDescription = "§7Increases damage dealt";

    @ConfigField(name = "attributes.dodge.name")
    private String dodgeName = "§2Dodge";
    @ConfigField(name = "attributes.dodge.default")
    private double dodgeDefault = 5;
    @ConfigField(name = "attributes.dodge.description")
    private String dodgeDescription = "§7Allows you to dodge an attack §8makes a jump back";

    @ConfigField(name = "attributes.life-steal.name")
    private String lifeStealName = "§2LifeSteal";
    @ConfigField(name = "attributes.life-steal.default")
    private double lifeStealDefault = 0;
    @ConfigField(name = "attributes.life-steal.description")
    private String lifeStealDescription = "§7Restores health in volume from damage dealt";

    @ConfigField(name = "attributes.max-health.name")
    private String maxHealthName = "§2Health";
    @ConfigField(name = "attributes.max-health.description")
    private String maxHealthDescription = "§7Increases maximum health";

    @ConfigField(name = "attributes.unbreakable.name")
    private String unbreakableName = "§2Unbreakable";
    @ConfigField(name = "attributes.unbreakable.description")
    private String unbreakableDescription = "§7The item will not lose durability";

    @ConfigField(name = "attributes.undroppable.name")
    private String undroppableName = "§2Undroppable";
    @ConfigField(name = "attributes.undroppable.description")
    private String undroppableDescription = "§7Prevents item drops after death";

    @ConfigField(name = "attributes.critical-damage.name")
    private String criticalDamageName = "§2Crit.Damage";
    @ConfigField(name = "attributes.critical-damage.default")
    private double criticalDamageDefault = 150;
    @ConfigField(name = "attributes.critical-damage.description")
    private String criticalDamageDescription = "§7Damage on critical hit";

    @ConfigField(name = "attributes.critical-chance.name")
    private String criticalChanceName = "§2Crit.Chance";
    @ConfigField(name = "attributes.critical-chance.default")
    private double criticalChanceDefault = 10;
    @ConfigField(name = "attributes.critical-chance.description")
    private String criticalChanceDescription = "§7Сritical hit chance";

    @ConfigField(name = "attributes.stun.name")
    private String stunName = "§2Stun";
    @ConfigField(name = "attributes.stun.default")
    private double stunDefault = 0;
    @ConfigField(name = "attributes.stun.duration", comment = "in ms")
    private long stunDuration = 300;
    @ConfigField(name = "attributes.stun.description")
    private String stunDescription = "§7Chance to stun target when dealing damage";

    @ConfigField(name = "gui.name")
    private String attributeManagerGuiName = "§0Item Editor";
    @ConfigField(name = "gui.empty-slot.name")
    private String emptySlotName = "§b§l<==§2§l Empty §b§l==>";
    @ConfigField(name = "gui.empty-slot.description")
    private String emptySlotDescription = "§7 > Place an item to edit here <";
    @ConfigField(name = "gui.rename")
    private String rename = "§aRename";
    @ConfigField(name = "gui.clean")
    private String clean = "§aClean";
    @ConfigField(name = "gui.hide")
    private String hideInformation = "§aHide";
    @ConfigField(name = "gui.next-page")
    private String nextPage = "§aNext page";
    @ConfigField(name = "gui.previous-page")
    private String previousPage = "§aPrevious page";
    @ConfigField(name = "gui.enter-text")
    private String enterText = "Enter Text: ";
    @ConfigField(name = "gui.enter-number")
    private String enterNumber = "Enter Number";

    @Override
    public Plugin getPlugin() {
        return LoreImprove.getInstance();
    }

    @Override
    public String getName() {
        return "config.yml";
    }
}
