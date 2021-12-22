package ru.ckateptb.loreimprove.headpicture;

import lombok.extern.slf4j.Slf4j;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.springframework.stereotype.Service;
import ru.ckateptb.tablecloth.async.AsyncService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class HeadPictureService implements Listener {
    private final AsyncService asyncService;
    private final Map<OfflinePlayer, HeadPicture> cache = new ConcurrentHashMap<>();
    private final HeadPicture defaultHeadPicture = new DefaultHeadPicture();

    public HeadPictureService(AsyncService asyncService) {
        this.asyncService = asyncService;
    }

    @EventHandler
    public void on(PlayerJoinEvent event) {
        OfflinePlayer player = event.getPlayer();
        asyncService.supplyAsync(() -> new PlayerHeadPicture(player), headPicture -> cache.put(player, (HeadPicture) headPicture));
    }

    public HeadPicture getHeadPicture(OfflinePlayer player) {
        return cache.getOrDefault(player, defaultHeadPicture);
    }
}
