package eu.omewillem.joinenquete.gui;

import eu.omewillem.joinenquete.JoinEnquete;
import eu.omewillem.joinenquete.utils.PluginConfig;
import eu.omewillem.joinenquete.utils.Utils;
import eu.omewillem.joinenquete.utils.gui.ItemBuilder;
import eu.omewillem.joinenquete.utils.enums.Choice;
import eu.omewillem.joinenquete.utils.gui.GUIHolder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class EnqueteGUI extends GUIHolder {

    // should probably cache the gui.
    public EnqueteGUI() {
        FileConfiguration config = JoinEnquete.getInstance().getConfig();
        this.inventory = Bukkit.createInventory(this, 27, Utils.parse(config.getString("messages.title")));

        inventory.setItem(11,
                new ItemBuilder(Utils.getSkull(config.getString("heads.sites")))
                        .setName(Choice.SITES.getDisplay())
                        .toItemStack());
        inventory.setItem(12,
                new ItemBuilder(Utils.getSkull(config.getString("heads.friend")))
                        .setName(Choice.FRIEND.getDisplay())
                        .toItemStack());
        inventory.setItem(13,
                new ItemBuilder(Utils.getSkull(config.getString("heads.socialmedia")))
                        .setName(Choice.SOCIALMEDIA.getDisplay())
                        .toItemStack());
        inventory.setItem(14,
                new ItemBuilder(Utils.getSkull(config.getString("heads.discord")))
                        .setName(Choice.DISCORD.getDisplay())
                        .toItemStack());
        inventory.setItem(15,
                new ItemBuilder(Utils.getSkull(config.getString("heads.other")))
                        .setName(Choice.OTHER.getDisplay())
                        .toItemStack());
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        int slot = event.getRawSlot();
        switch (slot) {
            case 11:
                setChoice(player,Choice.SITES);
                break;
            case 12:
                setChoice(player,Choice.FRIEND);
                break;
            case 13:
                setChoice(player,Choice.SOCIALMEDIA);
                break;
            case 14:
                setChoice(player,Choice.DISCORD);
                break;
            case 15:
                setChoice(player,Choice.OTHER);
                break;
        }
    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!Utils.hasDone(event.getPlayer().getUniqueId())) {
            event.getPlayer().sendMessage(Utils.parse(JoinEnquete.getInstance()
                    .getConfig().getString("messages.notdone")));
        }
    }

    public void setChoice(Player player, Choice choice) {
        player.closeInventory();

        PluginConfig config = JoinEnquete.getInstance().getPlayersConfig();
        config.getConfig().set("players." + player.getUniqueId(), choice.name().toLowerCase());

        Utils.sendWebhook(player, choice);
        player.sendMessage(Utils.parse(JoinEnquete.getInstance()
                .getConfig().getString("messages.done")));

        ConsoleCommandSender console = Bukkit.getConsoleSender();
        Bukkit.dispatchCommand(console, JoinEnquete.getInstance().getConfig().getString("command").replace("{player}", player.getName()));
    }
}
