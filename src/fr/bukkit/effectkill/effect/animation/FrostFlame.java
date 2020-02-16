package fr.bukkit.effectkill.effect.animation;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import fr.bukkit.effectkill.Main;
import fr.bukkit.effectkill.effect.MainEffectKill;
import fr.bukkit.effectkill.utils.Particle;
import fr.bukkit.effectkill.utils.User;
import fr.bukkit.effectkill.utils.Utils;
import fr.bukkit.effectkill.utils.config.YAMLUtils;
import fr.bukkit.effectkill.utils.inventory.Heads;

public class FrostFlame extends MainEffectKill{

	public FrostFlame() {
		super("frostflame", YAMLUtils.get("messages").getFile().exists()?((String) Utils.gfc("messages", "effectKill.frostflame.name")):("§eFrostFlame"), new ArrayList<>(Arrays.asList("&8Your text here.", "&8Left-click to have this effect")), Heads.FLAME.getTexture());
	}

	@Override
	public void update(User user) {
		Location loc = user.getPlayer().getLocation();
		new BukkitRunnable() {
			double t = 0.0D;
			public void run() {
				t += 0.3;
				for (double phi = 0.0D; phi <= 6; phi += 1.5) {
					double x = 0.11D * (12.5 - t) * Math.cos(t + phi);
					double y = 0.23D * t;
					double z = 0.11D * (12.5 - t) * Math.sin(t + phi);
					loc.add(x, y, z);
					Particle.play(loc, Effect.FLAME);
					loc.subtract(x, y, z);

					if (t >= 12.5) {
						loc.add(x, y, z);
						if (phi > Math.PI) {
							cancel();
						} 
					} 
				} 
			}
		}.runTaskTimer(Main.getInstance(), 1L, 1L);
	}
}
