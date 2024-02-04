package me.jdomi.chat.listeners;

import me.jdomi.chat.api.config.ConfigManager;
import me.jdomi.chat.main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Random;

import static me.jdomi.chat.api.config.ConfigManager.plugin;
import static org.bukkit.Bukkit.getServer;

public class announcement
{
    public static int interval;
    public static int random;
    public static void autoAnnouncments()
    {
        if(ConfigManager.settings.getBoolean("settings.autoAnnouncements.enabled"))
        {
            final List<String> announcmentLines = ConfigManager.msg.getStringList("settings.autoAnnouncements.announcmentLines");

            interval = ConfigManager.settings.getInt("settings.autoAnnouncements.interval")*20;

            // random
            if(!ConfigManager.settings.getBoolean("settings.autoAnnouncements.random"))
            {
                Bukkit.getScheduler().scheduleSyncRepeatingTask(main.chat , new Runnable()
                {
                    public void run()
                    {
                        //random = new Random().nextInt(announcmentLines.size());
                        Bukkit.broadcastMessage(String.valueOf(announcmentLines.size()));
                    }
                }, 0, 20);
            }
        }
    }
}
