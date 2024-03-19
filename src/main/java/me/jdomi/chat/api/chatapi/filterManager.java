package me.jdomi.chat.api.chatapi;

import me.jdomi.chat.api.config.configManager;
import me.jdomi.chat.api.hex.IridiumColorAPI;
import me.jdomi.chat.main;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

public class filterManager
{
    public static ArrayList<Player> cooldown = new ArrayList<>();

    public static HashMap<Player, String> previousMessages = new HashMap<>();

    public static ConsoleCommandSender console = main.chat.getServer().getConsoleSender();

    public static boolean getBoolean(Player player, String message) {
        boolean isMuted = false;

        String messageC = message.toLowerCase();

        // ismuted
        if (configManager.mute.isSet("muted-players." + player.getName()))
        {
            if (configManager.mute.getBoolean("muted-players." + player.getName()))
            {
                player.sendMessage(IridiumColorAPI.process(configManager.settings.getString("plugin-prefix") + configManager.msg.getString("messages.playerIsMuted")));
                isMuted = true;
            }
        }

        // antiswear
        if (configManager.settings.getBoolean("settings.moderation.antiSwear") && !isMuted)
        {
            if (!player.hasPermission("ic.cooldown.bypass") || !player.isOp())
            {
                for (String blockedWord : configManager.words.getStringList("censored-words"))
                {


                    if (messageC.contains(blockedWord.toLowerCase()))
                    {
                        boolean nextSpaceCheck = false;
                        boolean prevSpaceCheck = false;

                        int sub = messageC.indexOf(blockedWord.toLowerCase());

                        char nextLetter;
                        char previousLetter;

                        //prev char check
                        if (sub > 0)
                        {
                            previousLetter = messageC.charAt(sub - 1);
                            if (previousLetter == ' ')
                            {
                                prevSpaceCheck = true;
                            }
                        }
                        else
                        {
                            prevSpaceCheck = true;
                        }


                        //next char check
                        if (sub + blockedWord.length() < messageC.length())
                        {
                            nextLetter = messageC.charAt(sub + blockedWord.length());
                            if (nextLetter == ' ')
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
                            player.sendMessage(IridiumColorAPI.process(configManager.settings.getString("plugin-prefix") + configManager.msg.getString("messages.swear").replace("%word%", blockedWord.toLowerCase())));
                            isMuted = true;
                            break;
                        }
                    }
                }
            }

        }
        //antiswear
        // cooldown
        if (configManager.settings.getBoolean("settings.moderation.chatCooldown.enabled") && !isMuted)
        {
            if (!player.hasPermission("ic.cooldown.bypass") || !player.isOp())
            {
                if (cooldown.contains(player))
                {

                    player.sendMessage(IridiumColorAPI.process(configManager.settings.getString("plugin-prefix") + configManager.msg.getString("messages.spam")));
                    isMuted = true;
                }
            }
        }
        // cooldown
        // anti spam
        if (configManager.settings.getBoolean("settings.moderation.antiChatRepeat") && !isMuted)
        {
            if (!player.hasPermission("ic.cooldown.bypass") || !player.isOp())
            {
                if (previousMessages.containsKey(player))
                {
                    if (message.equalsIgnoreCase(previousMessages.get(player)))
                    {
                        player.sendMessage(IridiumColorAPI.process(configManager.settings.getString("plugin-prefix") + configManager.msg.getString("messages.spam")));
                        isMuted = true;
                    }
                }

                previousMessages.put(player, message);
            }
        }
        // anti spam

        if (configManager.settings.getBoolean("settings.moderation.chatCooldown.enabled"))
        {


            cooldown.add(player);


            try
            {
                int cooldownSec = configManager.settings.getInt("settings.moderation.chatCooldown.cooldownTime") * 20;
                (new BukkitRunnable()
                {
                    public void run()
                    {
                        cooldown.remove(player);
                    }
                }).runTaskLater((Plugin) main.chat, cooldownSec);
            }
            catch (Exception ex)
            {


                console.sendMessage(IridiumColorAPI.process("&4Error with delay in config"));
            }
        }

        return isMuted;
    }
}