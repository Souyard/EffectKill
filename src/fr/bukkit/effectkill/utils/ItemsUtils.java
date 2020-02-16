package fr.bukkit.effectkill.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

import fr.bukkit.effectkill.utils.maths.MathUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemsUtils{
	
	private Material m;
	private byte data;
	private String name;

	public ItemsUtils(Material m, byte data, String name) {
		this.m = m;
		this.data = data;
		this.name = name;
	}

	public static ItemStack getColorArmor(Material material, Color color) {
		ItemStack itemStack = new ItemStack(material, 1);
		LeatherArmorMeta itemMeta = (LeatherArmorMeta)itemStack.getItemMeta();
		itemMeta.setColor(color);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}


	public static ItemStack getSkull(String s) { return getSkull(s, null); }


	public static ItemStack setName(ItemStack itemStack, String name) {
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(name);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	public static ItemStack getNamedSkull(String name) {
		ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
		SkullMeta skullMeta = (SkullMeta)itemStack.getItemMeta();
		skullMeta.setOwner(name);
		itemStack.setItemMeta(skullMeta);
		return itemStack;
	}

	public static ItemStack getSkullOwner(String owner) {
		ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
		SkullMeta itemMeta = (SkullMeta)itemStack.getItemMeta();
		itemMeta.setOwner(owner);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	public static ItemStack getSkull(String texture, String name) {
		ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
		SkullMeta skullMeta = (SkullMeta)itemStack.getItemMeta();
		GameProfile gameProfile = new GameProfile(UUID.randomUUID(), (String)null);
		PropertyMap propertyMap = gameProfile.getProperties();
		if (name != null) skullMeta.setDisplayName(Utils.colorize(name)); 
		propertyMap.put("textures", new Property("textures", texture));
		try {
			Field field = skullMeta.getClass().getDeclaredField("profile");
			field.setAccessible(true);
			field.set(skullMeta, gameProfile);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		itemStack.setItemMeta(skullMeta);
		return itemStack;
	}

	public static ColorData getGreatRandomColor() {
		Color color = null;
		byte b = 0;
		switch (MathUtils.randomRange(0, 8)) {
		case 0:
			b = 0;
			color = Color.WHITE;
			break;

		case 1:
			b = 1;
			color = Color.ORANGE;
			break;

		case 2:
			b = 2;
			color = Color.FUCHSIA;
			break;

		case 3:
			b = 4;
			color = Color.YELLOW;
			break;

		case 4:
			b = 5;
			color = Color.GREEN;
			break;

		case 5:
			b = 9;
			color = Color.NAVY;
			break;

		case 6:
			b = 10;
			color = Color.PURPLE;
			break;

		case 7:
			b = 11;
			color = Color.BLUE;
			break;

		case 8:
			b = 14;
			color = Color.RED;
			break;
		} 
		return new ColorData(b, color);
	}

	public ItemStack getItem() {
		ItemStack itemStack = new ItemStack(this.m, 1, (short)this.data);
		if (this.name != null) setDisplayName(itemStack, Utils.colorize(this.name)); 
		return itemStack;
	}

	public static ItemStack setDisplayName(ItemStack itemStack, String s) {
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(Utils.colorize(s));
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	public static void clearArmor(Player p) {
		p.getInventory().setHelmet(null);
		p.getInventory().setChestplate(null);
		p.getInventory().setLeggings(null);
		p.getInventory().setBoots(null);
	}
	public static ItemStack create(ItemStack item, String name, List<String> lore) {
		ItemStack itemStack = item;
		SkullMeta skullMeta = (SkullMeta)itemStack.getItemMeta();
		skullMeta.setDisplayName(name);
		if (lore != null)
			skullMeta.setLore(lore); 
		itemStack.setItemMeta(skullMeta);
		return itemStack;
	}
	public static ItemStack create(Material material, byte data, String name, String... lore) {
		ItemStack itemStack = new ItemStack(material, 1, (short)data);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(name);
		if (lore != null)
			itemMeta.setLore(Arrays.asList(lore)); 
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	public static class ColorData
	{
		private byte data;
		private Color color;

		public ColorData(byte data, Color color) {
			this.data = data;
			this.color = color;
		}


		public byte getData() { return this.data; }



		public Color getColor() { return this.color; }
	}
}
