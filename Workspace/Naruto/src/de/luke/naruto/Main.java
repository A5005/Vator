package de.luke.naruto;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.luke.config.ConfigManager;
import de.luke.naruto.constantData.Collections.CollectionInitializer;


//API
//https://hub.spigotmc.org/javadocs/spigot/

public class Main extends JavaPlugin {

	// server start or reload
	@Override
	public void onEnable() {
		
		System.out.println("##########  Plugin Naruto was loaded");
		
		ConfigManager.DataFolder=this.getDataFolder();
		
		
		
		try {
			CollectionInitializer.InitializeCollections();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
		Bukkit.getPluginManager().registerEvents(new AttackListener(this), this);
		

		getCommand("fire2").setExecutor(new FireCommand());
		getCommand("menu").setExecutor(new GuiCommands());

		

	}

	// not called when server closed with [x]
	@Override
	public void onDisable() {

	}

	

}
