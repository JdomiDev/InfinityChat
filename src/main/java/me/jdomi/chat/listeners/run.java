package me.jdomi.chat.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import me.jdomi.chat.api.config.ConfigManager;
import me.jdomi.chat.api.hex.IridiumColorAPI;
import me.jdomi.chat.commands.commands;
import me.jdomi.chat.main;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;


public class run {
    main plugin;

    public static int i = 50;

    public static void enableMSG() {
        ConfigManager.console.sendMessage("   ");
        ConfigManager.console.sendMessage("   ");
        ConfigManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>╭━━╮╱╱╭━╮╱╱╱╱╭╮╱╱╱╱╭━━━┳╮╱╱╱╱╭╮</GRADIENT:a4fc00>"));
        ConfigManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>╰┫┣╯╱╱┃╭╯╱╱╱╭╯╰╮╱╱╱┃╭━╮┃┃╱╱╱╭╯╰╮</GRADIENT:a4fc00>"));
        ConfigManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>╱┃┃╭━┳╯╰┳┳━╮┣╮╭╋╮╱╭┫┃╱╰┫╰━┳━┻╮╭╯</GRADIENT:a4fc00>"));
        ConfigManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>╱┃┃┃╭╋╮╭╋┫╭╮╋┫┃┃┃╱┃┃┃╱╭┫╭╮┃╭╮┃┃</GRADIENT:a4fc00>"));
        ConfigManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>╭┫┣┫┃┃┃┃┃┃┃┃┃┃╰┫╰━╯┃╰━╯┃┃┃┃╭╮┃╰╮</GRADIENT:a4fc00>"));
        ConfigManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>╰━━┻╯╰┻╯╰┻╯╰┻┻━┻━╮╭┻━━━┻╯╰┻╯╰┻━╯</GRADIENT:a4fc00>"));
        ConfigManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╭━╯┃</GRADIENT:a4fc00>"));
        ConfigManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╰━━╯</GRADIENT:a4fc00>"));
        ConfigManager.console.sendMessage("   ");
        ConfigManager.console.sendMessage("   ");
        ConfigManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>Infinity Chat Enabled</GRADIENT:a4fc00>"));
        ConfigManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>Version: " + main.chat.getDescription().getVersion() + "</GRADIENT:a4fc00>"));
        ConfigManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>Made with <3 by Jdomi</GRADIENT:a4fc00>"));
        ConfigManager.console.sendMessage("   ");
        ConfigManager.console.sendMessage("   ");
        ConfigManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>https://modrinth.com/plugin/infinitychat</GRADIENT:a4fc00>"));
        ConfigManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>https://polymart.org/resource/infinitychat-chat-plugin.1141</GRADIENT:a4fc00>"));
        ConfigManager.console.sendMessage("   ");
        ConfigManager.console.sendMessage("   ");
    }


