package me.jdomi.chat.listeners;

import me.jdomi.chat.api.config.ConfigManager;
import me.jdomi.chat.api.hex.IridiumColorAPI;
import me.jdomi.chat.commands.commands;
import me.jdomi.chat.main;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;





public class run
{
    main plugin;

    public static void enableMSG()
    {
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




    public static void depend()
    {
        if (main.chat.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI"))
        {


            ConfigManager.console.sendMessage(IridiumColorAPI.process("&aPlaceholderAPI is installed"));

        }
        else
        {

            ConfigManager.console.sendMessage(IridiumColorAPI.process("&cPlaceholderAPI is not installed it is strongly recommended that you install it!"));
        }
        if (main.chat.getServer().getPluginManager().isPluginEnabled("LuckPerms"))
        {


            ConfigManager.console.sendMessage(IridiumColorAPI.process("&aLuckperms is installed"));

        }
        else
        {

            ConfigManager.console.sendMessage(IridiumColorAPI.process("&cLuckperms is not installed it is strongly recommended to do so to improve server security and to have permissions!"));
        }
    }




    public static void register()
    {
        main.chat.getCommand("ic-reload").setExecutor((CommandExecutor)new commands(main.chat));
        main.chat.getCommand("clearchat").setExecutor((CommandExecutor)new commands(main.chat));
        main.chat.getCommand("cc").setExecutor((CommandExecutor)new commands(main.chat));
        main.chat.getCommand("ic").setExecutor((CommandExecutor)new commands(main.chat));
        main.chat.getCommand("infinitychat").setExecutor((CommandExecutor)new commands(main.chat));
        main.chat.getCommand("infinitychat-reload").setExecutor((CommandExecutor)new commands(main.chat));
        main.chat.getCommand("staff").setExecutor((CommandExecutor)new commands(main.chat));
        main.chat.getCommand("s").setExecutor((CommandExecutor)new commands(main.chat));
        main.pm.registerEvents((Listener)main.chat, (Plugin)main.chat);
        main.pm.registerEvents(new onchat(), (Plugin)main.chat);
        main.pm.registerEvents(new onleave(), (Plugin)main.chat);
        main.pm.registerEvents(new onjoin(), (Plugin)main.chat);
    }



    public static void versionUpdate()
    {
        if (ConfigManager.settings.getString("settings.debugMode").equalsIgnoreCase("true"))

            try
            {

                String plver = String.valueOf(main.chat.getDescription().getVersion().replace(" ", ""));
                String lastplver = String.valueOf(ConfigManager.settings.getString("last-plugin-version"));
                String conplver = String.valueOf(ConfigManager.settings.getString("plugin-version"));
                ConfigManager.settings.set("server-version", main.chat.getServer().getVersion());

                if (!plver.equals(conplver))
                {

                    ConfigManager.settings.set("last-plugin-version", ConfigManager.settings.getString("plugin-version"));
                    ConfigManager.settings.set("plugin-version", main.chat.getDescription().getVersion());
                }

                ConfigManager.saveMessagesGroups();
            }
            catch (Exception ex)
            {

                ConfigManager.console.sendMessage(IridiumColorAPI.process("&4Internal error contact devs"));
            }
    }
}