package me.jdomi.chat.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import me.jdomi.chat.api.config.ConfigManager;
import me.jdomi.chat.api.hex.IridiumColorAPI;
import me.jdomi.chat.main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.Console;
import java.util.Set;


public class commands implements CommandExecutor
{
    public commands(main plugin) {}
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (sender instanceof org.bukkit.entity.Player)
        {
            // player
            // reload config
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
            // clear chat
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
                    sender.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.permission")));
                }

            }
            // infinity
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
            else if (cmd.getLabel().equalsIgnoreCase("staff") || cmd.getLabel().equalsIgnoreCase("s"))
            {
                if(sender.hasPermission("ic.staff") || sender.isOp())
                {
                    if(args.length == 0)
                    {
                        sender.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.staffChatArgs")));
                    }
                    else
                    {
                        String msg = "";
                        for (int i = 0; i < args.length ; i++)
                        {
                            msg = msg + " " + args[i];
                        }

                        Set<String> groupsList = ConfigManager.groups.getConfigurationSection("groups").getKeys(false);
                        int n1 = groupsList.size();
                        int n2 = 0;

                        for (String groups : groupsList)
                        {
                            n2++;
                            Permission perm = new Permission(ConfigManager.settings.getString("settings.permPrefix") + "." + groups, PermissionDefault.FALSE);
                            // groups
                            if (sender.hasPermission(perm))
                            {
                                //papi
                                if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
                                {
                                    String format = ConfigManager.settings.getString("settings.staffChatFormat");
                                    format= PlaceholderAPI.setPlaceholders(((Player) sender).getPlayer(), format);
                                    format = format.replace("%prefix%", ConfigManager.groups.getString("groups." + groups));
                                    format = format.replace("%player_name%", ((Player) sender).getPlayer().getName());
                                    format = format.replace("%arrow%", "»");
                                    format = format.replace("%message%", msg);
                                    for(Player player : Bukkit.getServer().getOnlinePlayers())
                                    {
                                        if(player.hasPermission("ic.staff") || player.isOp())
                                        {
                                            player.sendMessage(IridiumColorAPI.process(format));
                                        }
                                    }
                                    break;
                                }
                                //nopapi
                                else
                                {
                                    String format = ConfigManager.settings.getString("settings.staffChatFormat");
                                    format = format.replace("%prefix%", ConfigManager.groups.getString("groups." + groups));
                                    format = format.replace("%player_name%", ((Player) sender).getPlayer().getName());
                                    format = format.replace("%arrow%", "»");
                                    format = format.replace("%message%", msg);
                                    for(Player player : Bukkit.getServer().getOnlinePlayers())
                                    {
                                        if(player.hasPermission("ic.staff") || player.isOp())
                                        {
                                            player.sendMessage(IridiumColorAPI.process(format));
                                        }
                                    }
                                    break;
                                }
                            }
                            // no groups
                            else if (n2 == n1)
                            {
                                // papi
                                if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
                                {
                                    String format = PlaceholderAPI.setPlaceholders(((Player) sender).getPlayer(), ConfigManager.settings.getString("settings.noGroupStaffChatFormat"));
                                    format = format.replace("%player_name%", sender.getName());
                                    format = format.replace("%arrow%", "»");
                                    format = format.replace("%message%", msg);

                                    for(Player player : Bukkit.getServer().getOnlinePlayers())
                                    {
                                        if(player.hasPermission("ic.staff") || player.isOp())
                                        {
                                            player.sendMessage(IridiumColorAPI.process(format));
                                        }
                                    }
                                    break;
                                }
                                // nopapi
                                else
                                {

                                    String format = ConfigManager.settings.getString("settings.noGroupStaffChatFormat");
                                    format = format.replace("%player_name%", sender.getName());
                                    format = format.replace("%arrow%", "»");
                                    format = format.replace("%message%", msg);

                                    for(Player player : Bukkit.getServer().getOnlinePlayers())
                                    {
                                        if(player.hasPermission("ic.staff") || player.isOp())
                                        {
                                            player.sendMessage(IridiumColorAPI.process(format));
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
                else
                {
                    sender.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.permission")));
                }
            }
            // local

            else if(cmd.getLabel().equalsIgnoreCase("l") || cmd.getLabel().equalsIgnoreCase("local"))
            {
                if(ConfigManager.settings.getString("settings.localChat.enabled").equalsIgnoreCase("true"))
                {
                    float distance = Float.valueOf(ConfigManager.settings.getString("settings.localChat.localChatDistance"));


                    String msg = "";
                    for (int i = 0; i < args.length ; i++)
                    {
                        msg = msg + " " + args[i];
                    }

                    for(Player player : Bukkit.getServer().getOnlinePlayers())
                    {

                        if(distance >= ((Player) sender).getLocation().distance(player.getLocation()))
                        {
                            Set<String> groupsList = ConfigManager.groups.getConfigurationSection("groups").getKeys(false);
                            int n1 = groupsList.size();
                            int n2 = 0;

                            for (String groups : groupsList)
                            {
                                n2++;
                                Permission perm = new Permission(ConfigManager.settings.getString("settings.permPrefix") + "." + groups, PermissionDefault.FALSE);
                                // groups
                                if (sender.hasPermission(perm))
                                {
                                    //papi
                                    if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
                                    {
                                        String format = ConfigManager.settings.getString("settings.localChat.localChatFormat");
                                        format= PlaceholderAPI.setPlaceholders(((Player) sender).getPlayer(), format);
                                        format = format.replace("%prefix%", ConfigManager.groups.getString("groups." + groups));
                                        format = format.replace("%player_name%", ((Player) sender).getPlayer().getName());
                                        format = format.replace("%arrow%", "»");
                                        format = format.replace("%message%", msg);
                                        player.sendMessage(IridiumColorAPI.process(format));
                                        break;
                                    }
                                    else
                                    {
                                        String format = ConfigManager.settings.getString("settings.localChat.localChatFormat");
                                        format = format.replace("%prefix%", ConfigManager.groups.getString("groups." + groups));
                                        format = format.replace("%player_name%", ((Player) sender).getPlayer().getName());
                                        format = format.replace("%arrow%", "»");
                                        format = format.replace("%message%", msg);
                                        player.sendMessage(IridiumColorAPI.process(format));
                                        break;
                                    }
                                }
                                else if (n2 == n1)
                                {
                                    //papi
                                    if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
                                    {
                                        String format = PlaceholderAPI.setPlaceholders(((Player) sender).getPlayer(), ConfigManager.settings.getString("settings.localChat.noGroupLocalChatFormat"));
                                        format = format.replace("%player_name%", sender.getName());
                                        format = format.replace("%arrow%", "»");
                                        format = format.replace("%message%", msg);
                                        player.sendMessage(IridiumColorAPI.process(format));
                                        break;

                                    }
                                    else
                                    {
                                        String format = (ConfigManager.settings.getString("settings.localChat.noGroupLocalChatFormat"));
                                        format = format.replace("%player_name%", sender.getName());
                                        format = format.replace("%arrow%", "»");
                                        format = format.replace("%message%", msg);
                                        player.sendMessage(IridiumColorAPI.process(format));
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                else
                {
                    sender.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.featureDisabled")));
                }
            }
        }
        else
        {
            // console
            // reload config
            if (cmd.getLabel().equalsIgnoreCase("ic-reload") || cmd.getLabel().equalsIgnoreCase("infinitychat-reload"))
            {
                ConfigManager.ReloadConfig();
                ConfigManager.ReloadConfig(); sender.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.pluginReload")));
            }
            // clear chat
            else if (cmd.getLabel().equalsIgnoreCase("cc") || cmd.getLabel().equalsIgnoreCase("clearchat"))
            {
                for (int i = 0; i <= 150; i++)
                {
                    Bukkit.broadcastMessage("   ");
                }
                String replaced1 = ConfigManager.msg.getString("messages.clearChat"), replaced2 = replaced1.replace("%player_name%", sender.getName());
                Bukkit.broadcastMessage(IridiumColorAPI.process(replaced2));
            }
            // infinity
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
                sender.sendMessage(" ");
            }
            else if (cmd.getLabel().equalsIgnoreCase("l") || cmd.getLabel().equalsIgnoreCase("local"))
            {
                sender.sendMessage(IridiumColorAPI.process("&4Error you are not a player"));
            }

        }
        return false;
    }
}