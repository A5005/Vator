package de.luke.naruto;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.luke.naruto.constantData.Collections.CollectionInitializer;
import net.md_5.bungee.api.ChatColor;

//API
//https://hub.spigotmc.org/javadocs/spigot/

public class Main extends JavaPlugin {

	// server start or reload
	@Override
	public void onEnable() {
		
		System.out.println("##########  Plugin Naruto was loaded");
		
		CollectionInitializer.InitializeCollections();

		Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
		
		getCommand("menu").setExecutor(new GuiCommands());

	}

	// not called when server closed with [x]
	@Override
	public void onDisable() {

	}

	

}
