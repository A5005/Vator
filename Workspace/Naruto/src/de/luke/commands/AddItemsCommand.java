package de.luke.commands;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import de.luke.config.ConfigManager;


public class AddItemsCommand implements CommandExecutor {

	//TODO
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player))
			return true;

		Player player = (Player) sender;

		Inventory inventory = player.getInventory();

		YamlConfiguration customConfig = ConfigManager.LoadOrCreateConfig("additems.yml");

		
		return true;

	}
}
