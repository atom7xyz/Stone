package xyz.sorridi.stone.spigot.utils.items;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import xyz.sorridi.stone.common.utils.data.Array;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * A class for serializing and deserializing inventories.
 * Original source <a href="https://gist.github.com/graywolf336/8153678">here.</a>
 *
 * @author graywolf336
 * @since 1.0
 */
public class BukkitSerialization
{

    /**
     * Converts a player inventory to a String array of Base64 strings.
     * The first string is the content and the second string is the armor.
     *
     * @param playerInventory The player inventory to convert into a Base64 string array.
     * @return An array of strings: [main content, armor content]
     */
    public static String[] playerInventoryToBase64(PlayerInventory playerInventory) throws IllegalStateException
    {
        // Get the main content part (does not include the armor)
        String content = toBase64(playerInventory);
        String armor = itemStackArrayToBase64(playerInventory.getArmorContents());

        return Array.of(content, armor);
    }

    /**
     * Serializes an array of {@link ItemStack} objects to a Base64 string.
     * Based on {@link #toBase64(Inventory)}.
     *
     * @param items The array of ItemStacks to serialize to a Base64 string.
     * @return A Base64 string representing the serialized ItemStack array.
     */
    public static String itemStackArrayToBase64(ItemStack[] items) throws IllegalStateException
    {
        try
        {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Write the size of the inventory
            dataOutput.writeInt(items.length);

            // Save every element in the list
            for (ItemStack item : items)
            {
                dataOutput.writeObject(item);
            }

            // Serialize the array and return as a Base64 string
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        }
        catch (Exception e)
        {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    /**
     * Serializes an inventory to a Base64 string.
     * Special thanks to Comphenix in the Bukkit forums or also known
     * as aadnk on GitHub.
     * <a href="https://gist.github.com/aadnk/8138186">Original Source</a>
     *
     * @param inventory The inventory to serialize to a Base64 string.
     * @return A Base64 string representing the serialized inventory.
     */
    public static String toBase64(Inventory inventory) throws IllegalStateException
    {
        try
        {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Write the size of the inventory
            dataOutput.writeInt(inventory.getSize());

            // Save every element in the list
            for (int i = 0; i < inventory.getSize(); i++)
            {
                dataOutput.writeObject(inventory.getItem(i));
            }

            // Serialize the array and return as a Base64 string
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        }
        catch (Exception e)
        {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    /**
     * Converts a Base64 string back into an {@link Inventory}.
     * Special thanks to Comphenix in the Bukkit forums or also known
     * as aadnk on GitHub.
     * <a href="https://gist.github.com/aadnk/8138186">Original Source</a>
     *
     * @param data The Base64 string containing the serialized inventory.
     * @return The Inventory created from the Base64 string.
     * @throws IOException If there is an issue with decoding or reading the data.
     */
    public static Inventory fromBase64(String data) throws IOException
    {
        try
        {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            Inventory inventory = Bukkit.getServer().createInventory(null, dataInput.readInt());

            // Read the serialized inventory
            for (int i = 0; i < inventory.getSize(); i++)
            {
                inventory.setItem(i, (ItemStack) dataInput.readObject());
            }

            dataInput.close();
            return inventory;
        }
        catch (ClassNotFoundException e)
        {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    /**
     * Converts a Base64 string back into an array of {@link ItemStack} objects.
     * Based on {@link #fromBase64(String)}.
     *
     * @param data The Base64 string to convert to an array of ItemStacks.
     * @return An array of ItemStacks created from the Base64 string.
     * @throws IOException If there is an issue with decoding or reading the data.
     */
    public static ItemStack[] itemStackArrayFromBase64(String data) throws IOException
    {
        try
        {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];

            // Read the serialized inventory into an ItemStack array
            for (int i = 0; i < items.length; i++)
            {
                items[i] = (ItemStack) dataInput.readObject();
            }

            dataInput.close();
            return items;
        }
        catch (ClassNotFoundException e)
        {
            throw new IOException("Unable to decode class type.", e);
        }
    }

}
