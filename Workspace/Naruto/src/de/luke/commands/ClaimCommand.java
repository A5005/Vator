package de.luke.commands;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import de.luke.naruto.constantData.Collections.MaterialIcons;
import de.luke.naruto.constantData.Items.MaterialIcon;

public class ClaimCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player))
			return false;

		Player player = (Player) sender;
		Inventory inventory = player.getInventory();

		HashMap<Material, Integer> dbAmounts;
		HashMap<MaterialIcon, Integer> newAmounts = new HashMap<MaterialIcon, Integer>();

		try {
			dbAmounts = MaterialIcons.DbReadAllAmounts(player.getUniqueId());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		ArrayList<ItemStack> itemStacksToRemove = new ArrayList<ItemStack>();

		for (ItemStack itemStack : inventory) {

			if (itemStack == null)
				continue;

			Material material = itemStack.getType();
			MaterialIcon materialIcon = MaterialIcons.TryGetMaterialIconFromMcMaterial(material);

			if (materialIcon == null)
				continue;

			int stackAmount = itemStack.getAmount();

			if (newAmounts.containsKey(materialIcon)) {
				int existingAmount = newAmounts.get(materialIcon);
				newAmounts.put(materialIcon, existingAmount + stackAmount);
			} else {
				// add database amount only once!
				int dbAmount = dbAmounts.get(material);
				System.out.println(String.format("AddDbAmount %s  dbAmount: %d", material.toString(), dbAmount));
				newAmounts.put(materialIcon, dbAmount + stackAmount);
			}

			itemStacksToRemove.add(itemStack);

			System.out.println(String.format("material: %s | stackAmount: %d", material.toString(), stackAmount));

		}

		for (ItemStack itemStack : itemStacksToRemove) {
			inventory.remove(itemStack);
		}

		for (HashMap.Entry<MaterialIcon, Integer> entry : newAmounts.entrySet()) {
			System.out.println("Material = " + entry.getKey().GetDisplayName() + "," + "Count = " + entry.getValue());
		}

		try {
			MaterialIcons.DbWriteAmounts(newAmounts, player.getUniqueId());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return true;
	}

}
