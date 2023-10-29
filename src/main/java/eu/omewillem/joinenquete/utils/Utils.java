package eu.omewillem.joinenquete.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import eu.omewillem.joinenquete.JoinEnquete;
import eu.omewillem.joinenquete.utils.enums.Choice;
import eu.omewillem.joinenquete.utils.webhook.DiscordWebhook;
import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.UUID;

@UtilityClass
public class Utils {
    public String parse(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public void sendWebhook(Player player, Choice choice) {
        if (JoinEnquete.getInstance().getConfig().getString("discord-webhook", "") == "") return;

        DiscordWebhook webhook = new DiscordWebhook(JoinEnquete.getInstance().getConfig().getString("discord-webhook"));
        webhook.setAvatarUrl("https://minotar.net/helm/"+player.getName()+"/600.png");
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(player.getName())
                .setDescription("heeft de keuze: **" + choice.name() + "** gemaakt.")
                .setColor(Color.BLACK));

        try {
            webhook.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasDone(UUID uuid) {
        FileConfiguration config = JoinEnquete.getInstance().getPlayersConfig().getConfig();
        return config.contains("players." + uuid);
    }

    public ItemStack getSkull(String value) {
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
        gameProfile.getProperties().put("textures", new Property("textures", value));

        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        Field profileField = null;
        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, gameProfile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException exception) {
            exception.printStackTrace();
        }
        skull.setItemMeta(skullMeta);

        return skull;
    }
}
