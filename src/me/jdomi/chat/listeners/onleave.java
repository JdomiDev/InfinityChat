package me.jdomi.chat.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import me.jdomi.chat.api.config.ConfigManager;
import me.jdomi.chat.api.hex.IridiumColorAPI;
import me.jdomi.chat.main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;


public class onleave implements Listener
{
    main plugin;

    @EventHandler
    public void onquit(PlayerQuitEvent e)
    {
        if (ConfigManager.settings.getString("settings.leaveFormat").equalsIgnoreCase("true"))
        {
            if (main.chat.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI"))
            {

                e.setQuitMessage("");

                String format = ConfigManager.msg.getString("messages.leaveFormat");
                Bukkit.broadcastMessage(format);
                String replaced = PlaceholderAPI.setPlaceholders(e.getPlayer(), format);

                Bukkit.broadcastMessage(IridiumColorAPI.process(replaced));

            }
            else
            {

                e.setQuitMessage("");

                String format = ConfigManager.msg.getString("messages.leaveFormat");
                String replaced = format.replace("%player_name%", e.getPlayer().getDisplayName());

                Bukkit.broadcastMessage(IridiumColorAPI.process(replaced));
            }
        }
    }
}