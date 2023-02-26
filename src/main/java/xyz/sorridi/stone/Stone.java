package xyz.sorridi.stone;

import lombok.SneakyThrows;
import me.lucko.helper.internal.HelperImplementationPlugin;
import me.lucko.helper.maven.MavenLibrary;
import me.lucko.helper.maven.Repository;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import me.lucko.helper.plugin.ap.Plugin;
import me.lucko.helper.plugin.ap.PluginDependency;
import org.bukkit.plugin.PluginLoadOrder;
import xyz.sorridi.stone.annotations.impl.serializer.SerializerProcessor;

//@MavenLibrary(groupId = "me.lucko", artifactId = "helper-sql", version = "1.3.0")

@MavenLibrary(groupId = "commons-codec", artifactId = "commons-codec", version = "1.15")
@MavenLibrary(groupId = "com.google.guava", artifactId = "guava", version = "31.1-jre")
@MavenLibrary(groupId = "org.apache.commons", artifactId = "commons-math3", version = "3.6.1")

@MavenLibrary(groupId = "org.javassist", artifactId = "javassist", version = "3.29.2-GA")
@MavenLibrary(groupId = "org.reflections", artifactId = "reflections", version = "0.10.2")

@MavenLibrary(groupId = "org.jooq", artifactId = "jooq", version = "3.17.6")
@MavenLibrary(groupId = "com.zaxxer", artifactId = "HikariCP", version = "5.0.1")
@MavenLibrary(groupId = "org.mariadb.jdbc", artifactId = "mariadb-java-client", version = "3.0.6")

@MavenLibrary(
        groupId = "com.mojang",
        artifactId = "authlib",
        version = "1.5.25",
        repo = @Repository(url = "https://repo.papermc.io/repository/maven-public/")
)

@MavenLibrary(
        groupId = "io.papermc",
        artifactId = "paperlib",
        version = "1.0.7",
        repo = @Repository(url = "https://repo.papermc.io/repository/maven-public/")
)

@MavenLibrary(
        groupId = "pl.mikigal",
        artifactId = "ConfigAPI",
        version = "1.2.3",
        repo = @Repository(url = "https://repo.mikigal.pl/releases")
)

@MavenLibrary(
        groupId = "com.github.bwaldvogel",
        artifactId = "base91",
        version = "26d6fc6207",
        repo = @Repository(url = "https://www.jitpack.io")
)

@Plugin(
        name = "Stone",
        version = "1.0-SNAPSHOT",
        description = "Yet another spigot plugin library.",
        authors = "Sorridi",
        website = "https://sorridi.xyz/plugins/stone",
        load = PluginLoadOrder.STARTUP,
        depends = @PluginDependency("helper")
)

@HelperImplementationPlugin
public final class Stone extends ExtendedJavaPlugin
{

    @Override
    @SneakyThrows
    public void enable()
    {
        new SerializerProcessor(this).process();
    }

    @Override
    public void disable()
    {
        // Plugin shutdown logic
    }

}
