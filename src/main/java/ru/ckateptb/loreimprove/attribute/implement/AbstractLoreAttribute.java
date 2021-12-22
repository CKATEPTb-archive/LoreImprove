package ru.ckateptb.loreimprove.attribute.implement;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ckateptb.loreimprove.attribute.interfaces.LoreAttribute;
import ru.ckateptb.loreimprove.config.LoreImproveConfig;

public abstract class AbstractLoreAttribute implements LoreAttribute {
    @Autowired
    protected LoreImproveConfig config;
}
