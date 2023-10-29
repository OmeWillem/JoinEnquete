package eu.omewillem.joinenquete.commands;

import eu.omewillem.joinenquete.JoinEnquete;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class CheckCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.hasPermission("enquete.check")) return false;

        // literally stole this code XD, its pretty goofy
        FileConfiguration config = JoinEnquete.getInstance().getPlayersConfig().getConfig();
        int totalPlayers = config.getConfigurationSection("players").getKeys(false).size();
        commandSender.sendMessage("Totale spelers: " + totalPlayers);
        for (String category : new String[]{"sites", "friend", "socialmedia", "discord", "other"}) {
            int count = config.getConfigurationSection("players").getKeys(false).stream().filter(uuid -> config.getString("players." + uuid).equalsIgnoreCase(category)).toArray().length;
            commandSender.sendMessage(category + ": " + count);
        }
        return false;
    }
}
