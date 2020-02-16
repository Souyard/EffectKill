package fr.bukkit.effectkill.utils;

import org.bukkit.entity.Player;

public class Messages {
	public static void sendhelp(Player p) {
		p.sendMessage("§c--- §6EFFECTKILL §c---");
		p.sendMessage("§4Usage:");
		p.sendMessage("§c/effectkill §r: §8open gui.");
		p.sendMessage("§c/effectkill remove §r: remove your effect");
		p.sendMessage("§c/effectkill help §r: §8send help command.");
		p.sendMessage(" ");
		p.sendMessage("§c--- §6EFFECTKILL §c---");
	}
}
