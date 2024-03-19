package me.jdomi.chat.listeners;

import java.util.Set;
import me.clip.placeholderapi.PlaceholderAPI;
import me.jdomi.chat.api.chatapi.antiShout;
import me.jdomi.chat.api.config.configManager;
import me.jdomi.chat.api.chatapi.filterManager;
import me.jdomi.chat.api.hex.IridiumColorAPI;
import me.jdomi.chat.api.chatapi.mentionPlayer;
import me.jdomi.chat.main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class onChat implements Listener
{
    Boolean Papi = main.chat.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI");

    public void sendChat(Player sender, String message, AsyncPlayerChatEvent e)
    {
        boolean forceLocalChat;

        forceLocalChat = configManager.settings.getBoolean("settings.localChat.forceLocalChat");


        if (forceLocalChat)
        {
            float configDistance = (float) configManager.settings.getInt("settings.localChat.localChatDistance");
            for(Player player : Bukkit.getServer().getOnlinePlayers())
            {
                double distance = player.getLocation().distance(sender.getLocation());
                if(distance <= configDistance)
                {
                    player.sendMessage(IridiumColorAPI.process(message));
                    e.setCancelled(true);
                }
            }
        }
        else
        {
            if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
            {
                e.setFormat(IridiumColorAPI.process(PlaceholderAPI.setPlaceholders(sender, message).replace("%","%%")));
            }
            else
            {
                e.setFormat(IridiumColorAPI.process(message.replace("%","%%")));
            }
        }
    }

    @EventHandler
    public void chatformat(AsyncPlayerChatEvent e)
    {
        final Player player = e.getPlayer();

        if(!filterManager.getBoolean(player,e.getMessage()))
        {
            // mention
            mentionPlayer.mentionCheck(e.getPlayer(),e.getMessage(), configManager.settings.getBoolean("settings.localChat.forceLocalChat"));

            //antishout
            e.setMessage(antiShout.getString(e.getPlayer(),e.getMessage()));

            // chat formatting
            if (configManager.settings.getBoolean("settings.formatting.chatFormatting"))
            {

                boolean forceLocal = configManager.settings.getBoolean("settings.localChat.forceLocalChat");

                Set<String> groupsList = configManager.groups.getConfigurationSection("groups").getKeys(false);
                int n1 = groupsList.size();
                int n2 = 0;

                for (String groups : groupsList)
                {
                    n2++;
                    Permission perm = new Permission(configManager.settings.getString("settings.permPrefix") + "." + groups, PermissionDefault.FALSE);
                    // has group perm
                    if (player.hasPermission(perm))
                    {
                        String format;

                        if(forceLocal)
                        {
                            format = configManager.settings.getString("settings.localChat.localChatFormat");
                        }
                        else
                        {
                            format = configManager.settings.getString("settings.formatting.chatFormat");
                        }

                        format = PlaceholderAPI.setPlaceholders(e.getPlayer(), format);
                        format = format.replace("%prefix%", configManager.groups.getString("groups." + groups));
                        format = format.replace("%player_name%", e.getPlayer().getName());
                        format = format.replace("%arrow%", "»");
                        format = format.replace("%message%", e.getMessage());


                        if(e.getPlayer().hasPermission("ic.placeholderChat") && !e.isCancelled() && Papi)
                        {
                            format = PlaceholderAPI.setPlaceholders(e.getPlayer(), format);
                            sendChat(e.getPlayer(), format, e);
                            break;
                        }
                        else if(!e.isCancelled())
                        {
                            sendChat(e.getPlayer(), format, e);
                            break;
                        }
                    }

                    // if no groups
                    else if (n2 == n1)
                    {
                        String format;
                        if(forceLocal)
                        {
                            format = configManager.settings.getString("settings.localChat.noGroupLocalChatFormat");
                        }
                        else
                        {
                            format = configManager.settings.getString("settings.formatting.noGroupChatFormat");
                        }

                        format = format.replace("%player_name%", e.getPlayer().getName());
                        format = format.replace("%arrow%", "»");
                        format = format.replace("%message%", e.getMessage());
                        if(e.getPlayer().hasPermission("ic.placeholderChat") && !e.isCancelled() && Papi)
                        {
                            format = PlaceholderAPI.setPlaceholders(e.getPlayer(), format);
                            sendChat(e.getPlayer(), format, e);
                            break;
                        }
                        else if(!e.isCancelled())
                        {
                            sendChat(e.getPlayer(), format, e);
                            break;
                        }
                    }
                    perm = null;
                }
            }
        }
        else
        {
            e.setCancelled(true);
        }
    }
}