package me.jdomi.chat.api.config;

import java.io.File;
import java.io.IOException;

import me.jdomi.chat.main;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;




public class configManager
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

    public static File muteF = new File(main.chat.getDataFolder().getAbsolutePath(), "mute.yml");
    public static FileConfiguration mute = YamlConfiguration.loadConfiguration(settingsF);

    public static FileConfiguration essentials;
    public static File essentialsF;



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


            mute.save(muteF);
            mute = YamlConfiguration.loadConfiguration(muteF);


        }
        catch (IOException iOException)
        {
            console.sendMessage("&4Error Plugin Config Cant Be Saved. The plugin will not work correctly!");
        }

    }
    public static void saveMute()
    {
        try
        {
            mute.save(muteF);
            mute = YamlConfiguration.loadConfiguration(muteF);


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
        if (muteF == null)
        {
            muteF = new File(main.chat.getDataFolder(), "mute.yml");
        }
        if (!muteF.exists())
        {
            main.chat.saveResource("mute.yml", false);
        }
    }



    public static void ReloadConfig()
    {
        settings = null;
        msg = null;
        words = null;
        groups = null;
        mute = null;

        settings = YamlConfiguration.loadConfiguration(settingsF);

        words = YamlConfiguration.loadConfiguration(wordsF);

        msg = YamlConfiguration.loadConfiguration(msgF);

        groups = YamlConfiguration.loadConfiguration(groupsF);

        mute = YamlConfiguration.loadConfiguration(muteF);
    }

    public static void SetEssentials()
    {
        if(Bukkit.getPluginManager().getPlugin("EssentialsDiscord").isEnabled() && settings.getBoolean("settings.essentialsDiscordStaffChannel.enabled"))
        {
            try
            {
                essentialsF = new File(Bukkit.getPluginManager().getPlugin("EssentialsDiscord").getDataFolder().getAbsolutePath(), "config.yml");
                essentials = YamlConfiguration.loadConfiguration(essentialsF);
                if(!essentials.isSet("channels.staff-channel"))
                {
                    essentials.set("channels.staff-channel", 0);
                }
                if(!essentials.isSet("message-types.staff"))
                {
                    essentials.set("message-types.staff", "staff-channel");
                }
                essentials.save(essentialsF);
            }
            catch(Exception ex)
            {

            }
        }
    }
}