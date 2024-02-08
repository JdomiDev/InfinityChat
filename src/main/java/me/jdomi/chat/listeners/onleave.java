package me.jdomi.chat.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import me.jdomi.chat.api.config.ConfigManager;
import me.jdomi.chat.api.hex.IridiumColorAPI;
import me.jdomi.chat.main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import static me.jdomi.chat.api.config.ConfigManager.console;

public class onleave implements Listener
{
    main plugin;

    @EventHandler
    public void onquit(PlayerQuitEvent e)
    {
        // format leave?
        if (ConfigManager.settings.getBoolean("settings.leaveFormat"))
        {
            if (main.chat.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI"))
            {

                e.setQuitMessage("");

                Bukkit.broadcastMessage(IridiumColorAPI.process(PlaceholderAPI.setPlaceholders(e.getPlayer(), ConfigManager.msg.getString("messages.leaveFormat"))));

            }
            else
            {

                e.setQuitMessage("");
                Bukkit.broadcastMessage(IridiumColorAPI.process(ConfigManager.msg.getString("messages.leaveFormat").replace("%player_name%", e.getPlayer().getDisplayName())));
            }
        }
        // sound no global
        if(!ConfigManager.settings.getBoolean("settings.leaveSound.global"))
        {
            try
            {
                int cooldownSec = ConfigManager.settings.getInt("settings.leaveSound.delay") * 20;

                (new BukkitRunnable()
                {
                    public void run()
                    {
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.valueOf(ConfigManager.settings.getString("settings.leaveSound.sound").toUpperCase()), 1.0F, 1.0F);
                    }
                }).runTaskLater((Plugin)main.chat, cooldownSec+10);
            }
            catch (Exception ex)
            {
                console.sendMessage(IridiumColorAPI.process("&4Error with sound"));
            }
        }
        // sound global
        else if(ConfigManager.settings.getBoolean("settings.leaveSound.global"))
        {
            try
            {
                int cooldownSec = ConfigManager.settings.getInt("settings.leaveSound.delay") * 20;

                (new BukkitRunnable()
                {
                    public void run()
                    {
                        for(Player player : Bukkit.getServer().getOnlinePlayers())
                        {
                            if(!(e.getPlayer().getDisplayName().equals(player.getDisplayName())))
                            {
                                player.playSound(player.getLocation(), Sound.valueOf(ConfigManager.settings.getString("settings.leaveSound.sound").toUpperCase()), 1.0F, 1.0F);
                            }
                        }
                    }

                }).runTaskLater((Plugin)main.chat, cooldownSec);
            }
            catch (Exception ex)
            {
                console.sendMessage(IridiumColorAPI.process("&4Error with sound"));
            }
        }
    }
}