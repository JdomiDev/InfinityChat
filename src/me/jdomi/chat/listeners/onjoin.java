package me.jdomi.chat.listeners;

import java.util.List;
import me.clip.placeholderapi.PlaceholderAPI;
import me.jdomi.chat.api.config.ConfigManager;
import me.jdomi.chat.api.hex.IridiumColorAPI;
import me.jdomi.chat.main;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import static me.jdomi.chat.api.config.ConfigManager.console;


public class onjoin implements Listener
{
    main plugin;

    @EventHandler
    public void onjoin(final PlayerJoinEvent e)
    {
        if (ConfigManager.settings.getString("settings.updateChecking").equalsIgnoreCase("true") && (e.getPlayer().hasPermission("ic.*") || e.getPlayer().isOp()))
        {
            if (main.updateAvailable)
            {
                e.getPlayer().sendMessage(IridiumColorAPI.process(ConfigManager.settings.getString("plugin-prefix") + "&cV" + main.updateVer + " "+ConfigManager.msg.getString("messages.updateAvailable")));
                e.getPlayer().sendMessage(IridiumColorAPI.process("&chttps://modrinth.com/plugin/infinitychat/version/latest"));
            }
        }
        if (ConfigManager.settings.getString("settings.joinParticle.enabled").equalsIgnoreCase("true"))
        {
            try
            {
                int cooldownSec = Integer.valueOf(ConfigManager.settings.getString("settings.joinParticle.delay")).intValue() * 20;
                int multiplier = Integer.valueOf(ConfigManager.settings.getString("settings.joinParticle.multiplier")).intValue() * 20;
                String particleType = ConfigManager.settings.getString("settings.joinParticle.particle").toUpperCase();

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
        if (ConfigManager.settings.getString("settings.joinFormat").equalsIgnoreCase("true"))
        {
            if (main.chat.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI"))
            {
                e.setJoinMessage("");

                String format = ConfigManager.msg.getString("messages.joinFormat");
                String replaced = PlaceholderAPI.setPlaceholders(e.getPlayer(), format);

                Bukkit.broadcastMessage(IridiumColorAPI.process(replaced));
            }
            else
            {
                e.setJoinMessage("");

                String format = ConfigManager.msg.getString("messages.joinFormat");
                String replaced = format.replace("%player_name%", e.getPlayer().getDisplayName());

                Bukkit.broadcastMessage(IridiumColorAPI.process(replaced));
            }
        }

        if (ConfigManager.settings.getString("settings.joinSound.enabled").equalsIgnoreCase("true"))
        {

            try
            {
                int cooldownSec = Integer.valueOf(ConfigManager.settings.getString("settings.joinSound.delay")).intValue() * 20;

                (new BukkitRunnable()
                {
                    public void run()
                    {
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.valueOf(ConfigManager.settings.getString("settings.joinSound.sound").toUpperCase()), 1.0F, 1.0F);
                    }
                }).runTaskLater((Plugin)main.chat, cooldownSec);
            }
            catch (Exception ex)
            {
                console.sendMessage(IridiumColorAPI.process("&4Error with sound"));
            }
        }

        if (ConfigManager.settings.getString("settings.motd").equalsIgnoreCase("true"))
        {
            final List<String> motdLines = ConfigManager.msg.getStringList("motdLines");

            try
            {
                int cooldownSec = Integer.valueOf(ConfigManager.settings.getString("settings.motdDelayTime")).intValue() * 20;

                (new BukkitRunnable()
                {
                    public void run()
                    {
                        if (main.chat.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {


                            for (String str : motdLines)
                            {
                                String replaced = PlaceholderAPI.setPlaceholders(e.getPlayer(), str);
                                e.getPlayer().sendMessage(IridiumColorAPI.process(replaced));

                            }

                        }
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