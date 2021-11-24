package de.luke.commands;

import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class RemoveAllItemsCommand implements CommandExecutor {
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player))
			return true;

		Player player = (Player) sender;
		
		Inventory inventory =player.getInventory();
		
		ArrayList<ItemStack> itemStacksToRemove = new ArrayList<ItemStack>();

		for (ItemStack itemStack : inventory) {

			if (itemStack == null)
				continue;

			itemStacksToRemove.add(itemStack);
		}
		
		for (ItemStack itemStack : itemStacksToRemove) {
			inventory.remove(itemStack);
		}
		
		
		return true;
	}
}
