package fr.bukkit.effectkill.effect.animation;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Effect;
import org.bukkit.Location;

import fr.bukkit.effectkill.effect.MainEffectKill;
import fr.bukkit.effectkill.utils.Particle;
import fr.bukkit.effectkill.utils.User;
import fr.bukkit.effectkill.utils.Utils;
import fr.bukkit.effectkill.utils.config.YAMLUtils;
import fr.bukkit.effectkill.utils.inventory.Heads;
import fr.bukkit.effectkill.utils.maths.MathUtils;

public class Heart extends MainEffectKill{

	public Heart() {
		super("love",YAMLUtils.get("messages").getFile().exists()?((String) Utils.gfc("messages", "effectKill.love.name")):("§eLove"), new ArrayList<>(Arrays.asList("&8Your text here.", "&8Left-click to have this effect")), Heads.HEART.getTexture());
	}

	@Override
	public void update(User user) {
		Location loc = user.getPlayer().getLocation();
		for (double height = 0.0; height < 1.0; height += 0.2) {
			Particle.play(loc.clone().add((double)MathUtils.randomRange(-1.0f, 1.0f), height, (double)MathUtils.randomRange(-1.0f, 1.0f)), Effect.HEART);
		}
	}
}
