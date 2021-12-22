package ru.ckateptb.loreimprove.command;

import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.springframework.stereotype.Component;
import ru.ckateptb.loreimprove.LoreImprove;
import ru.ckateptb.loreimprove.config.LoreImproveConfig;
import ru.ckateptb.loreimprove.gui.AttributeManagerGui;
import ru.ckateptb.loreimprove.gui.EquipmentStatsGui;
import ru.ckateptb.loreimprove.util.AnvilUtils;
import ru.ckateptb.tablecloth.spring.SpringContext;
import ru.ckateptb.tablecloth.temporary.paralyze.TemporaryParalyze;

@Component
public class LoreImproveCommand {
    public LoreImproveCommand(LoreImproveConfig config, AnvilUtils anvilUtils) {
        new CommandAPICommand("loreimprove")
                .withAliases("lorei", "li")
                .withPermission("loreimprove.admin")
                .executesPlayer((sender, args) -> {
                    new AttributeManagerGui(sender, config, anvilUtils).open();
                })
                .withSubcommand(
                        new CommandAPICommand("reload")
                                .executes((sender, args) -> {
                                    SpringContext.getInstance().getBean(LoreImproveConfig.class).load();
                                })
                )
                .register();
        new CommandAPICommand("attributeinfo")
                .withAliases("ai", "attributes", "attribute", "attributei")
                .withPermission("loreimprove.info")
                .executesPlayer((sender, args) -> {
                    new EquipmentStatsGui().open(sender);
                })
                .register();
    }
}
