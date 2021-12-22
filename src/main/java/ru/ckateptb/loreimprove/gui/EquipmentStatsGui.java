package ru.ckateptb.loreimprove.gui;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.ckateptb.loreimprove.attribute.interfaces.ScalableLoreAttribute;
import ru.ckateptb.loreimprove.attribute.service.LoreAttributeService;
import ru.ckateptb.loreimprove.headpicture.HeadPicture;
import ru.ckateptb.loreimprove.headpicture.HeadPictureService;
import ru.ckateptb.tablecloth.gui.book.BookUtil;
import ru.ckateptb.tablecloth.minedown.MineDown;
import ru.ckateptb.tablecloth.spring.SpringContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EquipmentStatsGui {
    public void open(Player player) {
        AnnotationConfigApplicationContext context = SpringContext.getInstance();
        HeadPicture headPicture = context.getBean(HeadPictureService.class).getHeadPicture(player);
        LoreAttributeService attributeService = context.getBean(LoreAttributeService.class);
        List<ScalableLoreAttribute> attributes = attributeService.getLoreAttributes().stream()
                .map(attribute -> {
                    if (attribute instanceof ScalableLoreAttribute scalableAttribute) {
                        return scalableAttribute;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<BaseComponent[]> components = new ArrayList<>();

        StringBuilder header = new StringBuilder("     ");
        String mineDown = MineDown.stringify(headPicture.toBaseComponent()).replaceAll("\n", "\n     ");
        mineDown = mineDown.substring(0, mineDown.length()-5);
        header.append(mineDown);

        Iterator<ScalableLoreAttribute> iterator = attributes.iterator();
        while (iterator.hasNext()) {
            StringBuilder builder = new StringBuilder(header).append('\n');
            for(int i = 0; i < 4; i ++) {
                if(!iterator.hasNext()) break;
                ScalableLoreAttribute attribute = iterator.next();
                builder.append("[")
                        .append(String.format(attribute.getLore(), attributeService.getAttributeAmount(player, attribute)))
                        .append("](hover=")
                        .append(attribute.getDescription())
                        .append(")\n");
            }
            components.add(MineDown.parse(builder.toString()));
        }
        ItemStack book = BookUtil.writtenBook()
                .author("LoreImprove")
                .title("Attribute Status")
                .pages(components)
                .build();
        BookUtil.openPlayer(player, book);
    }
}
