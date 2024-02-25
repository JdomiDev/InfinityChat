package me.jdomi.chat.listeners;

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
        main.chat.getCommand("mute").setExecutor((CommandExecutor) new commands(main.chat));
        main.chat.getCommand("m").setExecutor((CommandExecutor) new commands(main.chat));
        main.chat.getCommand("unmute").setExecutor((CommandExecutor) new commands(main.chat));
        main.chat.getCommand("um").setExecutor((CommandExecutor) new commands(main.chat));
        main.pm.registerEvents((Listener) main.chat, (Plugin) main.chat);
        main.pm.registerEvents(new onchat(), (Plugin) main.chat);
        main.pm.registerEvents(new onleave(), (Plugin) main.chat);
        main.pm.registerEvents(new onjoin(), (Plugin) main.chat);
    }

    // this updates config from old plugin version
    public static void versionUpdate()
    {
        //anc
        if(!ConfigManager.msg.isSet("messages.announceArgs"))
        {
            ConfigManager.msg.set("messages.announceArgs", "&cUsage: /announce <message here>");
        }
        //um
        if(!ConfigManager.msg.isSet("messages.unmuteArgs"))
        {
            ConfigManager.msg.set("messages.unmuteArgs", "&cUsage: /unmute <username here>");
        }
        //mute
        if(!ConfigManager.msg.isSet("messages.muteArgs"))
        {
            ConfigManager.msg.set("messages.muteArgs", "&cUsage: /mute <username here>");
        }
        //local
        if(!ConfigManager.msg.isSet("messages.localChatArgs"))
        {
            ConfigManager.msg.set("messages.localChatArgs", "&cUsage: /local <message here>");
        }
        // anti shout
        if(!ConfigManager.settings.isSet("settings.antiCapsLockSpam"))
        {
            ConfigManager.settings.set("settings.antiCapsLockSpam", true);
        }
        // anti shout %
        if(!ConfigManager.settings.isSet("settings.antiCapsLockSpamPercentage"))
        {
            ConfigManager.settings.set("settings.antiCapsLockSpamPercentage", 100);
        }
        // depend update
        if(!ConfigManager.settings.isSet("depend-update"))
        {
            ConfigManager.settings.set("depend-update", true);
        }
        // mute scsful msg
        if(!ConfigManager.msg.isSet("messages.mute"))
        {
            ConfigManager.msg.set("messages.mute", "&aYou have muted %player% successfully");
        }
        // cant type in chat
        if(!ConfigManager.msg.isSet("messages.playerIsMuted"))
        {
            ConfigManager.msg.set("messages.playerIsMuted", "&cYou cannot type in chat because you are muted!");
        }
        // unmute scsful msg
        if(!ConfigManager.msg.isSet("messages.unmute"))
        {
            ConfigManager.msg.set("messages.unmute", "&aYou have unmuted %player% successfully");
        }
        // already unmute
        if(!ConfigManager.msg.isSet("messages.alreadyUnmute"))
        {
            ConfigManager.msg.set("messages.alreadyUnmute", "&cPlayer %player% is not muted!");
        }
        // already muted
        if(!ConfigManager.msg.isSet("messages.alreadyMute"))
        {
            ConfigManager.msg.set("messages.alreadyMute", "&cPlayer %player% is muted already!");
        }
        // global join sound
        if(!ConfigManager.settings.isSet("settings.joinSound.global"))
        {
            ConfigManager.settings.set("settings.joinSound.global", true);
        }
        // announce format
        if(!ConfigManager.settings.isSet("settings.announceFormat"))
        {
            ConfigManager.settings.set("settings.announceFormat", "<GRADIENT:e34034>[ANNOUNCEMENT]</GRADIENT:e0af26><GRADIENT:b2de43>%message%</GRADIENT:49d1cf>");
        }
        // leave sound
        if(!ConfigManager.settings.isSet("settings.leaveSound.enabled"))
        {
            ConfigManager.settings.set("settings.leaveSound.enabled", true);
            ConfigManager.settings.set("settings.leaveSound.global", true);
            ConfigManager.settings.set("settings.leaveSound.sound", "ENTITY_VILLAGER_HURT");
            ConfigManager.settings.set("settings.leaveSound.delay", 3);
        }
        // auto announcements
        if(!ConfigManager.settings.isSet("settings.autoAnnouncements.enabled"))
        {
            ArrayList<String> List = new ArrayList<String>();
            List.add("&7%prefix%&7Download <RAINBOW1>InfintyChat</RAINBOW>&7 at &7https://modrinth.com/plugin/infinitychat");
            List.add("&7%prefix%&7Thanks for using <RAINBOW1>InfintyChat</RAINBOW>");
            List.add("&7%prefix%&7This server is running <RAINBOW1>InfintyChat</RAINBOW>");

            ConfigManager.settings.set("settings.autoAnnouncements.enabled", true);
            ConfigManager.settings.set("settings.autoAnnouncements.random", false);
            ConfigManager.settings.set("settings.autoAnnouncements.interval", 120);
            ConfigManager.settings.set("settings.autoAnnouncements.announcmentLines", List);
        }
        // essentials discord stuff
        if(!ConfigManager.settings.isSet("settings.essentialsDiscordStaffChannel.enabled"))
        {
            ConfigManager.settings.set("settings.essentialsDiscordStaffChannel.enabled", false);
        }

        ConfigManager.saveMessagesGroups();
    }
    // check and tells admins that there is a new plugin version
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
    // download needed papi extensions
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