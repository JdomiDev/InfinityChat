package me.jdomi.chat.listeners;

import java.util.List;
import me.clip.placeholderapi.PlaceholderAPI;
import me.jdomi.chat.api.config.configManager;
import me.jdomi.chat.api.hex.IridiumColorAPI;
import me.jdomi.chat.main;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import static me.jdomi.chat.api.config.configManager.console;


public class onJoin implements Listener
{
    @EventHandler
    public void onjoin(final PlayerJoinEvent e)
    {
        // update check
        if (configManager.settings.getBoolean("settings.updateChecking") && (e.getPlayer().hasPermission("ic.*") || e.getPlayer().isOp()))
        {
            if (main.updateAvailable)
            {
                e.getPlayer().sendMessage(IridiumColorAPI.process(configManager.settings.getString("plugin-prefix") + "&cV" + main.updateVer + " "+ configManager.msg.getString("messages.updateAvailable")));
                e.getPlayer().sendMessage(IridiumColorAPI.process("&chttps://modrinth.com/plugin/infinitychat/version/latest"));
            }
        }
        // particle
        if (configManager.settings.getBoolean("settings.joinParticle.enabled"))
        {
            try
            {
                int cooldownSec = configManager.settings.getInt("settings.joinParticle.delay") * 20;
                int multiplier = configManager.settings.getInt("settings.joinParticle.multiplier") * 20;
                String particleType = configManager.settings.getString("settings.joinParticle.particle").toUpperCase();

                (new BukkitRunnable()
                {
                    public void run()
                    {
                        e.getPlayer().getLocation().getWorld().spawnParticle(Particle.valueOf(particleType), e.getPlayer().getLocation() ,multiplier);
                    }
                }).runTaskLater((Plugin)main.chat, cooldownSec);

            }
            catch(Exception ex)
            {
                console.sendMessage("&4Error Spawning Particles!");
            }
        }
        // join format
        if (configManager.settings.getBoolean("settings.formatting.joinFormat"))
        {
            if (main.chat.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI"))
            {
                e.setJoinMessage("");

                String format = configManager.msg.getString("messages.joinFormat");
                String replaced = PlaceholderAPI.setPlaceholders(e.getPlayer(), format);

                Bukkit.broadcastMessage(IridiumColorAPI.process(replaced));
            }
            else
            {
                e.setJoinMessage("");

                String format = configManager.msg.getString("messages.joinFormat");
                String replaced = format.replace("%player_name%", e.getPlayer().getDisplayName());

                Bukkit.broadcastMessage(IridiumColorAPI.process(replaced));
            }
        }
        // join sound
        if (configManager.settings.getBoolean("settings.joinSound.enabled"))
        {
            // no global
            if(!configManager.settings.getBoolean("settings.joinSound.global"))
            {
                try
                {
                    int cooldownSec = configManager.settings.getInt("settings.joinSound.delay") * 20;

                    (new BukkitRunnable()
                    {
                        public void run()
                        {
                            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.valueOf(configManager.settings.getString("settings.joinSound.sound").toUpperCase()), 100.0F, 1.0F);
                        }
                    }).runTaskLater((Plugin)main.chat, cooldownSec);
                }
                catch (Exception ex)
                {
                    console.sendMessage(IridiumColorAPI.process("&4Error with sound"));
                }
            }
            // global
            else if(configManager.settings.getBoolean("settings.joinSound.global"))
            {
                try
                {
                    int cooldownSec = configManager.settings.getInt("settings.joinSound.delay") * 20;

                    (new BukkitRunnable()
                    {
                        public void run()
                        {
                            for(Player player : Bukkit.getServer().getOnlinePlayers())
                            {
                                player.playSound(player.getLocation(), Sound.valueOf(configManager.settings.getString("settings.joinSound.sound").toUpperCase()), 100.0F, 1.0F);
                            }
                        }

                    }).runTaskLater((Plugin)main.chat, cooldownSec);
                }
                catch (Exception ex)
                {
                    console.sendMessage(IridiumColorAPI.process("&4Error with sound"));
                }
            }
        }
        // motd
        if (configManager.settings.getBoolean("settings.motd.enabled"))
        {
            final List<String> motdLines = configManager.msg.getStringList("motdLines");

            try
            {
                int cooldownSec = configManager.settings.getInt("settings.motd.delay") * 20;

                (new BukkitRunnable()
                {
                    public void run()
                    {
                        // if papi
                        if (main.chat.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {


                            for (String str : motdLines)
                            {
                                String replaced = PlaceholderAPI.setPlaceholders(e.getPlayer(), str);
                                e.getPlayer().sendMessage(IridiumColorAPI.process(replaced));

                            }

                        }
                        // no papi
                        else {

                            for (String str : motdLines) {


                                String replaced1 = str.replace("%player_name%", e.getPlayer().getDisplayName());
                                String replaced2 = replaced1.replace("%online_players%", String.valueOf(main.chat.getServer().getOnlinePlayers().size()));
                                String replaced3 = replaced2.replace("%server_max_players%", String.valueOf(main.chat.getServer().getMaxPlayers()));
                                String replaced4 = replaced3.replace("%statistic_time_played:hours%", String.valueOf(Statistic.PLAY_ONE_MINUTE));
                                e.getPlayer().sendMessage(IridiumColorAPI.process(replaced4));
                            }
                        }
                    }
                }).runTaskLater((Plugin)main.chat, cooldownSec);
            }
            catch (Exception ex) {

                console.sendMessage(IridiumColorAPI.process("&4Error with delay in config"));
            }
        }
    }
}