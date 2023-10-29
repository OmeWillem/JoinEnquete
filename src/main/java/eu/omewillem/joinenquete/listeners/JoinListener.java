package eu.omewillem.joinenquete.listeners;

import eu.omewillem.joinenquete.JoinEnquete;
import eu.omewillem.joinenquete.gui.EnqueteGUI;
import eu.omewillem.joinenquete.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (Utils.hasDone(player.getUniqueId())) return;
        if (!player.hasPlayedBefore()) {
            Bukkit.getScheduler().runTaskLater(JoinEnquete.getInstance(), () -> {
                new EnqueteGUI().open(player);
            }, 10);
            return;
        }

        event.getPlayer().sendMessage(Utils.parse(JoinEnquete.getInstance()
                .getConfig().getString("messages.notdone")));
    }
}
