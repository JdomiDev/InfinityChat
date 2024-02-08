package me.jdomi.chat.listeners;

import me.jdomi.chat.api.config.ConfigManager;
import me.jdomi.chat.api.hex.IridiumColorAPI;
import me.jdomi.chat.main;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import java.util.List;
import java.util.Random;

import static me.jdomi.chat.api.config.ConfigManager.console;

public class announcement
{
    public static int interval;
    public static int random;
    public static int currentMessage = 0;

    // announcement
    public static void autoAnnouncments()
    {
        // check if announcement enabled
        if(ConfigManager.settings.getBoolean("settings.autoAnnouncements.enabled"))
        {
            final List<String> announcmentLines = ConfigManager.settings.getStringList("settings.autoAnnouncements.announcmentLines");

            interval = ConfigManager.settings.getInt("settings.autoAnnouncements.interval")*20;

            // random
            if(ConfigManager.settings.getBoolean("settings.autoAnnouncements.random"))
            {
                Bukkit.getScheduler().scheduleSyncRepeatingTask(main.chat , new Runnable()
                {
                    public void run()
                    {
                        try
                        {
                            random = new Random().nextInt(announcmentLines.size());
                            String message = String.valueOf(announcmentLines.get(random));

                            message = message.replace("%online_players%", String.valueOf(main.chat.getServer().getOnlinePlayers().size()));
                            message = message.replace("%server_max_players%", String.valueOf(main.chat.getServer().getMaxPlayers()));
                            message = message.replace("%statistic_time_played:hours%", String.valueOf(Statistic.PLAY_ONE_MINUTE));
                            message = message.replace("%prefix%",ConfigManager.settings.getString("plugin-prefix"));
                            Bukkit.broadcastMessage(IridiumColorAPI.process(message));
                        }
                        catch (Exception ex)
                        {
                            Bukkit.broadcastMessage(IridiumColorAPI.process("&4Error with announcmentLines config"));
                        }
                    }
                }, 0, interval);
            }
            // non random
            else
            {
                Bukkit.getScheduler().scheduleSyncRepeatingTask(main.chat , new Runnable()
                {
                    public void run()
                    {
                        try
                        {
                            // resets current message
                            if(currentMessage == announcmentLines.size()-1)
                            {
                                currentMessage = 0;
                            }
                            else
                            {
                                currentMessage++;
                            }
                            String message = String.valueOf(announcmentLines.get(currentMessage));

                            message = message.replace("%online_players%", String.valueOf(main.chat.getServer().getOnlinePlayers().size()));
                            message = message.replace("%server_max_players%", String.valueOf(main.chat.getServer().getMaxPlayers()));
                            message = message.replace("%statistic_time_played:hours%", String.valueOf(Statistic.PLAY_ONE_MINUTE));
                            message = message.replace("%prefix%",ConfigManager.settings.getString("plugin-prefix"));
                            Bukkit.broadcastMessage(IridiumColorAPI.process(message));
                        }
                        catch (Exception ex)
                        {
                            console.sendMessage(IridiumColorAPI.process("&4Error with config"));
                        }
                    }
                }, 0, interval);
            }
        }
    }
}
