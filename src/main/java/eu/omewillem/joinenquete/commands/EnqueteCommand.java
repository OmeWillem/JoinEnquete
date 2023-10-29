package eu.omewillem.joinenquete.commands;

import eu.omewillem.joinenquete.JoinEnquete;
import eu.omewillem.joinenquete.gui.EnqueteGUI;
import eu.omewillem.joinenquete.utils.Utils;
import eu.omewillem.joinenquete.utils.webhook.DiscordWebhook;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnqueteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        if (Utils.hasDone(player.getUniqueId())) {
            player.sendMessage(Utils.parse(JoinEnquete.getInstance().getConfig().getString("messages.alreadydone")));
            return false;
        }

        new EnqueteGUI().open(player);
        return false;
    }
}
