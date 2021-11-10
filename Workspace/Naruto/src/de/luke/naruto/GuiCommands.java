package de.luke.naruto;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import de.luke.naruto.enums.ItemId;

public class GuiCommands implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		System.out.println("Bla");

		if (sender instanceof Player) {
			Player player = (Player) sender;
			// player.sendMessage(ChatColor.DARK_RED + "Hello, " + player.getName() +
			// ChatColor.GREEN + "!");

			Inventory inventory = Bukkit.createInventory(null, 36, "§7§lMaterials");

			MaterialInfo materialItem = MaterialInfos.GetMaterialItem(ItemId.Book);
		
			ItemStack itemStack = new MaterialData(materialItem.GetMaterial(), materialItem.GetbyteValue()).toItemStack(1);
			ItemMeta itemMeta = itemStack.getItemMeta();
			itemMeta.setDisplayName("HALLO1");
			itemStack.setItemMeta(itemMeta);
			
			inventory.setItem(0, itemStack);

			player.openInventory(inventory);

		} else
			System.out.println("You cannot use this command through console!");

		return true;
	}

}
