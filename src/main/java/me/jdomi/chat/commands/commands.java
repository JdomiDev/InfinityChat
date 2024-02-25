package me.jdomi.chat.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import me.jdomi.chat.api.config.ConfigManager;
import me.jdomi.chat.api.hex.IridiumColorAPI;
import me.jdomi.chat.main;
import net.essentialsx.api.v2.services.discord.DiscordService;
import net.essentialsx.api.v2.services.discord.MessageType;
import net.essentialsx.dep.net.dv8tion.jda.api.entities.MessageChannel;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static me.jdomi.chat.api.config.ConfigManager.*;

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
                    ConfigManager.ReloadConfig();
                    sender.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.pluginReload")));
                }
                else
                {
                    sender.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.permission")));
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
                        if(!mutes.contains(args[0]) || (!mute.isSet("muted-players."+args[0]) && !mute.getBoolean("muted-players."+args[0])))
                        {
                            // save mute
                            ConfigManager.mute.set("muted-players."+args[0],true);
                            saveMute();
                            mutes.add(args[0]);
                            sender.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.mute").replace("%player%",args[0])));
                        }
                        else
                        {
                            //already muted
                            sender.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.alreadyMute").replace("%player%",args[0])));
                        }
                    }
                    else
                    {
                        sender.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.muteArgs")));
                    }

                }
                else
                {
                    sender.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.permission")));
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
                        if(mutes.contains(args[0]) || (mute.isSet("muted-players."+args[0]) && mute.getBoolean("muted-players."+args[0])))
                        {
                            // save choice
                            ConfigManager.mute.set("muted-players."+args[0],false);
                            saveMute();
                            mutes.remove(args[0]);
                            sender.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.unmute").replace("%player%",args[0])));
                        }
                        else
                        {
                            //already unmuted
                            sender.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.alreadyUnmute").replace("%player%",args[0])));
                        }
                    }
                    else
                    {
                        sender.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.unmuteArgs")));
                    }
                }
                else
                {
                    // no perm
                    sender.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.permission")));
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

                        String format = ConfigManager.settings.getString("settings.announceFormat").replace("%message%", msg);
                        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI") == true)
                        {
                            format = PlaceholderAPI.setPlaceholders(((Player) sender).getPlayer(), format);
                        }
                        Bukkit.broadcastMessage(IridiumColorAPI.process(format));
                    }
                    else
                    {
                        // no perm
                        sender.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.permission")));
                    }
                }
                else
                {
                    sender.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.announceArgs")));
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
                    // no perm
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
                                    format = PlaceholderAPI.setPlaceholders(((Player) sender).getPlayer(), format);
                                    format = format.replace("%prefix%", ConfigManager.groups.getString("groups." + groups));
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
                                    if (Bukkit.getPluginManager().isPluginEnabled("EssentialsDiscord") && ConfigManager.settings.getBoolean("settings.essentialsDiscordStaffChannel.enabled"))
                                    {
                                        main.api.sendMessage(new MessageType("staff"), IridiumColorAPI.process(format), true);
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
                                    for (Player player : Bukkit.getServer().getOnlinePlayers())
                                    {
                                        if (player.hasPermission("ic.staff") || player.isOp())
                                        {
                                            player.sendMessage(IridiumColorAPI.process(format));
                                        }
                                    }
                                    if (Bukkit.getPluginManager().isPluginEnabled("EssentialsDiscord") && ConfigManager.settings.getBoolean("settings.essentialsDiscordStaffChannel.enabled"))
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
                                    String format = PlaceholderAPI.setPlaceholders(((Player) sender).getPlayer(), ConfigManager.settings.getString("settings.noGroupStaffChatFormat"));
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
                                    if (Bukkit.getPluginManager().isPluginEnabled("EssentialsDiscord") && ConfigManager.settings.getBoolean("settings.essentialsDiscordStaffChannel.enabled"))
                                    {
                                        main.api.sendMessage(new MessageType("staff"), IridiumColorAPI.process(format), true);
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

                                    for (Player player : Bukkit.getServer().getOnlinePlayers())
                                    {
                                        if (player.hasPermission("ic.staff") || player.isOp())
                                        {
                                            player.sendMessage(IridiumColorAPI.process(format));
                                        }
                                    }
                                    if (Bukkit.getPluginManager().isPluginEnabled("EssentialsDiscord") && ConfigManager.settings.getBoolean("settings.essentialsDiscordStaffChannel.enabled"))
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
                        sender.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.staffChatArgs")));
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
                if(args.length != 0)
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
                else
                {
                    sender.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.localChatArgs")));
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
                sender.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.pluginReload")));
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
            // mute
            else if (cmd.getLabel().equalsIgnoreCase("mute") || cmd.getLabel().equalsIgnoreCase("m")
            )
            {
                if(args.length != 0)
                {
                    if(!mutes.contains(args[0]) || (!mute.isSet("muted-players."+args[0]) && !mute.getBoolean("muted-players."+args[0])))
                    {
                        ConfigManager.mute.set("muted-players."+args[0],true);
                        saveMute();
                        mutes.add(args[0]);
                        sender.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.mute").replace("%player%",args[0])));
                    }
                    else
                    {
                        //already muted
                        sender.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.alreadyMute").replace("%player%",args[0])));
                    }
                }
                else
                {
                    sender.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.muteArgs")));
                }
            }
            // unmute
            else if (cmd.getLabel().equalsIgnoreCase("unmute") || cmd.getLabel().equalsIgnoreCase("um"))
            {
                if(args.length != 0)
                {
                    if (mutes.contains(args[0]) || (mute.isSet("muted-players." + args[0]) && mute.getBoolean("muted-players." + args[0])))
                    {
                        ConfigManager.mute.set("muted-players." + args[0], false);
                        saveMute();
                        mutes.remove(args[0]);
                        sender.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.unmute").replace("%player%", args[0])));
                    }
                    else
                    {
                        //already unmuted
                        sender.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.alreadyUnmute").replace("%player%", args[0])));
                    }
                }
                else
                {
                    sender.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.unmuteArgs")));
                }
            }
        }
        return false;
    }
}