package fr.bukkit.effectkill.utils.config;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Maps;

import fr.bukkit.effectkill.Main;

public class YAMLUtils {

	private String name;
	private File file;
	private FileConfiguration config;

	public static Map<String, YAMLUtils> createds = Maps.newHashMap();

	private YAMLUtils(String name, JavaPlugin javaPlugin) {
		if (createds.containsKey(name)) throw new IllegalArgumentException();
		this.name = name;
		this.file = new File(javaPlugin.getDataFolder(), name+".yml");
		this.config = YamlConfiguration.loadConfiguration(this.file);
	}
	public void build(String header) {
		if (!this.file.exists()) {
			config.options().copyDefaults(true);
			if (!header.equals("")) config.options().header(header);
			save();
		}
	}
	public void save() {
		try {
			config.save(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public FileConfiguration getConfig() {
		return config;
	}
	public File getFile() {
		return file;
	}
	public String getName() {
		return name;
	}
	public static YAMLUtils get(String str) {
		return createds.computeIfAbsent(str, k -> new YAMLUtils(k, Main.getInstance()));
	}
	public static Set<FileConfiguration> getConfigs() {
		return createds.keySet().stream()
				.map(str -> get(str).getConfig())
				.collect(Collectors.toSet());
	}
}
