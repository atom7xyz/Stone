package xyz.sorridi.stone;

import lombok.SneakyThrows;
import me.lucko.helper.Commands;
import me.lucko.helper.internal.HelperImplementationPlugin;
import me.lucko.helper.maven.MavenLibrary;
import me.lucko.helper.maven.Repository;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import me.lucko.helper.plugin.ap.Plugin;
import me.lucko.helper.plugin.ap.PluginDependency;
import org.bukkit.plugin.PluginLoadOrder;
import xyz.sorridi.stone.annotations.impl.serializer.SerializerProcessor;
import xyz.sorridi.stone.commands.StoneCommand;

/* Data structures */
@MavenLibrary(groupId = "commons-codec", artifactId = "commons-codec", version = "1.15")
@MavenLibrary(groupId = "com.google.guava", artifactId = "guava", version = "31.1-jre")
@MavenLibrary(groupId = "org.apache.commons", artifactId = "commons-math3", version = "3.6.1")
@MavenLibrary(groupId = "org.apache.commons", artifactId = "commons-collections4", version = "4.4")

/* Reflection related */
@MavenLibrary(groupId = "org.javassist", artifactId = "javassist", version = "3.29.2-GA")
@MavenLibrary(groupId = "org.reflections", artifactId = "reflections", version = "0.10.2")

/* Database related */
@MavenLibrary(groupId = "org.jooq", artifactId = "jooq", version = "3.18.0")
@MavenLibrary(groupId = "com.zaxxer", artifactId = "HikariCP", version = "5.0.1")
@MavenLibrary(groupId = "org.mariadb.jdbc", artifactId = "mariadb-java-client", version = "3.0.6")
@MavenLibrary(groupId = "com.mysql", artifactId = "mysql-connector-j", version = "8.0.33")

/* Minecraft related */
@MavenLibrary(groupId = "com.mojang",
              artifactId = "authlib",
              version = "1.5.25",
              repo = @Repository(url = "https://repo.papermc.io/repository/maven-public/"))

@MavenLibrary(groupId = "io.papermc",
              artifactId = "paperlib",
              version = "1.0.7",
              repo = @Repository(url = "https://repo.papermc.io/repository/maven-public/"))

@MavenLibrary(groupId = "pl.mikigal",
              artifactId = "ConfigAPI",
              version = "1.2.3",
              repo = @Repository(url = "https://repo.mikigal.pl/releases"))

@Plugin(name = "Stone",
        version = "1.0-SNAPSHOT",
        description = "Yet another spigot plugin library.",
        authors = "Sorridi",
        website = "https://sorridi.xyz",
        load = PluginLoadOrder.STARTUP,
        depends = @PluginDependency("helper"))

@HelperImplementationPlugin
public final class Stone extends ExtendedJavaPlugin
{

    @Override
    @SneakyThrows
    public void enable()
    {
        new SerializerProcessor(this).process();

        Commands.create()
                .description("Stone command.")
                .handler(new StoneCommand(this))
                .registerAndBind(this, "stone");
    }

    @Override
    public void disable()
    {
        // Plugin shutdown logic
    }

}
