package me.jdomi.chat;

import me.clip.placeholderapi.PlaceholderAPI;
import me.jdomi.chat.api.config.ConfigManager;
import me.jdomi.chat.api.hex.IridiumColorAPI;
import me.jdomi.chat.listeners.run;
import net.md_5.bungee.Metrics;
import net.md_5.bungee.chat.SelectorComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import me.jdomi.chat.api.metrics.metrics;

import java.io.BufferedReader;
import java.io.ObjectInputFilter;
import java.net.URL;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Scanner;

public class main extends JavaPlugin implements Listener
{
    public static main chat;
    public static PluginManager pm = Bukkit.getServer().getPluginManager();

    public static boolean updateAvailable = false;

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
        run.depend();
        run.enableMSG();
        run.register();
        run.versionUpdate();

        if(ConfigManager.settings.getString("settings.updateChecking").equalsIgnoreCase("true"))
        {
            try
            {

                URL url = new URL("https://raw.githubusercontent.com/JdomiDev/InfinityChat/main/version.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                String line = reader.readLine();
                reader.close();
                updateVer = line;


                if(Float.parseFloat(main.chat.getDescription().getVersion().replace(".","")) < (Float.parseFloat(line.replace(".",""))))
                {
                    ConfigManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>InfinityChat</GRADIENT:a4fc00>&7 » new version available!    https://modrinth.com/plugin/infinitychat/version/latest"));
                    updateAvailable = true;
                }
                else
                {
                    ConfigManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>InfinityChat</GRADIENT:a4fc00>&7 » up to date!"));
                }
            }
            catch(Exception ex)
            {
                ConfigManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>InfinityChat</GRADIENT:a4fc00>&7 » &4up to date!"));
            }
        }
    }
}