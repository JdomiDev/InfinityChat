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
                Permission perm = new Permission(ConfigManager.settings.getString("settings.permPrefix") + "." + ConfigManager.settings.getString("settings.permPrefix"), PermissionDefault.FALSE);
                if (player.hasPermission(perm))
                {
                    //disabled cause broken enable if fixed
                    if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
                    {



                        String str1 = ConfigManager.settings.getString("settings.chatFormat");
                        String withPlaceholdersSet = PlaceholderAPI.setPlaceholders(e.getPlayer(), str1);
                        String str2 = withPlaceholdersSet.replace("%prefix%", ConfigManager.groups.getString("groups." + groups));
                        String str3 = str2.replace("%player_name%", e.getPlayer().getName());
                        String str4 = str3.replace("%arrow%", "»");
                        String str5 = str4.replace("%message%", e.getMessage());
                        e.setFormat(IridiumColorAPI.process(str5));


                        break;
                    }

                    String inported = ConfigManager.settings.getString("settings.chatFormat");
                    String replaced1 = inported.replace("%prefix%", ConfigManager.groups.getString("groups." + groups));
                    String replaced2 = replaced1.replace("%player_name%", e.getPlayer().getName());
                    String replaced3 = replaced2.replace("%arrow%", "»");
                    String replaced4 = replaced3.replace("%message%", e.getMessage());
                    e.setFormat(IridiumColorAPI.process(replaced4));

                    break;
                }
                if (n2 == n1)
                {
                    //disabled cause broken enable if fixed
                    if (main.chat.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI"))
                    {


                        String inported = PlaceholderAPI.setPlaceholders(e.getPlayer(), ConfigManager.settings.getString("settings.noPermissionChatFormat"));
                        String replaced1 = inported.replace("%player_name%", e.getPlayer().getName());
                        String replaced2 = replaced1.replace("%arrow%", "»");
                        String replaced3 = replaced2.replace("%message%", e.getMessage());
                        if(e.getPlayer().hasPermission("ic.placeholderChat") && e.isCancelled() == false)
                        {
                            replaced3 = PlaceholderAPI.setPlaceholders(e.getPlayer(), replaced3);
                            Bukkit.broadcastMessage(IridiumColorAPI.process(replaced3));
                        }
                        else if(e.isCancelled() == false)
                        {
                            Bukkit.broadcastMessage(IridiumColorAPI.process(replaced3));
                        }
                        e.setCancelled(true);

                    }
                    else {

                        String inported = ConfigManager.settings.getString("settings.noPermissionChatFormat");
                        String replaced1 = inported.replace("%player_name%", e.getPlayer().getName());
                        String replaced2 = replaced1.replace("%arrow%", "»");
                        String replaced3 = replaced2.replace("%message%", e.getMessage());
                        if(e.isCancelled() == false)
                        {
                            Bukkit.broadcastMessage(IridiumColorAPI.process(replaced3));
                        }
                        e.setCancelled(true);
                    }
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