package fr.bukkit.effectkill.effect.animation;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.bukkit.effectkill.Main;
import fr.bukkit.effectkill.effect.MainEffectKill;
import fr.bukkit.effectkill.utils.Particle;
import fr.bukkit.effectkill.utils.User;
import fr.bukkit.effectkill.utils.Utils;
import fr.bukkit.effectkill.utils.config.YAMLUtils;
import fr.bukkit.effectkill.utils.inventory.Heads;

public class Squid extends MainEffectKill{

	public Squid() {
		super("squid",YAMLUtils.get("messages").getFile().exists()?((String) Utils.gfc("messages", "effectKill.squid.name")):("§eSquid"), new ArrayList<>(Arrays.asList("&8Your text here.", "&8Left-click to have this effect")), Heads.SQUID.getTexture());
	}

	@Override
	public void update(User user) {
		Location loc = user.getPlayer().getLocation().add(0, -0.3, 0);
		ArmorStand armor = (ArmorStand)loc.getWorld().spawnEntity(loc.add(0, -1, 0), EntityType.ARMOR_STAND);
		armor.setVisible(false);
		armor.setGravity(false);
		Entity e = user.getPlayer().getWorld().spawnEntity(loc, EntityType.SQUID);
		armor.setPassenger(e);
		as.add(armor);
		new BukkitRunnable() {
			int i = 0;
			@Override
			public void run() {
				i++;
				Entity passenger = armor.getPassenger();
				armor.eject();
				armor.teleport(armor.getLocation().add(0,0.5,0));
				armor.setPassenger(passenger);
				Particle.play(armor.getLocation().add(0.0, -0.2, 0.0), Effect.FLAME);
				user.getPlayer().playSound(loc, Sound.CHICKEN_EGG_POP, 1f, 1f);
				if(i == 20) {
					as.remove(armor);
					armor.remove();
					e.remove();
					Particle.play(armor.getLocation().add(0.0, 0.5, 0.0), Effect.EXPLOSION_HUGE, 1);
					i = 0;
					cancel();
				} 
			}
		}.runTaskTimer(Main.getInstance(), 1, 0);
	}
}
