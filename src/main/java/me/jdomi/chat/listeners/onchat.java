package me.jdomi.chat.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Pattern;
import me.clip.placeholderapi.PlaceholderAPI;
import me.jdomi.chat.api.config.ConfigManager;
import me.jdomi.chat.api.hex.IridiumColorAPI;
import me.jdomi.chat.main;
import net.essentialsx.api.v2.services.discord.DiscordService;
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

    HashMap<Player, String> previousMessages = new HashMap<>();

    main plugin;

    private ConsoleCommandSender console = main.chat.getServer().getConsoleSender();


    public final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    public void sendChat(Player sender, String message, AsyncPlayerChatEvent e)
    {
        boolean forceLocalChat;
        if(ConfigManager.settings.getBoolean("settings.localChat.forceLocalChat"))
        {
            forceLocalChat = true;
        }
        else
        {
            forceLocalChat = false;
        }


        if (forceLocalChat)
        {
            float configDistance = (float) ConfigManager.settings.getInt("settings.localChat.localChatDistance");
            for(Player player : Bukkit.getServer().getOnlinePlayers())
            {
                double distance = player.getLocation().distance(sender.getLocation());
                if(distance <= configDistance)
                {
                    player.sendMessage(IridiumColorAPI.process(message));
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
        boolean isMuted = false;
        final Player player = e.getPlayer();

        String message = e.getMessage().toLowerCase();
        String messageC = e.getMessage();
        // ismuted
        if(ConfigManager.mute.isSet("muted-players."+e.getPlayer().getName()))
        {
            if(ConfigManager.mute.getBoolean("muted-players."+e.getPlayer().getName()))
            {
                player.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.playerIsMuted")));
                e.setCancelled(true);
                isMuted = true;
            }
        }
        // anti shout
        if (ConfigManager.settings.getBoolean("settings.antiCapsLockSpam"))
        {
            if(!player.hasPermission("ic.shout") || !player.isOp())
            {
                String regex = "[^a-zA-Z0-9\\s]";
                String msg = messageC.replace(" ", "");
                msg = msg.replaceAll(regex,"");

                int charCount = msg.length();
                int capLetCound = 0;


                for (int i = 0; i < charCount; i++)
                {
                    char currentChar = msg.charAt(i);
                    if(Character.isUpperCase(currentChar))
                    {
                        capLetCound++;
                    }
                }
                double percentage = (double) capLetCound / charCount * 100;

                if(percentage >= ConfigManager.settings.getInt("settings.antiCapsLockSpamPercentage"))
                {
                    messageC = messageC.toLowerCase();
                    messageC = messageC.substring(0,1).toUpperCase() + messageC.substring(1);
                    e.setMessage(messageC);
                }
            }
        }

        // antiswear
        if (ConfigManager.settings.getBoolean("settings.antiSwear") && !isMuted)
        {
            if (!player.hasPermission("ic.cooldown.bypass") || !player.isOp())
            {
                for (String blockedWord : ConfigManager.words.getStringList("censored-words"))
                {


                    if(message.contains(blockedWord.toLowerCase()))
                    {
                        boolean nextSpaceCheck = false;
                        boolean prevSpaceCheck = false;

                        int sub = message.indexOf(blockedWord.toLowerCase());

                        char nextLetter;
                        char previousLetter;

                        //prev char check
                        if(sub > 0)
                        {
                            previousLetter = message.charAt(sub - 1);
                            if(previousLetter == ' ')
                            {
                                prevSpaceCheck = true;
                            }
                        }
                        else
                        {
                            prevSpaceCheck = true;
                        }


                        //next char check
                        if(sub+blockedWord.length() < message.length())
                        {
                            nextLetter = message.charAt(sub + blockedWord.length());
                            if(nextLetter == ' ')
                            {
                                nextSpaceCheck = true;
                            }
                        }
                        else
                        {
                            nextSpaceCheck = true;
                        }


                        // check if it no letter behind or front
                        if (nextSpaceCheck && prevSpaceCheck)
                        {
                            player.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.swear").replace("%word%", blockedWord.toLowerCase())));
                            e.setCancelled(true);
                            isMuted = true;
                            break;
                        }
                    }
                }
            }
        }
        // cooldown
        if (ConfigManager.settings.getBoolean("settings.chatCooldown") && !isMuted)
        {
            if (!player.hasPermission("ic.cooldown.bypass") || !player.isOp())
            {
                if (this.cooldown.contains(player))
                {

                    player.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.spam")));
                    e.setCancelled(true);

                    return;
                }
            }
        }
        // anti spam
        if (ConfigManager.settings.getBoolean("settings.antiChatRepeat") && !isMuted)
        {
            if (!player.hasPermission("ic.cooldown.bypass") || !player.isOp())
            {
                if (previousMessages.containsKey(player))
                {
                    if (message.equalsIgnoreCase(previousMessages.get(player)))
                    {
                        player.sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + ConfigManager.msg.getString("messages.spam")));
                        e.setCancelled(true);
                    }
                }

                previousMessages.put(player, message);
            }
        }
        // chat formatting
        if (ConfigManager.settings.getBoolean("settings.chatFormatting"))
        {

            boolean forceLocal = ConfigManager.settings.getBoolean("settings.localChat.forceLocalChat");

            Set<String> groupsList = ConfigManager.groups.getConfigurationSection("groups").getKeys(false);
            int n1 = groupsList.size();
            int n2 = 0;

            for (String groups : groupsList)
            {
                n2++;
                Permission perm = new Permission(ConfigManager.settings.getString("settings.permPrefix") + "." + groups, PermissionDefault.FALSE);
                // has group perm
                if (player.hasPermission(perm))
                {
                    String format;

                    if(forceLocal)
                    {
                        format = ConfigManager.settings.getString("settings.localChat.localChatFormat");
                    }
                    else
                    {
                        format = ConfigManager.settings.getString("settings.chatFormat");
                    }

                    //if placeholderapi
                    if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
                    {
                        format = PlaceholderAPI.setPlaceholders(e.getPlayer(), format);
                        format = format.replace("%prefix%", ConfigManager.groups.getString("groups." + groups));
                        format = format.replace("%player_name%", e.getPlayer().getName());
                        format = format.replace("%arrow%", "»");
                        format = format.replace("%message%", e.getMessage());


                        if(e.getPlayer().hasPermission("ic.placeholderChat") && e.isCancelled() == false)
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
                    else
                    {
                        // if no placeholderapi
                        format = format.replace("%prefix%", ConfigManager.groups.getString("groups." + groups));
                        format = format.replace("%player_name%", e.getPlayer().getName());
                        format = format.replace("%arrow%", "»");
                        format = format.replace("%message%", e.getMessage());
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
                        format = ConfigManager.settings.getString("settings.localChat.noGroupLocalChatFormat");
                    }
                    else
                    {
                        format = ConfigManager.settings.getString("settings.noGroupChatFormat");
                    }

                    //if papi
                    if (main.chat.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI"))
                    {

                        format = format.replace("%player_name%", e.getPlayer().getName());
                        format = format.replace("%arrow%", "»");
                        format = format.replace("%message%", e.getMessage());
                        if(e.getPlayer().hasPermission("ic.placeholderChat") && e.isCancelled() == false)
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
                    else
                    {
                        //if no papi
                        format = format.replace("%player_name%", e.getPlayer().getName());
                        format = format.replace("%arrow%", "»");
                        format = format.replace("%message%", e.getMessage());
                        if(!e.isCancelled())
                        {
                            sendChat(e.getPlayer(), format, e);
                            break;
                        }
                    }
                }
                perm = null;
            }
            //e.setCancelled(true);
        }
        if (ConfigManager.settings.getBoolean("settings.chatCooldown"))
        {


            this.cooldown.add(player);


            try
            {
                int cooldownSec = ConfigManager.settings.getInt("settings.chatCooldownTime") * 20;
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