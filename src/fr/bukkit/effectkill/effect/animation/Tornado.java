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

public class Tornado extends MainEffectKill{

	public Tornado(){
		super("tornado",YAMLUtils.get("messages").getFile().exists()?((String) Utils.gfc("messages", "effectKill.tornado.name")):("§eTornado"), new ArrayList<>(Arrays.asList("&8Your text here.", "&8Left-click to have this effect")), Heads.TORNADO.getTexture());
	}

	@Override
	public void update(User user) {
		Location location = user.getPlayer().getLocation();
		new BukkitRunnable() {
			int angle = 0;
			@Override
			public void run() {
				int max_height = 7;
				double max_radius = 5;
				int lines = 5;
				double height_increasement = 0.25;
				double radius_increasement = max_radius / max_height;
				for (int l = 0; l < lines; l++) {
					for (double y = 0; y < max_height; y+=height_increasement ) {
						double radius = y * radius_increasement;
						double x = Math.cos(Math.toRadians(360/lines*l + y*30 - angle)) * radius;
						double z = Math.sin(Math.toRadians(360/lines*l + y*30 - angle)) * radius;
						Particle.play(location.clone().add(x,y,z), Effect.CLOUD);
					}
				}
				angle++;
				if(angle == 70) {
					cancel();
				}
			}
		}.runTaskTimer(Main.getInstance(), 2, 0);
	}
}
