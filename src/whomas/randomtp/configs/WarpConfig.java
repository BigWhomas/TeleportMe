package whomas.randomtp.configs;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class WarpConfig {
	
	private static File file;
	private static FileConfiguration customFile;
	
	public static void setup() {
		file = new File(Bukkit.getPluginManager().getPlugin("TeleportMe").getDataFolder(), "Warp.yml");
		
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch(IOException e) {
				
			}
		}
		customFile = YamlConfiguration.loadConfiguration(file);
	}
	
	public static FileConfiguration get() {
		return customFile;
	}
	
	public static void save() {
		try {
			customFile.save(file);
		} catch(IOException e) {
			Bukkit.getLogger().info("Could not save Warp.yml");
		}
	}
	
	public static void reload() {
		customFile = YamlConfiguration.loadConfiguration(file);
	}

}