    public static void depend() {
        if (main.chat.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {


            ConfigManager.console.sendMessage(IridiumColorAPI.process("&aPlaceholderAPI is installed"));

        } else {

            ConfigManager.console.sendMessage(IridiumColorAPI.process("&cPlaceholderAPI is not installed it is strongly recommended that you install it!"));
        }
        if (main.chat.getServer().getPluginManager().isPluginEnabled("LuckPerms")) {


            ConfigManager.console.sendMessage(IridiumColorAPI.process("&aLuckperms is installed"));

        } else {

            ConfigManager.console.sendMessage(IridiumColorAPI.process("&cLuckperms is not installed it is strongly recommended to do so to improve server security and to have permissions!"));
        }
    }


    public static void register()
    {
        main.chat.getCommand("announce").setExecutor((CommandExecutor) new commands(main.chat));
        main.chat.getCommand("anc").setExecutor((CommandExecutor) new commands(main.chat));
        main.chat.getCommand("ic-reload").setExecutor((CommandExecutor) new commands(main.chat));
        main.chat.getCommand("clearchat").setExecutor((CommandExecutor) new commands(main.chat));
        main.chat.getCommand("cc").setExecutor((CommandExecutor) new commands(main.chat));
        main.chat.getCommand("ic").setExecutor((CommandExecutor) new commands(main.chat));
        main.chat.getCommand("infinitychat").setExecutor((CommandExecutor) new commands(main.chat));
        main.chat.getCommand("infinitychat-reload").setExecutor((CommandExecutor) new commands(main.chat));
        main.chat.getCommand("staff").setExecutor((CommandExecutor) new commands(main.chat));
        main.chat.getCommand("s").setExecutor((CommandExecutor) new commands(main.chat));
        main.chat.getCommand("local").setExecutor((CommandExecutor) new commands(main.chat));
        main.chat.getCommand("l").setExecutor((CommandExecutor) new commands(main.chat));
        main.pm.registerEvents((Listener) main.chat, (Plugin) main.chat);
        main.pm.registerEvents(new onchat(), (Plugin) main.chat);
        main.pm.registerEvents(new onleave(), (Plugin) main.chat);
        main.pm.registerEvents(new onjoin(), (Plugin) main.chat);
    }


    public static void versionUpdate()
    {
        if(!ConfigManager.settings.isSet("depend-update"))
        {
            ConfigManager.settings.set("depend-update", true);
        }

        if(!ConfigManager.settings.isSet("settings.joinSound.global"))
        {
            ConfigManager.settings.set("settings.joinSound.global", true);
        }
        if(!ConfigManager.settings.isSet("settings.announceFormat"))
        {
            ConfigManager.settings.set("settings.announceFormat", "<GRADIENT:e34034>[ANNOUNCEMENT]</GRADIENT:e0af26><GRADIENT:b2de43>%message%</GRADIENT:49d1cf>");
        }
        if(!ConfigManager.settings.isSet("settings.leaveSound.enabled"))
        {
            ConfigManager.settings.set("settings.leaveSound.enabled", true);
            ConfigManager.settings.set("settings.leaveSound.global", true);
            ConfigManager.settings.set("settings.leaveSound.sound", "ENTITY_VILLAGER_HURT");
            ConfigManager.settings.set("settings.leaveSound.delay", 3);
        }

        ConfigManager.saveMessagesGroups();
    }

    public static void updateCheck()
    {
        if (ConfigManager.settings.getString("settings.updateChecking").equalsIgnoreCase("true")) {
            try
            {

                URL url = new URL("https://raw.githubusercontent.com/JdomiDev/InfinityChat/main/version.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                String line = reader.readLine();
                reader.close();
                main.updateVer = line;


                if (Float.parseFloat(main.chat.getDescription().getVersion().replace(".", "")) < (Float.parseFloat(line.replace(".", ""))))
                {
                    ConfigManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>InfinityChat</GRADIENT:a4fc00>&7 » new version available!    https://modrinth.com/plugin/infinitychat/version/latest"));
                    main.updateAvailable = true;
                }
                else
                {
                    ConfigManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>InfinityChat</GRADIENT:a4fc00>&7 » up to date!"));
                }
            }
            catch (Exception ex)
            {
                ConfigManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>InfinityChat</GRADIENT:a4fc00>&7 » &4up to date!"));
            }
        }
    }

    public static void placeHolderUpdate()
    {
        List<String> extensions = List.of("Player", "PlayerList", "Server", "Statistic");
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

        for (String row : extensions)
        {
            i = i + 20;
            (new BukkitRunnable()
            {
                public void run()
                {
                    Bukkit.dispatchCommand(console, "papi ecloud download " + row);
                }
            }).runTaskLater((Plugin) main.chat, i);
        }

        (new BukkitRunnable()
        {
            public void run()
            {
                Bukkit.dispatchCommand(console, "papi reload");
            }
        }).runTaskLater((Plugin) main.chat, 20+((extensions.size()*2)*20));

    }
}