package fr.bukkit.effectkill.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import fr.bukkit.effectkill.Main;
import fr.bukkit.effectkill.effect.MainEffectKill;
import fr.bukkit.effectkill.effect.animation.Tornado;
import fr.bukkit.effectkill.effect.animation.DropSoup;
import fr.bukkit.effectkill.effect.animation.FrostFlame;
import fr.bukkit.effectkill.effect.animation.HeadExplode;
import fr.bukkit.effectkill.effect.animation.Heart;
import fr.bukkit.effectkill.effect.animation.Redstone;
import fr.bukkit.effectkill.effect.animation.Satan;
import fr.bukkit.effectkill.effect.animation.Wave;
import fr.bukkit.effectkill.effect.animation.Squid;
import fr.bukkit.effectkill.utils.config.YAMLUtils;
import fr.bukkit.effectkill.utils.inventory.CustomInventory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Manager {
	public static void buildConfigs(String... configs) {
		for (String config : configs) {
			if (config.equalsIgnoreCase("config")) {
				YAMLUtils yaml = YAMLUtils.get("config");
				if (yaml.getFile().exists()) return;
				yaml.build("This is the base config of EffectKill");

				yaml.getConfig().set("menu-item.give-on-join", false);
				yaml.getConfig().set("menu-item.slot", 3);
				yaml.getConfig().set("menu-item.type", "NETHER_STAR");
				yaml.getConfig().set("menu-item.name", "&6&lEffectKill");
				yaml.getConfig().set("putEffectKiller", true);
				yaml.save();
			} else if (config.equalsIgnoreCase("messages")) {
				YAMLUtils yaml = YAMLUtils.get("messages");
				if (yaml.getFile().exists()) return;
				yaml.build("Messages configuration");

				yaml.getConfig().set("prefix", "&8[&6EffectKill&8]");
				yaml.getConfig().set("no-permission", "%prefix% &cYou do not have the required permission!");
				yaml.getConfig().set("no-player", "%prefix% &cThis player %player% doesn't exists");
				yaml.getConfig().set("list-effect", "%prefix% &cThe Effect &e%effectname% &cdoes not exist. Here is the list of effect:&a ");
				yaml.getConfig().set("remove", "%prefix% &cYou deleted your effect");
				yaml.getConfig().set("spawn", "%prefix% &eYou spawned %effectname%");
				yaml.getConfig().set("menu.effectKill", "&7EffectKill - select");
				yaml.getConfig().set("menu.spawn", "&aSpawn");
				yaml.getConfig().set("menu.despawn", "&cDespawn");
				yaml.getConfig().set("menu.effect", "&fYour effect -> ");
				for (MainEffectKill effectKill : MainEffectKill.instanceList) {
					yaml.getConfig().set("effectKill."+effectKill.getName()+".name", effectKill.getDisplayName());
					yaml.getConfig().set("effectKill."+effectKill.getName()+".description", effectKill.getDescription());
				}
				yaml.save();
			}else throw new NullPointerException(config+" is not defined!");
		}
	}
	private Map<String,MainEffectKill> effectKills = new HashMap<>();

	public void loadMinions() {
		MainEffectKill.instanceList.addAll(Arrays.asList(
				new Heart(),
				new Wave(),
				new FrostFlame(),
				new Squid(),
				new Satan(),
				new DropSoup(),
				new Tornado(),
				new Redstone(),
				new HeadExplode()));
		MainEffectKill.instanceList.forEach(o -> MainEffectKill.effectList.add(o.getClass())); }
	
	public Map<String, MainEffectKill> getEffectKills() {
		return effectKills;
	}

	@SuppressWarnings("unchecked")
	public CustomInventory buildInventory(User user) {
		return new CustomInventory(Main.getInstance(), "effectkill", false, null, Utils.colorize((String) Utils.gfc("messages", "menu.effectKill")), 27).advManipule(customInventory -> {
			if (user.getEffectKill() != null) {
				ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
				SkullMeta skull = (SkullMeta) item.getItemMeta();
				skull.setDisplayName(Utils.colorize((String) Utils.gfc("messages", "menu.effect")) + user.getEffectKill().getDisplayName());
				skull.setOwner(user.getPlayer().getName());
				item.setItemMeta(skull);
				customInventory.addItem(item, 0);
				customInventory.addItem(ItemsUtils.create(Material.BARRIER, (byte)0, Utils.colorize((String) Utils.gfc("messages", "menu.despawn"))), 4);
			}
			int i = 9;
			for (MainEffectKill effectKills : MainEffectKill.instanceList) {
				String itemName = (String) Utils.gfc("messages", "menu.spawn");
				if (user.getEffectKill() != null && user.getEffectKill().getName().equalsIgnoreCase(effectKills.getName()))
					itemName = (String) Utils.gfc("messages", "menu.despawn");

				List<String> lores = ((List<String>) Utils.gfc("messages", "effectKill."+effectKills.getName()+".description")).stream().map(Utils::colorize).collect(Collectors.toList());
				customInventory.addItem(
						ItemsUtils.create(effectKills.getItemStack(), Utils.colorize(itemName + " " + Utils.gfc("messages", "effectKill."+effectKills.getName()+".name")), lores)
						, i);
				i++;
			}
		});
	}
}
