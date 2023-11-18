package me.jdomi.chat.commands;

import me.jdomi.chat.api.config.ConfigManager;
import me.jdomi.chat.api.hex.IridiumColorAPI;
import me.jdomi.chat.main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;







public class commands implements CommandExecutor
{
    public commands(main plugin) {}
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (sender instanceof org.bukkit.entity.Player)
        {
            if (cmd.getLabel().equalsIgnoreCase("ic-reload") || cmd.getLabel().equalsIgnoreCase("infinitychat-reload"))
            {
                if (sender.hasPermission("ic.reload"))
                {
                    ConfigManager.ReloadConfig(); sender.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.pluginReload")));
                }
                else
                {
                    sender.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.permission")));
                }
            }
            else if (cmd.getLabel().equalsIgnoreCase("cc") || cmd.getLabel().equalsIgnoreCase("clearchat"))
            {
                if (sender.hasPermission("ic.cc"))
                {
                    for (int i = 0; i <= 150; i++)
                    {
                        Bukkit.broadcastMessage("   ");
                    }
                    String replaced1 = ConfigManager.msg.getString("messages.clearChat"), replaced2 = replaced1.replace("%player_name%", sender.getName());
                    Bukkit.broadcastMessage(IridiumColorAPI.process(replaced2));

                }
                else
                {
                    sender.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.settings.getString("plugin-prefix")));
                }

            }
            else if (cmd.getLabel().equalsIgnoreCase("ic") || cmd.getLabel().equalsIgnoreCase("infinitychat"))
            {
                PluginDescriptionFile pdf = main.chat.getDescription();

                sender.sendMessage(" ");
                sender.sendMessage(" ");
                sender.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>---------------------InfinityChat---------------------</GRADIENT:a4fc00>"));
                sender.sendMessage(" ");
                sender.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>Author: Jdomi</GRADIENT:a4fc00>"));
                sender.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>Version: " + pdf.getVersion() + "</GRADIENT:a4fc00>"));
                sender.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>Link: https://modrinth.com/plugin/infinitychat/version/latest</GRADIENT:a4fc00>"));
                sender.sendMessage(" ");
                sender.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>---------------------InfinityChat---------------------</GRADIENT:a4fc00>"));
                sender.sendMessage(" ");
                sender.sendMessage(" ");
            }

        }
        else if (cmd.getLabel().equalsIgnoreCase("ic-reload") || cmd.getLabel().equalsIgnoreCase("infinitychat-reload"))
        {
            ConfigManager.ReloadConfig();
            ConfigManager.ReloadConfig(); sender.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.pluginReload")));
        }

        else if (cmd.getLabel().equalsIgnoreCase("cc") || cmd.getLabel().equalsIgnoreCase("clearchat"))
        {
            sender.sendMessage(IridiumColorAPI.process("&4You must be a player to do this"));
        }

        else if (cmd.getLabel().equalsIgnoreCase("ic") || cmd.getLabel().equalsIgnoreCase("infinitychat"))
        {
            PluginDescriptionFile pdf = main.chat.getDescription();
            sender.sendMessage(" "); sender.sendMessage(" ");
            sender.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>-------------------------InfinityChat-------------------------</GRADIENT:a4fc00>"));
            sender.sendMessage(" ");
            sender.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>Author: Jdomi</GRADIENT:a4fc00>"));
            sender.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>Version: " + pdf.getVersion() + "</GRADIENT:a4fc00>"));
            sender.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>Link: https://modrinth.com/plugin/infinitychat/version/latest</GRADIENT:a4fc00>"));
            sender.sendMessage(" ");
            sender.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>-------------------------InfinityChat-------------------------</GRADIENT:a4fc00>"));
            sender.sendMessage(" ");
            sender.sendMessage(" "); }


        return false;
    }
}