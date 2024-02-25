package me.jdomi.chat;

import me.jdomi.chat.api.config.ConfigManager;
import me.jdomi.chat.api.hex.IridiumColorAPI;
import me.jdomi.chat.commands.commands;
import me.jdomi.chat.listeners.run;
import net.essentialsx.api.v2.services.discord.DiscordService;
import net.essentialsx.api.v2.services.discord.MessageType;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import me.jdomi.chat.api.metrics.metrics;
import me.jdomi.chat.listeners.announcement;

import static me.jdomi.chat.api.config.ConfigManager.console;


public class main extends JavaPlugin implements Listener
{
    public static main chat;
    public static PluginManager pm = Bukkit.getServer().getPluginManager();
    public static boolean updateAvailable = false;

    public static DiscordService api;

    public static String updateVer;
    public void onEnable()
    {
        // metrics
        int pluginId = 20326;
        metrics metrics = new metrics(this, pluginId);
        chat = this;
        // plugin enable logic

        ConfigManager.DefaultConfig();
        ConfigManager.ReloadConfig();
        if(Bukkit.getPluginManager().isPluginEnabled("Essentials") && Bukkit.getPluginManager().isPluginEnabled("EssentialsDiscord"))
        {
            ConfigManager.SetEssentials();
        }
        run.depend();
        run.enableMSG();
        run.register();
        run.versionUpdate();
        announcement.autoAnnouncments();
        run.updateCheck();
        if(Bukkit.getPluginManager().isPluginEnabled("EssentialsDiscord") && ConfigManager.settings.getBoolean("settings.essentialsDiscordStaffChannel.enabled"))
        {
            staffChat();
        }

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI") && ConfigManager.settings.getString("depend-update").equalsIgnoreCase("true"))
        {

            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main.chat, new Runnable()
            {
                public void run()
                {
                    run.placeHolderUpdate();
                }
            }, 100);
        }
    }
    public static void staffChat()
    {
        api = (DiscordService)Bukkit.getServicesManager().load(DiscordService.class);
        api.registerMessageType(Bukkit.getPluginManager().getPlugin("EssentialsDiscord"), new MessageType("staff"));
    }
}