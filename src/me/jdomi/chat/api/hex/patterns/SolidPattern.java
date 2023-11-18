package me.jdomi.chat.api.hex.patterns;

import me.jdomi.chat.api.hex.IridiumColorAPI;

import java.util.regex.Matcher;

public class SolidPattern implements Pattern
{
    java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("<SOLID:([0-9A-Fa-f]{6})>|#\\{([0-9A-Fa-f]{6})}");

    public String process(String string)
    {
        Matcher matcher = pattern.matcher(string);
        while (matcher.find())
        {
            String color = matcher.group(1);
            if (color == null) color = matcher.group(2);

            string = string.replace(matcher.group(), IridiumColorAPI.getColor(color) + "");
        }
        return string;
    }
}