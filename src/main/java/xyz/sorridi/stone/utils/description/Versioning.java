package xyz.sorridi.stone.utils.description;

import lombok.Getter;
import lombok.val;
import org.apache.commons.codec.digest.DigestUtils;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import xyz.sorridi.stone.immutable.ErrorMessages;
import xyz.sorridi.stone.utils.data.Array;
import xyz.sorridi.stone.utils.Replace;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Versioning utilities.
 * @author Sorridi
 * @since 1.0
 */
@Getter
public class Versioning
{
    private final List<String>  rawAuthors, depends, softDepends;
    private final String        name, version, site, description, hash, authors;

    public Versioning(@NotNull Plugin plugin)
    {
        val desc = plugin.getDescription();
        
        name        = desc.getName();
        version     = desc.getVersion();
        rawAuthors  = desc.getAuthors();
        site        = desc.getWebsite();
        description = desc.getDescription();
        depends     = desc.getDepend();
        softDepends = desc.getSoftDepend();
        hash        = getHash(8);
        authors     = getFormattedAuthors();
    }

    /**
     * Gets the formatted authors of the plugin.
     * @return The formatted authors of the plugin.
     */
    private String getFormattedAuthors()
    {
        return Replace.of(rawAuthors, Array.of("[", "]"), "", "").toString();
    }

    /**
     * Gets the hash of the plugin.
     * @param length The length of the hash.
     * @return The hash of the plugin.
     */
    public String getHash(int length)
    {
        checkArgument(length > 0, ErrorMessages.ZERO.get());

        String fileName = name + "-" + version + ".jar";
        String fileHash = "unknown hash";

        try
        {
            byte[] file = Files.readAllBytes(Paths.get("./plugins/" + fileName));
            fileHash = DigestUtils.md5Hex(file).toLowerCase().substring(0, length);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return fileHash;
    }

}
