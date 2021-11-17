package de.luke.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {

	public static File DataFolder;

	public static void LoadOrCreateConfig() {

		// DebugServer\plugins\Naruto\customConfig.yml
		System.out.println(DataFolder.getPath());

		File customConfigFile = new File(DataFolder, "customConfig.yml");

		if (!customConfigFile.exists()) {

			System.out.println("No customConfig -> Creating it");

			YamlConfiguration yamlConfiguration = new YamlConfiguration();
			yamlConfiguration.set("HALLO", "WELT");
			yamlConfiguration.set("Int", 5);
			yamlConfiguration.set("Bool", true);
			yamlConfiguration.set("Double", 1.324);

			try {
				yamlConfiguration.save(customConfigFile);
				System.out.println("Success writing customConfig.yml");

			} catch (IOException e) {

				System.out.println("Error writing customConfig.ym");
			}

			return;
		} else {

			System.out.println("Found customConfig -> Reading it");
			FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
			String mystring = customConfig.getString("HALLO");
			int myInt = customConfig.getInt("Int");
			boolean myBool = customConfig.getBoolean("Bool");
			double myDouble = customConfig.getDouble("Double");
		

			System.out.println("result= " + mystring + " " + myInt + " " + myBool + " " + myDouble);
		}

	}

}