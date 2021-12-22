package ru.ckateptb.loreimprove;

import lombok.Getter;
import ru.ckateptb.loreimprove.attribute.interfaces.LoreAttribute;
import ru.ckateptb.loreimprove.attribute.service.LoreAttributeService;
import ru.ckateptb.tablecloth.Tablecloth;
import ru.ckateptb.tablecloth.spring.plugin.SpringPlugin;

public final class LoreImprove extends SpringPlugin {
    @Getter
    private static LoreImprove instance;

    public LoreImprove() {
        //TODO Сделать аттрибут для луков и арбалетов, на автоматическое попадание в цель
        instance = this;
        Tablecloth.getInstance().registerSpringStartUpHandler(context -> {
            LoreAttributeService loreAttributeService = context.getBean(LoreAttributeService.class);
            context.getBeansOfType(LoreAttribute.class).values().forEach(loreAttributeService::register);
        });
    }
}
