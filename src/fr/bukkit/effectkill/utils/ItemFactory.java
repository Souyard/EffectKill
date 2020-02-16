package fr.bukkit.effectkill.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class ItemFactory{
    public static ItemStack create(Material material, byte data, String displayName, String... lore) {
        @SuppressWarnings("deprecation")
		ItemStack itemStack = new MaterialData(material, data).toItemStack(1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        if (lore != null) {
            List<String> finalLore = new ArrayList<String>();
            for (final String s : lore) {
                finalLore.add(s);
            }
            itemMeta.setLore(finalLore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    
    public static ItemStack create(Material material, byte data, String displayName) {
        return create(material, data, displayName, (String[])null);
    }
}
