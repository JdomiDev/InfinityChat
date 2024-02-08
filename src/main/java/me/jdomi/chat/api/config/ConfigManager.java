package me.jdomi.chat.api.config;

import java.io.File;
import java.io.IOException;

import me.jdomi.chat.main;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;




public class ConfigManager
{

    public static main plugin;
    public static ConsoleCommandSender console = main.chat.getServer().getConsoleSender();

    public static File msgF = new File(main.chat.getDataFolder().getAbsolutePath(), "messages.yml");
    public static FileConfiguration msg = YamlConfiguration.loadConfiguration(msgF);


    public static File groupsF = new File(main.chat.getDataFolder().getAbsolutePath(), "groups.yml");
    public static FileConfiguration groups = YamlConfiguration.loadConfiguration(groupsF);



    public static File wordsF = new File(main.chat.getDataFolder().getAbsolutePath(), "censored-words.yml");
    public static FileConfiguration words = YamlConfiguration.loadConfiguration(wordsF);



    public static File settingsF = new File(main.chat.getDataFolder().getAbsolutePath(), "settings.yml");
    public static FileConfiguration settings = YamlConfiguration.loadConfiguration(settingsF);


    public static void saveMessagesGroups()
    {
        try
        {
            groups.save(groupsF);
            groups = YamlConfiguration.loadConfiguration(groupsF);

            msg.save(msgF);
            msg = YamlConfiguration.loadConfiguration(msgF);

            words.save(wordsF);
            words = YamlConfiguration.loadConfiguration(wordsF);

            settings.save(settingsF);
            settings = YamlConfiguration.loadConfiguration(settingsF);

        }
        catch (IOException iOException)
        {
            console.sendMessage("&4Error Plugin Config Cant Be Saved. The plugin will not work correctly!");
        }
    }




    public static void DefaultConfig()
    {
        if (groupsF == null)
        {
            groupsF = new File(main.chat.getDataFolder(), "groups.yml");
        }
        if (!groupsF.exists())
        {
            main.chat.saveResource("groups.yml", false);
        }


        if (msgF == null)
        {
            msgF = new File(main.chat.getDataFolder(), "messages.yml");
        }
        if (!msgF.exists())
        {
            main.chat.saveResource("messages.yml", false);
        }

        if (wordsF == null)
        {
            wordsF = new File(main.chat.getDataFolder(), "censored-words.yml");
        }
        if (!wordsF.exists())
        {
            main.chat.saveResource("censored-words.yml", false);
        }

        if (settingsF == null)
        {
            settingsF = new File(main.chat.getDataFolder(), "settings.yml");
        }
        if (!settingsF.exists())
        {
            main.chat.saveResource("settings.yml", false);
        }
    }



    public static void ReloadConfig()
    {
        settings = null;
        msg = null;
        words = null;
        groups = null;

        settings = YamlConfiguration.loadConfiguration(settingsF);

        words = YamlConfiguration.loadConfiguration(wordsF);

        msg = YamlConfiguration.loadConfiguration(msgF);

        groups = YamlConfiguration.loadConfiguration(groupsF);
    }
}