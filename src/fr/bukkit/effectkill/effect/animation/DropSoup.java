package fr.bukkit.effectkill.effect.animation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.util.Vector;

import fr.bukkit.effectkill.Main;
import fr.bukkit.effectkill.effect.MainEffectKill;
import fr.bukkit.effectkill.utils.ItemFactory;
import fr.bukkit.effectkill.utils.Particle;
import fr.bukkit.effectkill.utils.User;
import fr.bukkit.effectkill.utils.Utils;
import fr.bukkit.effectkill.utils.config.YAMLUtils;
import fr.bukkit.effectkill.utils.inventory.Heads;

public class DropSoup extends MainEffectKill{

	Random r = new Random();
	ArrayList<Item> items = new ArrayList<Item>();

	public DropSoup() {
		super("dropsoup", YAMLUtils.get("messages").getFile().exists()?((String) Utils.gfc("messages", "effectKill.dropsoup.name")):("§eDropSoup"), new ArrayList<>(Arrays.asList("&8Your text here.", "&8Left-click to have this effect")), Heads.SOUP.getTexture());
	}

	@Override
	public void update(User user) {
		for (int i = 0; i < 30; i++) {
			Item ITEM = user.getPlayer().getWorld().dropItem(user.getPlayer().getLocation(), ItemFactory.create(Material.MUSHROOM_SOUP, (byte)0, UUID.randomUUID().toString()));
			ITEM.setPickupDelay(300);
			items.add(ITEM);
			ITEM.setVelocity(new Vector(r.nextDouble() - 0.5D, r.nextDouble() / 2.0D, r.nextDouble() - 0.5D));
		} 
		Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable(){
			public void run() {
				for (Item i : items) {
					Particle.play(i.getLocation(), Effect.COLOURED_DUST);
					i.remove();
				} 
			}
		},50L);
	}
}
