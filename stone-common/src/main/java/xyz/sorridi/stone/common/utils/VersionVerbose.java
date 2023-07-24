package xyz.sorridi.stone.common.utils;

import lombok.Getter;
import org.apache.commons.codec.digest.DigestUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.sorridi.stone.common.immutable.Err;
import xyz.sorridi.stone.common.utils.data.Array;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@Getter
public abstract class VersionVerbose<P, D>
{
    private static final String[] TO_REPLACE_ARRAY = Array.of("[", "]");

    protected String hash;
    protected P plugin;

    public VersionVerbose(P plugin)
    {
        this.plugin = plugin;
    }

    @NotNull
    public abstract D getDescriptionFile();

    @NotNull
    public abstract String getName();

    @NotNull
    public abstract String getVersion();

    @Nullable
    public abstract String getSite();

    @Nullable
    public abstract String getAuthors();

    @Nullable
    public abstract String getDescription();

    @Nullable
    public abstract List<String> getRawAuthors();

    @Nullable
    public abstract List<String> getDepends();

    @Nullable
    public abstract List<String> getSoftDepends();

    /**
     * Gets the hash of the plugin.
     *
     * @param length The length of the hash.
     * @return The hash of the plugin.
     */
    public final String calculateHash(int length)
    {
        checkArgument(length > 0, Err.ZERO.get());

        String fileName = getName() + "-" + getVersion() + ".jar";
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

    public String getHash()
    {
        if (hash == null)
        {
            hash = calculateHash(6);
        }

        return hash;
    }

}