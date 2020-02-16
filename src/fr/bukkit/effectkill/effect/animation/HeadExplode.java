package fr.bukkit.effectkill.effect.animation;

import java.util.ArrayList;
import java.util.Arrays;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import fr.bukkit.effectkill.Main;
import fr.bukkit.effectkill.effect.MainEffectKill;
import fr.bukkit.effectkill.utils.Particle;
import fr.bukkit.effectkill.utils.User;
import fr.bukkit.effectkill.utils.Utils;
import fr.bukkit.effectkill.utils.config.YAMLUtils;
import fr.bukkit.effectkill.utils.inventory.Heads;

public class HeadExplode extends MainEffectKill{

	public HeadExplode() {
		super("headexplode",YAMLUtils.get("messages").getFile().exists()?((String) Utils.gfc("messages", "effectKill.headexplose.name")):("§eHeadExplose"), new ArrayList<>(Arrays.asList("&8Your text here.", "&8Left-click to have this effect")), Heads.ANGRY.getTexture());
	}
	@Override
	public void update(User user) {
		Location loc = user.getPlayer().getLocation();
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
		SkullMeta skullMeta = (SkullMeta)skull.getItemMeta();
		skullMeta.setOwner(user.getPlayer().getName());
		skull.setItemMeta(skullMeta);
		ArmorStand armor = (ArmorStand)loc.getWorld().spawnEntity(loc.add(0, -1, 0), EntityType.ARMOR_STAND);
		armor.setVisible(false);
		armor.setCustomName("§c§l" + user.getPlayer().getName());
		armor.setCustomNameVisible(true);
		armor.setHelmet(skull);
		armor.setGravity(false);
		as.add(armor);
		new BukkitRunnable() {
			int i = 0;
			@Override
			public void run() {
				i++;
				armor.teleport(armor.getLocation().add(0,0.5,0));  
				armor.setHeadPose(armor.getHeadPose().add(0.0, 1, 0.0));
				Particle.play(armor.getLocation().add(0.0, -0.2, 0.0), Effect.FLAME);
				if(i == 20) {
					as.remove(armor);
					armor.remove();
					Particle.play(armor.getLocation().add(0.0, 0.5, 0.0), Effect.EXPLOSION_HUGE, 1);
					cancel();
				}
			}
		}.runTaskTimer(Main.getInstance(), 1, 0);
	}
}
