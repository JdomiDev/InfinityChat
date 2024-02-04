package me.jdomi.chat.api.hex.patterns;

import me.jdomi.chat.api.hex.IridiumColorAPI;
import org.bukkit.Bukkit;

import java.util.regex.Matcher;

public class SolidPattern implements Pattern
{
    java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("<SOLID:([0-9A-Fa-f]{6})>|#\\{([0-9A-Fa-f]{6})}");

    java.util.regex.Pattern patternC = java.util.regex.Pattern.compile("&#([0-9A-Fa-f]{6})|#\\{([0-9A-Fa-f]{6})}");

    public String process(String string)
    {
        Matcher matcher = pattern.matcher(string);
        while (matcher.find())
        {
            String color = matcher.group(1);
            if (color == null) color = matcher.group(2);

            string = string.replace(matcher.group(), IridiumColorAPI.getColor(color) + "");
        }

        Matcher matcherC = patternC.matcher(string);
        while (matcherC.find())
        {
            String color = matcherC.group(1);
            if (color == null) color = matcherC.group(2);

            string = string.replace(matcherC.group(), IridiumColorAPI.getColor(color) + "");
        }

        return string;
    }
}