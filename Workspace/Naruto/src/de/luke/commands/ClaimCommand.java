package de.luke.commands;


import java.sql.SQLException;

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
			return true;

		Player player = (Player) sender;
		Inventory inventory = player.getInventory();

		// TODO DATABASE READ CURRENT AMOUNTS

		for (ItemStack itemStack : inventory) {

			if (itemStack == null)
				continue;

			Material material = itemStack.getType();
			MaterialIcon materialIcon = MaterialIcons.TryGetMaterialIconFromMcMaterial(material);

			if (materialIcon == null)
				continue;

			int amount = itemStack.getAmount();
			System.out.println(material + ": " + amount);

		}

		try {
			MaterialIcons.DbReadAllAmounts(player.getUniqueId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

}
