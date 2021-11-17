package de.luke.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {

	public static File DataFolder;

	public static YamlConfiguration LoadOrCreateConfig(String fileName) {

		//for example  DebugServer\plugins\Naruto\customConfig.yml
		
		System.out.println(DataFolder.getPath());

		File customConfigFile = new File(DataFolder, fileName);

		if (!customConfigFile.exists()) {

			YamlConfiguration yamlConfiguration = new YamlConfiguration();
			yamlConfiguration.set("speed", 32);
			yamlConfiguration.set("range", 16);
			yamlConfiguration.set("fillcount", 2);

			try {
				yamlConfiguration.save(customConfigFile);
			} catch (IOException e) {

				System.out.println("Error writing " + fileName);
			}

			return yamlConfiguration;
		} else {

			YamlConfiguration customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
			return customConfig;
		}

	}

}