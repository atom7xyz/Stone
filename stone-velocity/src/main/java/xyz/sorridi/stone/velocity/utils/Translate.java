package xyz.sorridi.stone.velocity.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import xyz.sorridi.stone.common.utils.Replace;

import java.util.Arrays;
import java.util.Collection;

/**
 * Translate color codes.
 *
 * @author atom7xyz
 * @since 1.0
 */
public class Translate
{
    /**
     * Translates colors in a text with &.
     *
     * @param text The text to translate.
     * @return The translated text.
     */
    public static Component colors(String text)
    {
        return Component.text(Replace.of(text, "&", "§"));
    }

    /**
     * Translates colors in a text with &.
     *
     * @param text The text to translate.
     * @return The translated text.
     */
    public static Component colors(String ...text)
    {
        return colors(Arrays.stream(text).toList());
    }

    /**
     * Translate colors in a list of text with &.
     *
     * @param text The text to translate.
     * @param <T>  The type of the collection.
     * @return The translated text.
     */
    public static <T extends Collection<String>> Component colors(T text)
    {
        T translated = Replace.of(text, "&", "§");
        var result = Component.text();
        int counter = 0;

        for (String line : translated)
        {
            result.append(Component.text(line));

            if (counter++ < translated.size() - 1)
            {
                result.append(Component.newline());
            }
        }

        return result.build();
    }

    /**
     * Translate colors in a text component with & preserving the original component.
     *
     * @param component The component to translate.
     * @return The translated component.
     */
    public static TextComponent colorsKeep(TextComponent component)
    {
        return component.content(Replace.of(component.content(), "&", "§"));
    }

    /**
     * Gets the mini message instance.
     * @return The mini message instance.
     */
    public static MiniMessage getMiniMessage()
    {
        return MiniMessage.miniMessage();
    }

    /**
     * Deserializes using mini message a text.
     *
     * @param text The text to deserialize.
     * @return The deserialized text.
     */
    public static Component mini(String text)
    {
        return getMiniMessage().deserialize(text);
    }

    /**
     * Deserializes using mini message a text with the given prefix.
     *
     * @param text The text to deserialize.
     * @return The deserialized text.
     */
    public static Component mini(Component prefix, String text)
    {
        return Component.text()
                        .append(prefix)
                        .append(mini(text))
                        .build();
    }

    /**
     * Deserializes using mini message a text with tag resolvers.
     *
     * @param text         The text to deserialize.
     * @param tagResolvers The tag resolvers to use.
     * @return The deserialized text.
     */
    public static Component mini(String text, TagResolver... tagResolvers)
    {
        return getMiniMessage().deserialize(text, tagResolvers);
    }

    /**
     * Deserializes using mini message with a line break between each line.
     *
     * @param text The text to deserialize.
     * @return The deserialized text.
     */
    public static Component mini(String... text)
    {
        return mini(String.join("<br>", text));
    }

    /**
     * Deserializes using mini message with a line break between each line.
     *
     * @param text The text to deserialize.
     * @param <T>  The type of the collection.
     * @return The deserialized text.
     */
    public static <T extends Collection<String>> Component mini(T text)
    {
        return mini(String.join("<br>", text));
    }
}
