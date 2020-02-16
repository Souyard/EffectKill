package fr.bukkit.effectkill.effect;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

import fr.bukkit.effectkill.Main;
import fr.bukkit.effectkill.utils.ItemsUtils;
import fr.bukkit.effectkill.utils.User;

public abstract class MainEffectKill implements Listener{

	public static List<MainEffectKill> instanceList = Lists.newArrayList();
	public static Main instance = Main.getInstance();
	public static MainEffectKill effectKill;
	public ArrayList<ArmorStand> as = new ArrayList<ArmorStand>();
	public ItemStack itemStack;
	protected String permission;
	protected String name;
	protected String displayName;
	protected List<String> description;
	public static List<Class<? extends MainEffectKill>> effectList = Lists.newArrayList();

	public MainEffectKill(String name, String displayName, ArrayList<String> description, String texture) {
		effectKill = this;
		ItemStack head = ItemsUtils.getSkull(texture);
		itemStack = ItemsUtils.create(head, displayName, description);
		this.name = name;
		this.description = description;
		this.displayName = displayName;
		instance.getServer().getPluginManager().registerEvents(this, instance);
		instance.getEffectKill().put(name,this);
	}
	public void despawn(User user) {
		if (user.getEffectKill() != null) {
			user.setEffectKill(null);
			return;
		}
	}
	public abstract void update(User user);
	public static MainEffectKill getInstance() {
		return effectKill;
	}
	public String getName() {
		return name;
	}
	public String getPermission() {
		return "effectkill." + name;
	}
	public String getDisplayName() {
		return displayName; 
	}
	public List<String> getDescription() {
		return description; 
	}
	public ItemStack getItemStack() {
		return itemStack;
	}
}