package de.luke.commands;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.luke.config.ConfigManager;
import de.luke.naruto.constantData.Collections.MaterialInfos;
import de.luke.naruto.constantData.Items.MaterialInfo;

public class FillItemsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player))
			return true;

		Player player = (Player) sender;

		Inventory inventory = player.getInventory();

		RemoveItems(inventory);

		YamlConfiguration customConfig = ConfigManager.LoadOrCreateConfig("fillitems.yml");

		for (int i = 0; i < inventory.getSize(); i++) {

			String yamlItem = customConfig.getString("i" + i);

			System.out.println("A " + yamlItem);
			String[] split = yamlItem.split(",");

			System.out.println("SPLIT ");

			String materialIdString = split[0];
			String amountString = split[1];

			int materialId = Integer.parseInt(materialIdString);
			int amount = Integer.parseInt(amountString);

			System.out.println(materialId + " / " + amount);

			MaterialInfo materialInfo = MaterialInfos.GetMaterialInfo(materialId);

			Material material = materialInfo.GetMaterial();
			ItemStack itemStack = new ItemStack(material, amount);
			System.out.println(i + " " + material.toString());

			inventory.setItem(i, itemStack);

		}

		return true;

	}

	private void RemoveItems(Inventory inventory) {
		ArrayList<ItemStack> itemStacksToRemove = new ArrayList<ItemStack>();

		for (ItemStack itemStack : inventory) {

			if (itemStack == null)
				continue;

			itemStacksToRemove.add(itemStack);
		}

		for (ItemStack itemStack : itemStacksToRemove) {
			inventory.remove(itemStack);
		}
	}

}