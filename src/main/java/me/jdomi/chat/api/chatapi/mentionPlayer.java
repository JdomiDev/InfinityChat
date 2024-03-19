package me.jdomi.chat.api.chatapi;

import me.jdomi.chat.api.config.configManager;
import me.jdomi.chat.api.hex.IridiumColorAPI;
import me.jdomi.chat.main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class mentionPlayer
{
    public static ConsoleCommandSender console = main.chat.getServer().getConsoleSender();

    public static void mentionCheck(Player sender, String messageC, boolean local)
    {
        // mention
        if (configManager.settings.getBoolean("settings.chatMention.enabled"))
        {
            boolean prefix = configManager.settings.getBoolean("settings.chatMention.onlyPrefix.enabled");
            String prefixString = configManager.settings.getString("settings.chatMention.onlyPrefix.prefix");

            for (org.bukkit.entity.Player Player : Bukkit.getOnlinePlayers())
            {
                if(prefix)
                {
                    if(messageC.contains(prefixString+Player.getName()))
                    {
                        if(!local)
                        {
                            try
                            {
                                int cooldownSec = configManager.settings.getInt("settings.chatMention.delay") * 20;

                                (new BukkitRunnable()
                                {
                                    public void run()
                                    {
                                        Player.playSound(Player.getLocation(), Sound.valueOf(configManager.settings.getString("settings.chatMention.sound").toUpperCase()), 100.0F, 1.0F);
                                        if(configManager.settings.getBoolean("settings.chatMention.sendMessage"))
                                        {
                                            Player.sendMessage(IridiumColorAPI.process(configManager.msg.getString("messages.mentionedPlayer").replace("%player%", sender.getDisplayName())));
                                        }
                                    }
                                }).runTaskLater(main.chat, cooldownSec);
                            }
                            catch (Exception ex)
                            {
                                console.sendMessage(IridiumColorAPI.process("&4Error with sound"));
                            }
                            break;
                        }
                        else if(configManager.settings.getInt("settings.localChat.localChatDistance") >= ((Player) sender).getLocation().distance(Player.getLocation()))
                        {
                            try
                            {
                                int cooldownSec = configManager.settings.getInt("settings.chatMention.delay") * 20;

                                (new BukkitRunnable()
                                {
                                    public void run()
                                    {
                                        Player.playSound(Player.getLocation(), Sound.valueOf(configManager.settings.getString("settings.chatMention.sound").toUpperCase()), 100.0F, 1.0F);
                                        if(configManager.settings.getBoolean("settings.chatMention.sendMessage"))
                                        {
                                            Player.sendMessage(IridiumColorAPI.process(configManager.msg.getString("messages.mentionedPlayer").replace("%player%", sender.getDisplayName())));
                                        }
                                    }
                                }).runTaskLater(main.chat, cooldownSec);
                            }
                            catch (Exception ex)
                            {
                                console.sendMessage(IridiumColorAPI.process("&4Error with sound"));
                            }
                            break;
                        }
                    }
                }
                else
                {
                    if(messageC.contains(Player.getName()))
                    {
                        float distance = Float.valueOf(configManager.settings.getInt("settings.localChat.localChatDistance"));

                        if(!local)
                        {
                            try
                            {
                                int cooldownSec = configManager.settings.getInt("settings.chatMention.delay") * 20;

                                (new BukkitRunnable()
                                {
                                    public void run()
                                    {
                                        Player.playSound(Player.getLocation(), Sound.valueOf(configManager.settings.getString("settings.chatMention.sound").toUpperCase()), 100.0F, 1.0F);
                                        if(configManager.settings.getBoolean("settings.chatMention.sendMessage"))
                                        {
                                            Player.sendMessage(IridiumColorAPI.process(configManager.msg.getString("messages.mentionedPlayer").replace("%player%", sender.getDisplayName())));
                                        }
                                    }
                                }).runTaskLater((Plugin)main.chat, cooldownSec);
                            }
                            catch (Exception ex)
                            {
                                console.sendMessage(IridiumColorAPI.process("&4Error with sound"));
                            }
                            break;
                        }
                        else if(configManager.settings.getInt("settings.localChat.localChatDistance") >= ((Player) sender).getLocation().distance(Player.getLocation()))
                        {
                            try
                            {
                                int cooldownSec = configManager.settings.getInt("settings.chatMention.delay") * 20;

                                (new BukkitRunnable()
                                {
                                    public void run()
                                    {
                                        Player.playSound(Player.getLocation(), Sound.valueOf(configManager.settings.getString("settings.chatMention.sound").toUpperCase()), 100.0F, 1.0F);
                                        if(configManager.settings.getBoolean("settings.chatMention.sendMessage"))
                                        {
                                            Player.sendMessage(IridiumColorAPI.process(configManager.msg.getString("messages.mentionedPlayer").replace("%player%", sender.getDisplayName())));
                                        }
                                    }
                                }).runTaskLater((Plugin)main.chat, cooldownSec);
                            }
                            catch (Exception ex)
                            {
                                console.sendMessage(IridiumColorAPI.process("&4Error with sound"));
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
}
