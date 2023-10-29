package eu.omewillem.joinenquete;

import eu.omewillem.joinenquete.commands.CheckCommand;
import eu.omewillem.joinenquete.commands.EnqueteCommand;
import eu.omewillem.joinenquete.listeners.JoinListener;
import eu.omewillem.joinenquete.utils.PluginConfig;
import eu.omewillem.joinenquete.utils.gui.GUIHolder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class JoinEnquete extends JavaPlugin {

    @Getter
    private static JoinEnquete instance;

    @Getter
    private PluginConfig playersConfig;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        instance = this;
        playersConfig = new PluginConfig(this, "players.yml", true);

        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        getCommand("enquete").setExecutor(new EnqueteCommand());
        getCommand("checkenquete").setExecutor(new CheckCommand());

        GUIHolder.init(this);
    }

    @Override
    public void onDisable() { playersConfig.saveConfig(); }

}
