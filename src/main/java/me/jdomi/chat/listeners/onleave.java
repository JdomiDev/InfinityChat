package me.jdomi.chat.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import me.jdomi.chat.api.config.configManager;
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

import static me.jdomi.chat.api.config.configManager.console;

public class onLeave implements Listener
{

    @EventHandler
    public void onquit(PlayerQuitEvent e)
    {
        // format leave?
        if (configManager.settings.getBoolean("settings.formatting.leaveFormat"))
        {
            if (main.chat.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI"))
            {

                e.setQuitMessage("");

                Bukkit.broadcastMessage(IridiumColorAPI.process(PlaceholderAPI.setPlaceholders(e.getPlayer(), configManager.msg.getString("messages.leaveFormat"))));

            }
            else
            {

                e.setQuitMessage("");
                Bukkit.broadcastMessage(IridiumColorAPI.process(configManager.msg.getString("messages.leaveFormat").replace("%player_name%", e.getPlayer().getDisplayName())));
            }
        }
        // sound no global
        if(!configManager.settings.getBoolean("settings.leaveSound.global"))
        {
            try
            {
                int cooldownSec = configManager.settings.getInt("settings.leaveSound.delay") * 20;

                (new BukkitRunnable()
                {
                    public void run()
                    {
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.valueOf(configManager.settings.getString("settings.leaveSound.sound").toUpperCase()), 100.0F, 1.0F);
                    }
                }).runTaskLater((Plugin)main.chat, cooldownSec+10);
            }
            catch (Exception ex)
            {
                console.sendMessage(IridiumColorAPI.process("&4Error with sound"));
            }
        }
        // sound global
        else if(configManager.settings.getBoolean("settings.leaveSound.global"))
        {
            try
            {
                int cooldownSec = configManager.settings.getInt("settings.leaveSound.delay") * 20;

                (new BukkitRunnable()
                {
                    public void run()
                    {
                        for(Player player : Bukkit.getServer().getOnlinePlayers())
                        {
                            if(!(e.getPlayer().getDisplayName().equals(player.getDisplayName())))
                            {
                                player.playSound(player.getLocation(), Sound.valueOf(configManager.settings.getString("settings.leaveSound.sound").toUpperCase()), 100.0F, 1.0F);
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