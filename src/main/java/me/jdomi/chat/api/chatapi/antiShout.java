package me.jdomi.chat.api.chatapi;

import me.jdomi.chat.api.config.configManager;
import org.bukkit.entity.Player;

public class antiShout
{
    public static String getString(Player player, String msg)
    {
        // anti shout
        if (configManager.settings.getBoolean("settings.moderation.antiCapsLockSpam.enabled"))
        {
            if(!player.hasPermission("ic.shout") || !player.isOp())
            {
                String regex = "[^a-zA-Z0-9\\s]";
                String message = msg.replace(" ", "");
                message = message.replaceAll(regex,"");

                int charCount = message.length();
                int capLetCound = 0;

                for (int i = 0; i < charCount; i++)
                {
                    char currentChar = message.charAt(i);
                    if(Character.isUpperCase(currentChar))
                    {
                        capLetCound++;
                    }
                }
                double percentage = (double) capLetCound / charCount * 100;

                if(percentage >= configManager.settings.getInt("settings.moderation.antiCapsLockSpam.upperCasePercentage"))
                {
                    msg = msg.toLowerCase();
                    msg = msg.substring(0,1).toUpperCase() + msg.substring(1);
                    return msg;
                }
            }
        }
        return msg;
    }
}
