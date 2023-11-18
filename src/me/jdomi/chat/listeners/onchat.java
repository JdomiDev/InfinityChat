package me.jdomi.chat.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.clip.placeholderapi.PlaceholderAPI;
import me.jdomi.chat.api.config.ConfigManager;
import me.jdomi.chat.api.hex.IridiumColorAPI;
import me.jdomi.chat.main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class onchat implements Listener
{
    ArrayList<Player> cooldown = new ArrayList<>();

    main plugin;

    private ConsoleCommandSender console = main.chat.getServer().getConsoleSender();


    public final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");


    public String format(String msg)
    {
        if (Bukkit.getVersion().contains("1.16"))
        {

            Matcher match = this.pattern.matcher(msg);
            while (match.find())
            {
                String color = msg.substring(match.start(), match.end());
                msg = msg.replace(color, "" + ChatColor.of(color));
            }
        }
        return ChatColor.translateAlternateColorCodes('&', msg);
    }


    @EventHandler
    public void chatformat(AsyncPlayerChatEvent e)
    {
        final Player player = e.getPlayer();

        String message = e.getMessage().toLowerCase();

        if (ConfigManager.settings.getString("settings.antiChatRepeat").equalsIgnoreCase("true"))
        {


            HashMap<Player, String> previousMessages = new HashMap<>();
            if (previousMessages.containsValue(player))
            {
                if (message.equalsIgnoreCase(previousMessages.get(player)))
                {


                    player.sendMessage(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.spam"));
                    e.setCancelled(true);
                }
            }

            previousMessages.put(player, message);
        }

        if (ConfigManager.settings.getString("settings.chatCooldown").equalsIgnoreCase("true"))
        {
            if (!player.hasPermission("ic.cooldown.bypass"))
            {
                if (this.cooldown.contains(player))
                {

                    player.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.spam")));
                    e.setCancelled(true);

                    return;
                }
            }
        }

        if (ConfigManager.settings.getString("settings.antiSwear").equalsIgnoreCase("true"))
        {
            for (String blockedWord : ConfigManager.words.getStringList("censored-words"))
            {

                if (message.contains(blockedWord.toLowerCase()))
                {

                    player.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.swear")));
                    e.setCancelled(true);

                    break;
                }
            }
        }
        if (ConfigManager.settings.getString("settings.hexChat").equalsIgnoreCase("true"))
        {
            e.setMessage(format(e.getMessage()));
        }


        if (ConfigManager.settings.getString("settings.chatFormatting").equalsIgnoreCase("true"))
        {


            Set<String> groupsList = ConfigManager.groups.getConfigurationSection("groups").getKeys(false);
            int n1 = groupsList.size();
            int n2 = 0;

            for (String groups : groupsList)
            {
                n2++;
                Permission perm = new Permission(ConfigManager.settings.getString("settings.permPrefix") + "." + groups, PermissionDefault.FALSE);
                if (player.hasPermission(perm))
                {
                    //if placeholderapi
                    if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
                    {



                        String format = ConfigManager.settings.getString("settings.chatFormat");
                        format= PlaceholderAPI.setPlaceholders(e.getPlayer(), format);
                        format = format.replace("%prefix%", ConfigManager.groups.getString("groups." + groups));
                        format = format.replace("%player_name%", e.getPlayer().getName());
                        format = format.replace("%arrow%", "»");
                        format = format.replace("%message%", e.getMessage());


                        if(e.getPlayer().hasPermission("ic.placeholderChat") && e.isCancelled() == false)
                        {
                            format = PlaceholderAPI.setPlaceholders(e.getPlayer(), format);
                            Bukkit.broadcastMessage(IridiumColorAPI.process(format));
                        }
                        else if(e.isCancelled() == false)
                        {
                            Bukkit.broadcastMessage(IridiumColorAPI.process(format));
                        }
                        e.setCancelled(true);


                        break;
                    }
                    // if no placeholderapi
                    String format = ConfigManager.settings.getString("settings.chatFormat");
                    format = format.replace("%prefix%", ConfigManager.groups.getString("groups." + groups));
                    format = format.replace("%player_name%", e.getPlayer().getName());
                    format = format.replace("%arrow%", "»");
                    format = format.replace("%message%", e.getMessage());
                    Bukkit.broadcastMessage(IridiumColorAPI.process(format));
                    e.setCancelled(true);


                    break;
                }

                // if no groups
                else if (n2 == n1)
                {
                    //if papi
                    if (main.chat.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI"))
                    {


                        String format = PlaceholderAPI.setPlaceholders(e.getPlayer(), ConfigManager.settings.getString("settings.noGroupChatFormat"));
                        format = format.replace("%player_name%", e.getPlayer().getName());
                        format = format.replace("%arrow%", "»");
                        format = format.replace("%message%", e.getMessage());
                        if(e.getPlayer().hasPermission("ic.placeholderChat") && e.isCancelled() == false)
                        {
                            format = PlaceholderAPI.setPlaceholders(e.getPlayer(), format);
                            Bukkit.broadcastMessage(IridiumColorAPI.process(format));
                        }
                        else if(e.isCancelled() == false)
                        {
                            Bukkit.broadcastMessage(IridiumColorAPI.process(format));
                        }
                        e.setCancelled(true);

                    }
                    else
                    {
                        //if no papi

                        String format = ConfigManager.settings.getString("settings.noPermissionChatFormat");
                        format = format.replace("%player_name%", e.getPlayer().getName());
                        format = format.replace("%arrow%", "»");
                        format = format.replace("%message%", e.getMessage());
                        if(e.isCancelled() == false)
                        {
                            Bukkit.broadcastMessage(IridiumColorAPI.process(format));
                        }
                        e.setCancelled(true);
                    }
                    break;
                }
                perm = null;
            }
        }

        if (ConfigManager.settings.getString("settings.chatCooldown").equalsIgnoreCase("true"))
        {


            this.cooldown.add(player);


            try
            {
                int cooldownSec = Integer.valueOf(ConfigManager.settings.getString("settings.chatCooldownTime")).intValue() * 20;
                (new BukkitRunnable()
                {
                    public void run()
                    {
                        onchat.this.cooldown.remove(player);
                    }
                }).runTaskLater((Plugin)main.chat, cooldownSec);
            }
            catch (Exception ex)
            {


                this.console.sendMessage(IridiumColorAPI.process("&4Error with delay in config"));
            }
        }
    }
}