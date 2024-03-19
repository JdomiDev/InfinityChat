package me.jdomi.chat.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import me.jdomi.chat.api.chatapi.antiShout;
import me.jdomi.chat.api.chatapi.filterManager;
import me.jdomi.chat.api.chatapi.mentionPlayer;
import me.jdomi.chat.api.config.configManager;
import me.jdomi.chat.api.hex.IridiumColorAPI;
import me.jdomi.chat.main;
import net.essentialsx.api.v2.services.discord.MessageType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.*;

import static me.jdomi.chat.api.config.configManager.*;

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
                    configManager.ReloadConfig();
                    sender.sendMessage(IridiumColorAPI.process(configManager.settings.getString("plugin-prefix") + configManager.msg.getString("messages.pluginReload")));
                }
                else
                {
                    sender.sendMessage(IridiumColorAPI.process(configManager.settings.getString("plugin-prefix") + configManager.msg.getString("messages.permission")));
                }
            }
            // mute
            else if ((cmd.getLabel().equalsIgnoreCase("mute") || cmd.getLabel().equalsIgnoreCase("m")))
            {
                if(sender.hasPermission("ic.mute") || sender.isOp())
                {
                    if(args.length != 0)
                    {
                        // check config/database
                        if((!mute.isSet("muted-players."+args[0]) && !mute.getBoolean("muted-players."+args[0])))
                        {
                            // save mute
                            configManager.mute.set("muted-players."+args[0],true);
                            saveMute();
                            sender.sendMessage(IridiumColorAPI.process(configManager.settings.getString("plugin-prefix") + configManager.msg.getString("messages.mute").replace("%player%",args[0])));
                        }
                        else
                        {
                            //already muted
                            sender.sendMessage(IridiumColorAPI.process(configManager.settings.getString("plugin-prefix") + configManager.msg.getString("messages.alreadyMute").replace("%player%",args[0])));
                        }
                    }
                    else
                    {
                        sender.sendMessage(IridiumColorAPI.process(configManager.settings.getString("plugin-prefix") + configManager.msg.getString("messages.muteArgs")));
                    }

                }
                else
                {
                    sender.sendMessage(IridiumColorAPI.process(configManager.settings.getString("plugin-prefix") + configManager.msg.getString("messages.permission")));
                }
            }
            // unmute
            else if ((cmd.getLabel().equalsIgnoreCase("unmute") || cmd.getLabel().equalsIgnoreCase("um")))
            {
                if(sender.hasPermission("ic.mute") || sender.isOp())
                {
                    if(args.length != 0)
                    {
                        // check config/database
                        if((mute.isSet("muted-players."+args[0]) && mute.getBoolean("muted-players."+args[0])))
                        {
                            // save choice
                            configManager.mute.set("muted-players." + args[0], null);
                            saveMute();
                            sender.sendMessage(IridiumColorAPI.process(configManager.settings.getString("plugin-prefix") + configManager.msg.getString("messages.unmute").replace("%player%",args[0])));
                        }
                        else
                        {
                            //already unmuted
                            sender.sendMessage(IridiumColorAPI.process(configManager.settings.getString("plugin-prefix") + configManager.msg.getString("messages.alreadyUnmute").replace("%player%",args[0])));
                        }
                    }
                    else
                    {
                        sender.sendMessage(IridiumColorAPI.process(configManager.settings.getString("plugin-prefix") + configManager.msg.getString("messages.unmuteArgs")));
                    }
                }
                else
                {
                    // no perm
                    sender.sendMessage(IridiumColorAPI.process(configManager.settings.getString("plugin-prefix") + configManager.msg.getString("messages.permission")));
                }
            }
            // announce
            else if(cmd.getLabel().equalsIgnoreCase("announce") || cmd.getLabel().equalsIgnoreCase("anc"))
            {
                if(args.length != 0)
                {
                    if (sender.hasPermission("ic.announce"))
                    {
                        String msg = "";

                        for (int i = 0; i < args.length ; i++)
                        {
                            msg = msg + " " + args[i];
                        }

                        String format = configManager.settings.getString("settings.formatting.announceFormat").replace("%message%", msg);
                        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI") == true)
                        {
                            format = PlaceholderAPI.setPlaceholders(((Player) sender).getPlayer(), format);
                        }
                        Bukkit.broadcastMessage(IridiumColorAPI.process(format));
                    }
                    else
                    {
                        // no perm
                        sender.sendMessage(IridiumColorAPI.process(configManager.settings.getString("plugin-prefix") + configManager.msg.getString("messages.permission")));
                    }
                }
                else
                {
                    sender.sendMessage(IridiumColorAPI.process(configManager.settings.getString("plugin-prefix") + configManager.msg.getString("messages.announceArgs")));
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
                    String replaced1 = configManager.msg.getString("messages.clearChat"), replaced2 = replaced1.replace("%player_name%", sender.getName());
                    Bukkit.broadcastMessage(IridiumColorAPI.process(replaced2));

                }
                else
                {
                    // no perm
                    sender.sendMessage(IridiumColorAPI.process(configManager.settings.getString("plugin-prefix") + configManager.msg.getString("messages.permission")));
                }

            }
            // infinity
            else if (cmd.getLabel().equalsIgnoreCase("ic") || cmd.getLabel().equalsIgnoreCase("infinitychat"))
            {

                PluginDescriptionFile pdf = main.chat.getDescription();

                sender.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>InfinityChat</GRADIENT:a4fc00>&7 » &#C4D3D1Running version " + pdf.getVersion()));
                sender.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>InfinityChat</GRADIENT:a4fc00>&7 » &#C4D3D1Plugin Created by: <GRADIENT:fcc600>Jdomi</GRADIENT:a4fc00>"));
            }
            // staff chat
            else if (cmd.getLabel().equalsIgnoreCase("staff") || cmd.getLabel().equalsIgnoreCase("s"))
            {
                if(sender.hasPermission("ic.staff") || sender.isOp())
                {
                    if (args.length != 0)
                    {
                        String msg = "";
                        for (int i = 0; i < args.length; i++)
                        {
                            msg = msg + " " + args[i];
                        }

                        Set<String> groupsList = configManager.groups.getConfigurationSection("groups").getKeys(false);
                        int n1 = groupsList.size();
                        int n2 = 0;

                        for (String groups : groupsList)
                        {
                            n2++;
                            Permission perm = new Permission(configManager.settings.getString("settings.permPrefix") + "." + groups, PermissionDefault.FALSE);
                            // groups
                            if (sender.hasPermission(perm))
                            {
                                //papi
                                if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
                                {
                                    String format = configManager.settings.getString("settings.formatting.staffChatFormat");
                                    format = PlaceholderAPI.setPlaceholders(((Player) sender).getPlayer(), format);
                                    format = format.replace("%prefix%", configManager.groups.getString("groups." + groups));
                                    format = format.replace("%player_name%", ((Player) sender).getPlayer().getName());
                                    format = format.replace("%arrow%", "»");
                                    format = format.replace("%message%", msg);
                                    for (Player player : Bukkit.getServer().getOnlinePlayers())
                                    {
                                        if (player.hasPermission("ic.staff") || player.isOp())
                                        {
                                            player.sendMessage(IridiumColorAPI.process(format));
                                        }
                                    }
                                    if (Bukkit.getPluginManager().isPluginEnabled("EssentialsDiscord") && configManager.settings.getBoolean("settings.essentialsDiscordStaffChannel.enabled"))
                                    {
                                        main.api.sendMessage(new MessageType("staff"), IridiumColorAPI.process(format), true);
                                    }
                                    break;
                                }
                                //nopapi
                                else
                                {
                                    String format = configManager.settings.getString("settings.formatting.staffChatFormat");
                                    format = format.replace("%prefix%", configManager.groups.getString("groups." + groups));
                                    format = format.replace("%player_name%", ((Player) sender).getPlayer().getName());
                                    format = format.replace("%arrow%", "»");
                                    format = format.replace("%message%", msg);
                                    for (Player player : Bukkit.getServer().getOnlinePlayers())
                                    {
                                        if (player.hasPermission("ic.staff") || player.isOp())
                                        {
                                            player.sendMessage(IridiumColorAPI.process(format));
                                        }
                                    }
                                    if (Bukkit.getPluginManager().isPluginEnabled("EssentialsDiscord") && configManager.settings.getBoolean("settings.essentialsDiscordStaffChannel.enabled"))
                                    {
                                        main.api.sendMessage(new MessageType("staff"), IridiumColorAPI.process(format), true);
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
                                    String format = PlaceholderAPI.setPlaceholders(((Player) sender).getPlayer(), configManager.settings.getString("settings.formatting.noGroupStaffChatFormat"));
                                    format = format.replace("%player_name%", sender.getName());
                                    format = format.replace("%arrow%", "»");
                                    format = format.replace("%message%", msg);

                                    for (Player player : Bukkit.getServer().getOnlinePlayers())
                                    {
                                        if (player.hasPermission("ic.staff") || player.isOp())
                                        {
                                            player.sendMessage(IridiumColorAPI.process(format));
                                        }
                                    }
                                    if (Bukkit.getPluginManager().isPluginEnabled("EssentialsDiscord") && configManager.settings.getBoolean("settings.essentialsDiscordStaffChannel.enabled"))
                                    {
                                        main.api.sendMessage(new MessageType("staff"), IridiumColorAPI.process(format), true);
                                    }
                                    break;
                                }
                                // nopapi
                                else
                                {

                                    String format = configManager.settings.getString("settings.formatting.noGroupStaffChatFormat");
                                    format = format.replace("%player_name%", sender.getName());
                                    format = format.replace("%arrow%", "»");
                                    format = format.replace("%message%", msg);

                                    for (Player player : Bukkit.getServer().getOnlinePlayers())
                                    {
                                        if (player.hasPermission("ic.staff") || player.isOp())
                                        {
                                            player.sendMessage(IridiumColorAPI.process(format));
                                        }
                                    }
                                    if (Bukkit.getPluginManager().isPluginEnabled("EssentialsDiscord") && configManager.settings.getBoolean("settings.essentialsDiscordStaffChannel.enabled"))
                                    {
                                        main.api.sendMessage(new MessageType("staff"), IridiumColorAPI.process(format), true);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    else
                    {
                        sender.sendMessage(IridiumColorAPI.process(configManager.settings.getString("plugin-prefix") + configManager.msg.getString("messages.staffChatArgs")));
                    }
                }
                else
                {
                    sender.sendMessage(IridiumColorAPI.process(configManager.settings.getString("plugin-prefix") + configManager.msg.getString("messages.permission")));
                }
            }
            // local

            else if(cmd.getLabel().equalsIgnoreCase("l") || cmd.getLabel().equalsIgnoreCase("local"))
            {
                if(args.length != 0)
                {
                    if(configManager.settings.getString("settings.localChat.enabled").equalsIgnoreCase("true"))
                    {
                        float distance = Float.valueOf(configManager.settings.getInt("settings.localChat.localChatDistance"));


                        String msg = "";
                        for (int i = 0; i < args.length ; i++)
                        {
                            msg = msg + " " + args[i];
                        }


                        if(!filterManager.getBoolean(((Player) sender).getPlayer(),msg))
                        {
                            mentionPlayer.mentionCheck(((Player) sender).getPlayer(),msg,true);
                            for(Player player : Bukkit.getServer().getOnlinePlayers())
                            {

                                if(distance >= ((Player) sender).getLocation().distance(player.getLocation()))
                                {
                                    msg = antiShout.getString(((Player) sender).getPlayer(),msg);

                                    Set<String> groupsList = configManager.groups.getConfigurationSection("groups").getKeys(false);
                                    int n1 = groupsList.size();
                                    int n2 = 0;

                                    for (String groups : groupsList)
                                    {
                                        n2++;
                                        Permission perm = new Permission(configManager.settings.getString("settings.permPrefix") + "." + groups, PermissionDefault.FALSE);
                                        // groups
                                        if (sender.hasPermission(perm))
                                        {
                                            //papi
                                            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
                                            {
                                                String format = configManager.settings.getString("settings.localChat.localChatFormat");
                                                format= PlaceholderAPI.setPlaceholders(((Player) sender).getPlayer(), format);
                                                format = format.replace("%prefix%", configManager.groups.getString("groups." + groups));
                                                format = format.replace("%player_name%", ((Player) sender).getPlayer().getName());
                                                format = format.replace("%arrow%", "»");
                                                format = format.replace("%message%", msg);
                                                player.sendMessage(IridiumColorAPI.process(format));
                                                break;
                                            }
                                            else
                                            {
                                                String format = configManager.settings.getString("settings.localChat.localChatFormat");
                                                format = format.replace("%prefix%", configManager.groups.getString("groups." + groups));
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
                                                String format = PlaceholderAPI.setPlaceholders(((Player) sender).getPlayer(), configManager.settings.getString("settings.localChat.noGroupLocalChatFormat"));
                                                format = format.replace("%player_name%", sender.getName());
                                                format = format.replace("%arrow%", "»");
                                                format = format.replace("%message%", msg);
                                                player.sendMessage(IridiumColorAPI.process(format));
                                                break;

                                            }
                                            else
                                            {
                                                String format = (configManager.settings.getString("settings.localChat.noGroupLocalChatFormat"));
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
                    }
                    else
                    {
                        sender.sendMessage(IridiumColorAPI.process(configManager.settings.getString("plugin-prefix") + configManager.msg.getString("messages.featureDisabled")));
                    }

                }
                else
                {
                    sender.sendMessage(IridiumColorAPI.process(configManager.settings.getString("plugin-prefix") + configManager.msg.getString("messages.localChatArgs")));
                }
            }
        }
        else
        {
            // console
            // reload config
            if (cmd.getLabel().equalsIgnoreCase("ic-reload") || cmd.getLabel().equalsIgnoreCase("infinitychat-reload"))
            {
                configManager.ReloadConfig();
                sender.sendMessage(IridiumColorAPI.process(configManager.settings.getString("plugin-prefix") + configManager.msg.getString("messages.pluginReload")));
            }
            // clear chat
            else if (cmd.getLabel().equalsIgnoreCase("cc") || cmd.getLabel().equalsIgnoreCase("clearchat"))
            {
                for (int i = 0; i <= 150; i++)
                {
                    Bukkit.broadcastMessage("   ");
                }
                String replaced1 = configManager.msg.getString("messages.clearChat"), replaced2 = replaced1.replace("%player_name%", sender.getName());
                Bukkit.broadcastMessage(IridiumColorAPI.process(replaced2));
            }
            // infinity
            else if (cmd.getLabel().equalsIgnoreCase("ic") || cmd.getLabel().equalsIgnoreCase("infinitychat"))
            {
                PluginDescriptionFile pdf = main.chat.getDescription();
                sender.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>InfinityChat</GRADIENT:a4fc00>&7 » &#C4D3D1Running version " + pdf.getVersion()));
                sender.sendMessage(IridiumColorAPI.process("<GRADIENT:fcc600>InfinityChat</GRADIENT:a4fc00>&7 » &#C4D3D1Plugin Created by: <GRADIENT:fcc600>Jdomi</GRADIENT:a4fc00>"));
            }
            else if (cmd.getLabel().equalsIgnoreCase("l") || cmd.getLabel().equalsIgnoreCase("local"))
            {
                sender.sendMessage(IridiumColorAPI.process("&4Error you are not a player"));
            }
            // mute
            else if (cmd.getLabel().equalsIgnoreCase("mute") || cmd.getLabel().equalsIgnoreCase("m")
            )
            {
                if(args.length != 0)
                {
                    if((!mute.isSet("muted-players."+args[0]) && !mute.getBoolean("muted-players."+args[0])))
                    {
                        configManager.mute.set("muted-players."+args[0],true);
                        saveMute();
                        sender.sendMessage(IridiumColorAPI.process(configManager.settings.getString("plugin-prefix") + configManager.msg.getString("messages.mute").replace("%player%",args[0])));
                    }
                    else
                    {
                        //already muted
                        sender.sendMessage(IridiumColorAPI.process(configManager.settings.getString("plugin-prefix") + configManager.msg.getString("messages.alreadyMute").replace("%player%",args[0])));
                    }
                }
                else
                {
                    sender.sendMessage(IridiumColorAPI.process(configManager.settings.getString("plugin-prefix") + configManager.msg.getString("messages.muteArgs")));
                }
            }
            // unmute
            else if (cmd.getLabel().equalsIgnoreCase("unmute") || cmd.getLabel().equalsIgnoreCase("um"))
            {
                if(args.length != 0)
                {
                    if ((mute.isSet("muted-players." + args[0]) && mute.getBoolean("muted-players." + args[0])))
                    {
                        configManager.mute.set("muted-players." + args[0], null);
                        saveMute();
                        sender.sendMessage(IridiumColorAPI.process(configManager.settings.getString("plugin-prefix") + configManager.msg.getString("messages.unmute").replace("%player%", args[0])));
                    }
                    else
                    {
                        //already unmuted
                        sender.sendMessage(IridiumColorAPI.process(configManager.settings.getString("plugin-prefix") + configManager.msg.getString("messages.alreadyUnmute").replace("%player%", args[0])));
                    }
                }
                else
                {
                    sender.sendMessage(IridiumColorAPI.process(configManager.settings.getString("plugin-prefix") + configManager.msg.getString("messages.unmuteArgs")));
                }
            }
        }
        return false;
    }
}