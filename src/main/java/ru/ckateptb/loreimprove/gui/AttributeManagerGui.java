package ru.ckateptb.loreimprove.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.ckateptb.loreimprove.attribute.interfaces.LoreAttribute;
import ru.ckateptb.loreimprove.attribute.interfaces.ScalableLoreAttribute;
import ru.ckateptb.loreimprove.attribute.interfaces.SwitchableLoreAttribute;
import ru.ckateptb.loreimprove.attribute.service.LoreAttributeService;
import ru.ckateptb.loreimprove.config.LoreImproveConfig;
import ru.ckateptb.loreimprove.util.AnvilUtils;
import ru.ckateptb.loreimprove.util.Utils;
import ru.ckateptb.tablecloth.gui.ItemButton;
import ru.ckateptb.tablecloth.gui.chest.ChestGui;
import ru.ckateptb.tablecloth.spring.SpringContext;

import java.util.Collections;
import java.util.Iterator;

public class AttributeManagerGui {
    private final Player player;
    private final AnvilUtils anvilUtils;
    private final ItemButton fillerButton;
    private final ItemButton targetButton;
    private final ItemButton renameButton;
    private final ItemButton hideFlagsButton;
    private final ItemButton clearButton;
    private final ItemButton nextPageButton;
    private final ItemButton previousPageButton;
    private final ItemStack emptyItem;
    private final ChestGui chestGui;
    private int currentPage = 1;

    public AttributeManagerGui(Player player, LoreImproveConfig config, AnvilUtils anvilUtils) {
        this.player = player;
        this.anvilUtils = anvilUtils;
        this.chestGui = new ChestGui(player, config.getAttributeManagerGuiName(), 4);

        ItemStack fillerItem = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta fillerItemMeta = fillerItem.getItemMeta();
        assert fillerItemMeta != null;
        fillerItemMeta.setDisplayName(" ");
        fillerItem.setItemMeta(fillerItemMeta);
        this.fillerButton = new ItemButton(fillerItem);

        this.emptyItem = new ItemStack(Material.NETHER_STAR);
        ItemMeta targetItemMeta = emptyItem.getItemMeta();
        assert targetItemMeta != null;
        targetItemMeta.addItemFlags(ItemFlag.values());
        targetItemMeta.setDisplayName(config.getEmptySlotName());
        targetItemMeta.setLore(Collections.singletonList(config.getEmptySlotDescription()));
        this.emptyItem.setItemMeta(targetItemMeta);

        Inventory inventory = chestGui.getInventory();
        this.chestGui.onClose(inventoryCloseEvent -> {
            ItemStack currentItem = inventory.getItem(4);
            assert currentItem != null;
            if (!currentItem.equals(this.emptyItem)) {
                player.getInventory().addItem(currentItem);
            }
        });

        this.targetButton = new ItemButton(this.emptyItem);
        targetButton.setHandle(event -> {
            ItemStack currentItem = event.getCurrentItem();
            ItemStack cursorItem = event.getCursor();
            assert cursorItem != null;
            Material cursorItemType = cursorItem.getType();
            assert currentItem != null;
            if (!currentItem.equals(this.emptyItem)) {
                player.getInventory().addItem(currentItem);
                inventory.setItem(4, null);
            }
            if (cursorItemType != Material.AIR) {
                inventory.setItem(4, cursorItem);
                player.setItemOnCursor(null);
            }
            if (inventory.getItem(4) == null) {
                chestGui.setSlot(4, targetButton);
            }
            updateAttributes();
            event.setCancelled(true);
        });

        ItemStack renameItem = new ItemStack(Material.NAME_TAG);
        ItemMeta renameItemMeta = renameItem.getItemMeta();
        assert renameItemMeta != null;
        renameItemMeta.addItemFlags(ItemFlag.values());
        renameItemMeta.setDisplayName(config.getRename());
        renameItem.setItemMeta(renameItemMeta);
        this.renameButton = new ItemButton(renameItem);
        this.renameButton.setHandle(event -> {
            ItemStack currentItem = inventory.getItem(4);
            assert currentItem != null;
            if (!currentItem.equals(emptyItem)) {
                chestGui.setIgnoreCloseEvent(true);
                anvilUtils.requestItemRename(player, currentItem, (player1, result) -> {
                }, player1 -> {
                    chestGui.setIgnoreCloseEvent(false);
                    chestGui.open();
                });
            }
            event.setCancelled(true);
        });

        ItemStack clearItem = new ItemStack(Material.PAPER);
        ItemMeta clearItemMeta = clearItem.getItemMeta();
        assert clearItemMeta != null;
        clearItemMeta.addItemFlags(ItemFlag.values());
        clearItemMeta.setDisplayName(config.getClean());
        clearItem.setItemMeta(clearItemMeta);
        this.clearButton = new ItemButton(clearItem);
        this.clearButton.setHandle(event -> {
            ItemStack currentItem = inventory.getItem(4);
            assert currentItem != null;
            if (!currentItem.equals(emptyItem)) {
//                ItemMeta itemMeta = Utils.getItemMeta(currentItem);
//                itemMeta.setLore(Collections.emptyList());
//                itemMeta.removeItemFlags(ItemFlag.values());
//                for (Enchantment enchantment : Enchantment.values()) {
//                    itemMeta.removeEnchant(enchantment);
//                }
//                for (Attribute attribute : Attribute.values()) {
//                    itemMeta.removeAttributeModifier(attribute);
//                }
                ItemMeta newMeta = Utils.getItemMeta(new ItemStack(currentItem.getType()));
//                itemMeta.setDisplayName(newMeta.getDisplayName());
//                currentItem.setItemMeta(itemMeta);
                currentItem.setItemMeta(newMeta);
            }
            event.setCancelled(true);
        });

        ItemStack hideFlagsItem = new ItemStack(Material.LEAD);
        ItemMeta hideFlagsItemMeta = hideFlagsItem.getItemMeta();
        assert hideFlagsItemMeta != null;
        hideFlagsItemMeta.addItemFlags(ItemFlag.values());
        hideFlagsItemMeta.setDisplayName(config.getHideInformation());
        hideFlagsItem.setItemMeta(hideFlagsItemMeta);
        this.hideFlagsButton = new ItemButton(hideFlagsItem);
        this.hideFlagsButton.setHandle(event -> {
            ItemStack currentItem = inventory.getItem(4);
            assert currentItem != null;
            if (!currentItem.equals(emptyItem)) {
                ItemMeta itemMeta = Utils.getItemMeta(currentItem);
                itemMeta.addItemFlags(ItemFlag.values());
                currentItem.setItemMeta(itemMeta);
            }
            event.setCancelled(true);
        });

        ItemStack nextPageItem = new ItemStack(Material.ARROW);
        ItemMeta nextPageItemMeta = nextPageItem.getItemMeta();
        assert nextPageItemMeta != null;
        nextPageItemMeta.addItemFlags(ItemFlag.values());
        nextPageItemMeta.setDisplayName(config.getNextPage());
        nextPageItem.setItemMeta(nextPageItemMeta);
        this.nextPageButton = new ItemButton(nextPageItem);
        this.nextPageButton.setHandle(event -> {
            this.currentPage++;
            updateAttributes();
            event.setCancelled(true);
        });

        ItemStack previousPageItem = new ItemStack(Material.ARROW);
        ItemMeta previousPageItemMeta = previousPageItem.getItemMeta();
        assert previousPageItemMeta != null;
        previousPageItemMeta.addItemFlags(ItemFlag.values());
        previousPageItemMeta.setDisplayName(config.getPreviousPage());
        previousPageItem.setItemMeta(previousPageItemMeta);
        this.previousPageButton = new ItemButton(previousPageItem);
        this.previousPageButton.setHandle(event -> {
            this.currentPage--;
            if(this.currentPage < 1) this.currentPage = 1;
            updateAttributes();
            event.setCancelled(true);
        });
    }

