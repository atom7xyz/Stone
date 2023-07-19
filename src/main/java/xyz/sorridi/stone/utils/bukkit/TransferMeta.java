package xyz.sorridi.stone.utils.bukkit;

import lombok.NonNull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * ItemMeta utilities.
 *
 * @author Sorridi
 * @since 1.0
 */
public class TransferMeta
{

    /**
     * Transfers the name from an item to another.
     *
     * @param from The item to get the name from.
     * @param to   The item to set the name to.
     */
    public static void name_v1_8(@NonNull ItemStack from, @NonNull ItemStack to)
    {
        ItemMeta meta = to.getItemMeta();

        meta.setDisplayName(from.getItemMeta().getDisplayName());
        to.setItemMeta(meta);
    }

    /**
     * Transfers the lore from an item to another.
     *
     * @param from The item to get the lore from.
     * @param to   The item to set the lore to.
     */
    public static void lore_v1_8(@NonNull ItemStack from, @NonNull ItemStack to)
    {
        ItemMeta meta = to.getItemMeta();

        meta.setLore(from.getItemMeta().getLore());
        to.setItemMeta(meta);
    }

    /**
     * Transfers the name from an item to another.
     *
     * @param from The item to get the name from.
     * @param to   The item to set the name to.
     */
    public static void name_v1_13(@NonNull ItemStack from, @NonNull ItemStack to)
    {
        ItemMeta meta = to.getItemMeta();

        meta.displayName(from.getItemMeta().displayName());
        to.setItemMeta(meta);
    }

    /**
     * Transfers the lore from an item to another.
     *
     * @param from The item to get the lore from.
     * @param to   The item to set the lore to.
     */
    public static void lore_v1_13(@NonNull ItemStack from, @NonNull ItemStack to)
    {
        ItemMeta meta = to.getItemMeta();

        meta.lore(from.getItemMeta().lore());
        to.setItemMeta(meta);
    }

}
