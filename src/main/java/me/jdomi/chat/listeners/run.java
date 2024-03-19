package me.jdomi.chat.listeners;

import me.jdomi.chat.api.config.configManager;
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


    public static int i = 50;

    public static void enableMSG() {
        configManager.console.sendMessage("   ");
        configManager.console.sendMessage("   ");
        configManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>╭━━╮╱╱╭━╮╱╱╱╱╭╮╱╱╱╱╭━━━┳╮╱╱╱╱╭╮</GRADIENT:a4fc00>"));
        configManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>╰┫┣╯╱╱┃╭╯╱╱╱╭╯╰╮╱╱╱┃╭━╮┃┃╱╱╱╭╯╰╮</GRADIENT:a4fc00>"));
        configManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>╱┃┃╭━┳╯╰┳┳━╮┣╮╭╋╮╱╭┫┃╱╰┫╰━┳━┻╮╭╯</GRADIENT:a4fc00>"));
        configManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>╱┃┃┃╭╋╮╭╋┫╭╮╋┫┃┃┃╱┃┃┃╱╭┫╭╮┃╭╮┃┃</GRADIENT:a4fc00>"));
        configManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>╭┫┣┫┃┃┃┃┃┃┃┃┃┃╰┫╰━╯┃╰━╯┃┃┃┃╭╮┃╰╮</GRADIENT:a4fc00>"));
        configManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>╰━━┻╯╰┻╯╰┻╯╰┻┻━┻━╮╭┻━━━┻╯╰┻╯╰┻━╯</GRADIENT:a4fc00>"));
        configManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╭━╯┃</GRADIENT:a4fc00>"));
        configManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╰━━╯</GRADIENT:a4fc00>"));
        configManager.console.sendMessage("   ");
        configManager.console.sendMessage("   ");
        configManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>Infinity Chat Enabled</GRADIENT:a4fc00>"));
        configManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>Version: " + main.chat.getDescription().getVersion() + "</GRADIENT:a4fc00>"));
        configManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>Made with <3 by Jdomi</GRADIENT:a4fc00>"));
        configManager.console.sendMessage("   ");
        configManager.console.sendMessage("   ");
        configManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>https://modrinth.com/plugin/infinitychat</GRADIENT:a4fc00>"));
        configManager.console.sendMessage("   ");
        configManager.console.sendMessage("   ");
    }


    public static void depend() {
        if (main.chat.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {


            configManager.console.sendMessage(IridiumColorAPI.process("&aPlaceholderAPI is installed"));

        } else {

            configManager.console.sendMessage(IridiumColorAPI.process("&cPlaceholderAPI is not installed it is strongly recommended that you install it!"));
        }
        if (main.chat.getServer().getPluginManager().isPluginEnabled("LuckPerms")) {


            configManager.console.sendMessage(IridiumColorAPI.process("&aLuckperms is installed"));

        } else {

            configManager.console.sendMessage(IridiumColorAPI.process("&cLuckperms is not installed it is strongly recommended to do so to improve server security and to have permissions!"));
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
        main.pm.registerEvents(new onChat(), (Plugin) main.chat);
        main.pm.registerEvents(new onLeave(), (Plugin) main.chat);
        main.pm.registerEvents(new onJoin(), (Plugin) main.chat);
    }

    // this updates config from old plugin version
    public static void versionUpdate()
    {
        //chat mention
        if(!configManager.settings.isSet("settings.chatMention.enabled"))
        {
            configManager.settings.set("settings.chatMention.enabled", true);
            configManager.settings.set("settings.chatMention.sendMessage", true);
            configManager.settings.set("settings.chatMention.delay", 0);
            configManager.settings.set("settings.chatMention.sound", "ENTITY_EXPERIENCE_ORB_PICKUP");
            configManager.settings.set("settings.chatMention.onlyPrefix.enabled", true);
            configManager.settings.set("settings.chatMention.onlyPrefix.prefix", "@");
        }
        if(!configManager.msg.isSet("messages.mentionedPlayer"))
        {
            configManager.msg.set("messages.mentionedPlayer","&aYou have been mentioned by &f%player%&a!");
        }
        //anc
        if(!configManager.msg.isSet("messages.announceArgs"))
        {
            configManager.msg.set("messages.announceArgs", "&cUsage: /announce <message here>");
        }
        //um
        if(!configManager.msg.isSet("messages.unmuteArgs"))
        {
            configManager.msg.set("messages.unmuteArgs", "&cUsage: /unmute <username here>");
        }
        //mute
        if(!configManager.msg.isSet("messages.muteArgs"))
        {
            configManager.msg.set("messages.muteArgs", "&cUsage: /mute <username here>");
        }
        //local
        if(!configManager.msg.isSet("messages.localChatArgs"))
        {
            configManager.msg.set("messages.localChatArgs", "&cUsage: /local <message here>");
        }
        // anti shout
        if(!configManager.settings.isSet("settings.antiCapsLockSpam"))
        {
            configManager.settings.set("settings.antiCapsLockSpam", true);
        }
        // anti shout %
        if(!configManager.settings.isSet("settings.antiCapsLockSpamPercentage"))
        {
            configManager.settings.set("settings.antiCapsLockSpamPercentage", 100);
        }
        // depend update
        if(!configManager.settings.isSet("depend-update"))
        {
            configManager.settings.set("depend-update", true);
        }
        // mute scsful msg
        if(!configManager.msg.isSet("messages.mute"))
        {
            configManager.msg.set("messages.mute", "&aYou have muted %player% successfully");
        }
        // cant type in chat
        if(!configManager.msg.isSet("messages.playerIsMuted"))
        {
            configManager.msg.set("messages.playerIsMuted", "&cYou cannot type in chat because you are muted!");
        }
        // unmute scsful msg
        if(!configManager.msg.isSet("messages.unmute"))
        {
            configManager.msg.set("messages.unmute", "&aYou have unmuted %player% successfully");
        }
        // already unmute
        if(!configManager.msg.isSet("messages.alreadyUnmute"))
        {
            configManager.msg.set("messages.alreadyUnmute", "&cPlayer %player% is not muted!");
        }
        // already muted
        if(!configManager.msg.isSet("messages.alreadyMute"))
        {
            configManager.msg.set("messages.alreadyMute", "&cPlayer %player% is muted already!");
        }
        // global join sound
        if(!configManager.settings.isSet("settings.joinSound.global"))
        {
            configManager.settings.set("settings.joinSound.global", true);
        }
        // announce format
        if(!configManager.settings.isSet("settings.announceFormat"))
        {
            configManager.settings.set("settings.announceFormat", "<GRADIENT:e34034>[ANNOUNCEMENT]</GRADIENT:e0af26><GRADIENT:b2de43>%message%</GRADIENT:49d1cf>");
        }
        // leave sound
        if(!configManager.settings.isSet("settings.leaveSound.enabled"))
        {
            configManager.settings.set("settings.leaveSound.enabled", true);
            configManager.settings.set("settings.leaveSound.global", true);
            configManager.settings.set("settings.leaveSound.sound", "ENTITY_VILLAGER_HURT");
            configManager.settings.set("settings.leaveSound.delay", 3);
        }
        // auto announcements
        if(!configManager.settings.isSet("settings.autoAnnouncements.enabled"))
        {
            ArrayList<String> List = new ArrayList<String>();
            List.add("&7%prefix%&7Download <RAINBOW1>InfintyChat</RAINBOW>&7 at &7https://modrinth.com/plugin/infinitychat");
            List.add("&7%prefix%&7Thanks for using <RAINBOW1>InfintyChat</RAINBOW>");
            List.add("&7%prefix%&7This server is running <RAINBOW1>InfintyChat</RAINBOW>");

            configManager.settings.set("settings.autoAnnouncements.enabled", true);
            configManager.settings.set("settings.autoAnnouncements.random", false);
            configManager.settings.set("settings.autoAnnouncements.interval", 120);
            configManager.settings.set("settings.autoAnnouncements.announcmentLines", List);
        }
        // essentials discord stuff
        if(!configManager.settings.isSet("settings.essentialsDiscordStaffChannel.enabled"))
        {
            configManager.settings.set("settings.essentialsDiscordStaffChannel.enabled", false);
        }
        // fix chat cooldown
        if(configManager.settings.isSet("settings.chatCooldown"))
        {
            Boolean newVal = configManager.settings.getBoolean("settings.chatCooldown");
            configManager.settings.set("settings.chatCooldown", null);
            configManager.settings.set("settings.moderation.chatCooldown.enabled", newVal);
        }
        // fix chat cooldowntime
        if(configManager.settings.isSet("settings.chatCooldownTime"))
        {
            int newVal = configManager.settings.getInt("settings.chatCooldownTime");
            configManager.settings.set("settings.chatCooldownTime", null);
            configManager.settings.set("settings.moderation.chatCooldown.cooldownTime", newVal);
        }

        // fix antishout
        if(configManager.settings.isSet("settings.antiCapsLockSpam"))
        {
            Boolean newVal = configManager.settings.getBoolean("settings.antiCapsLockSpam");
            configManager.settings.set("settings.antiCapsLockSpam", null);
            configManager.settings.set("settings.moderation.antiCapsLockSpam.enabled", newVal);
        }

        // fix chat antishout%
        if(configManager.settings.isSet("settings.antiCapsLockSpamPercentage"))
        {
            int newVal = configManager.settings.getInt("settings.antiCapsLockSpamPercentage");
            configManager.settings.set("settings.antiCapsLockSpamPercentage", null);
            configManager.settings.set("settings.moderation.antiCapsLockSpam.upperCasePercentage", newVal);
        }

        // fix antiSwear
        if(configManager.settings.isSet("settings.antiSwear"))
        {
            Boolean newVal = configManager.settings.getBoolean("settings.antiSwear");
            configManager.settings.set("settings.antiSwear", null);
            configManager.settings.set("settings.moderation.antiSwear", newVal);
        }

        // fix chatRepeat
        if(configManager.settings.isSet("settings.antiChatRepeat"))
        {
            Boolean newVal = configManager.settings.getBoolean("settings.antiChatRepeat");
            configManager.settings.set("settings.antiChatRepeat", null);
            configManager.settings.set("settings.moderation.antiChatRepeat", newVal);
        }

        // fix motdEnabled
        if(configManager.settings.isSet("settings.motd") && configManager.settings.isBoolean("settings.motd"))
        {
        }

        // fix motdDelayTime
        if(configManager.settings.isSet("settings.motd") && configManager.settings.isBoolean("settings.motd"))
        {
            int newVal = configManager.settings.getInt("settings.motdDelayTime");
            configManager.settings.set("settings.motdDelayTime", null);
            configManager.settings.set("settings.motd.delay", newVal);
        }
        // fix chatFormat
        if(configManager.settings.isSet("settings.chatFormatting"))
        {
            boolean newVal = configManager.settings.getBoolean("settings.chatFormatting");
            configManager.settings.set("settings.chatFormatting", null);
            configManager.settings.set("settings.formatting.chatFormatting", newVal);
        }
        // fix hexChat
        if(configManager.settings.isSet("settings.hexChat"))
        {
            configManager.settings.set("settings.hexChat", null);
        }
        // fix joinformat
        if(configManager.settings.isSet("settings.joinFormat"))
        {
            boolean newVal = configManager.settings.getBoolean("settings.joinFormat");
            configManager.settings.set("settings.joinFormat", null);
            configManager.settings.set("settings.formatting.joinFormat", newVal);
        }
        // fix leaveFormat
        if(configManager.settings.isSet("settings.leaveFormat"))
        {
            boolean newVal = configManager.settings.getBoolean("settings.leaveFormat");
            configManager.settings.set("settings.leaveFormat", null);
            configManager.settings.set("settings.formatting.leaveFormat", newVal);
        }

        // fix annFormat
        if(configManager.settings.isSet("settings.announceFormat"))
        {
            String newVal = configManager.settings.getString("settings.announceFormat");
            configManager.settings.set("settings.announceFormat", null);
            configManager.settings.set("settings.formatting.announceFormat", newVal);
        }
        // fix chat format
        if(configManager.settings.isSet("settings.chatFormat"))
        {
            String newVal = configManager.settings.getString("settings.chatFormat");
            configManager.settings.set("settings.chatFormat", null);
            configManager.settings.set("settings.formatting.chatFormat", newVal);
        }
        // fix chat noformat
        if(configManager.settings.isSet("settings.noGroupChatFormat"))
        {
            String newVal = configManager.settings.getString("settings.noGroupChatFormat");
            configManager.settings.set("settings.noGroupChatFormat", null);
            configManager.settings.set("settings.formatting.noGroupChatFormat", newVal);
        }
        // fix chat staffnoformat
        if(configManager.settings.isSet("settings.noGroupStaffChatFormat"))
        {
            String newVal = configManager.settings.getString("settings.noGroupStaffChatFormat");
            configManager.settings.set("settings.noGroupStaffChatFormat", null);
            configManager.settings.set("settings.formatting.noGroupStaffChatFormat", newVal);
        }
        // fix chat staffformat
        if(configManager.settings.isSet("settings.staffChatFormat"))
        {
            String newVal = configManager.settings.getString("settings.staffChatFormat");
            configManager.settings.set("settings.staffChatFormat", null);
            configManager.settings.set("settings.formatting.staffChatFormat", newVal);
        }

        configManager.saveMessagesGroups();
    }
    // check and tells admins that there is a new plugin version
    public static void updateCheck()
    {
        if (configManager.settings.getString("settings.updateChecking").equalsIgnoreCase("true")) {
            try
            {

                URL url = new URL("https://raw.githubusercontent.com/JdomiDev/InfinityChat/main/version.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                String line = reader.readLine();
                reader.close();
                main.updateVer = line;


                if (Float.parseFloat(main.chat.getDescription().getVersion().replace(".", "")) < (Float.parseFloat(line.replace(".", ""))))
                {
                    configManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>InfinityChat</GRADIENT:a4fc00>&7 » new version available!    https://modrinth.com/plugin/infinitychat/version/latest"));
                    main.updateAvailable = true;
                }
                else
                {
                    configManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>InfinityChat</GRADIENT:a4fc00>&7 » up to date!"));
                }
            }
            catch (Exception ex)
            {
                configManager.console.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>InfinityChat</GRADIENT:a4fc00>&7 » &4up to date!"));
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