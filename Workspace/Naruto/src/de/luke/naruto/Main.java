package de.luke.naruto;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.luke.commands.ClaimCommand;
import de.luke.commands.FillItemsCommand;
import de.luke.commands.MenuCommand;
import de.luke.commands.RemoveAllItemsCommand;
import de.luke.config.ConfigManager;
import de.luke.listener.AttackListener;
import de.luke.listener.InventoryListener;
import de.luke.listener.JoinListener;
import de.luke.naruto.constantData.Collections.CollectionInitializer;
import de.luke.naruto.database.NarutoDataBase;

//API
//https://hub.spigotmc.org/javadocs/spigot/

public class Main extends JavaPlugin {

	// server start or reload
	@Override
	public void onEnable() {

		System.out.println("##########  Plugin Naruto was loaded");

		ConfigManager.DataFolder = this.getDataFolder();

		try {
			CollectionInitializer.InitializeCollections();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
		Bukkit.getPluginManager().registerEvents(new AttackListener(this), this);

		getCommand("menu").setExecutor(new MenuCommand());
		getCommand("claim").setExecutor(new ClaimCommand());
		getCommand("rai").setExecutor(new RemoveAllItemsCommand());
		getCommand("fill").setExecutor(new FillItemsCommand());
		
		
		
		
		NarutoDataBase.loadMySQL();

	}

	// not called when server closed with [x]
	@Override
	public void onDisable() {

	}

}