    public void updateAttributes() {
        Iterator<LoreAttribute> iterator = SpringContext.getInstance().getBean(LoreAttributeService.class).getLoreAttributes().iterator();
        for (int skip = 0; skip < (currentPage - 1) * 7; skip++) {
            if (iterator.hasNext()) iterator.next();
            else break;
        }
        Inventory inventory = chestGui.getInventory();
        ItemStack currentItem = inventory.getItem(4);
        for (int i = 18; i <= 33; i++) {
            if (i == 25 || i == 26) continue;
            chestGui.clearSlot(i);
            if (currentItem.equals(emptyItem)) continue;
            if (iterator.hasNext()) {
                LoreAttribute attribute = iterator.next();
                ItemStack itemStack = new ItemStack(attribute.getIcon());
                ItemMeta itemMeta = Utils.getItemMeta(itemStack);
                itemMeta.setDisplayName(attribute.getName());
                itemMeta.addItemFlags(ItemFlag.values());
                itemStack.setItemMeta(itemMeta);
                chestGui.setSlot(i, new ItemButton(itemStack).setHandle(event -> {
                    if (attribute instanceof SwitchableLoreAttribute switchableLoreAttribute) {
                        switchableLoreAttribute.setActive(!switchableLoreAttribute.isActive(currentItem), currentItem);
                    }
                    if (attribute instanceof ScalableLoreAttribute scalableLoreAttribute) {
                        chestGui.setIgnoreCloseEvent(true);
                        anvilUtils.requestNumber(player, (player1, result) -> scalableLoreAttribute.setAmount(result, currentItem), player1 -> {
                            chestGui.setIgnoreCloseEvent(false);
                            chestGui.open();
                        });
                    }
                    event.setCancelled(true);
                }));
            }
        }
        chestGui.clearSlot(26);
        chestGui.clearSlot(35);
        if (currentItem.equals(emptyItem)) return;
        if(this.currentPage > 1) chestGui.setSlot(26, previousPageButton);
        if(iterator.hasNext()) chestGui.setSlot(35, nextPageButton);
    }

    public void open() {
        chestGui.getInventory().clear();
        chestGui.fill(new ItemButton(new ItemStack(Material.AIR)));
        for (int i = 0; i < 18; i++) {
            chestGui.setSlot(i, fillerButton);
        }
        for (int i = 25; i < 36; i += 9) {
            chestGui.setSlot(i, fillerButton);
        }
        chestGui.setSlot(4, targetButton);
        chestGui.setSlot(6, renameButton);
        chestGui.setSlot(7, hideFlagsButton);
        chestGui.setSlot(8, clearButton);


        chestGui.open();
    }
}
