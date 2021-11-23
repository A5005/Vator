package de.luke.naruto.Perspectives;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.luke.naruto.constantData.Collections.BaseIcons;
import de.luke.naruto.constantData.Collections.MaterialIcons;
import de.luke.naruto.constantData.Collections.WeaponIcons;
import de.luke.naruto.constantData.Ids.TypeIds;
import de.luke.naruto.constantData.Ids.UniqueIds;
import de.luke.naruto.constantData.Items.Cost;
import de.luke.naruto.constantData.Items.CraftCost;
import de.luke.naruto.constantData.Items.MaterialIcon;
import de.luke.naruto.constantData.Items.WeaponIcon;

public class CraftingPerspective {

	private final static int weaponPosition = 4;

	@SuppressWarnings("deprecation")
	public static Inventory OpenInventory(Player player, Inventory openInventory, int weaponId) throws SQLException {

		if (openInventory != null)
			player.closeInventory();

		WeaponIcon weaponIcon = WeaponIcons.GetWeaponIconFromId(weaponId);
		Inventory inventory = Bukkit.createInventory(null, 36, weaponIcon.GetDisplayName());

		List<String> lore = new ArrayList<>();
		lore.add("§7§lClick to open the menu!");

		UUID uuid = player.getUniqueId();

		UpdateWeaponIcon(inventory, weaponId, weaponIcon, uuid);
		UpdateCraftIcon(inventory, weaponId, weaponIcon, uuid);
		BaseIcons.AddToInventory(inventory, UniqueIds.Barrier, 27, TypeIds.BackIcon, null);
		BaseIcons.AddToInventory(inventory, UniqueIds.GreenWool, 31, TypeIds.ClaimIcon, null);

		player.openInventory(inventory);

		return inventory;
	}

	private static void UpdateCraftIcon(Inventory inventory, int weaponId, WeaponIcon weaponIcon, UUID uuid) throws SQLException {

		List<String> craftLore = GetCraftLore(weaponIcon, uuid);
		BaseIcons.AddToInventory(inventory, UniqueIds.Workbench, 20, TypeIds.WorkBenchIcon, craftLore);
	}

	private static void UpdateWeaponIcon(Inventory inventory, int weaponId, WeaponIcon weaponIcon, UUID uuid) throws SQLException {

		List<String> weaponLore = new ArrayList<String>();

		int amount = WeaponIcons.DbGetSpecificAmount(weaponIcon, uuid);
		weaponLore.add("§7§lYou have: §f§l" + amount);
		WeaponIcons.AddToInventory(inventory, weaponId, weaponPosition, TypeIds.CraftWeaponIcon, weaponLore);
	}

	public static void Craft(Player player, Inventory openInventory) throws SQLException {

		UUID uuid = player.getUniqueId();
		ItemStack itemStack = openInventory.getItem(weaponPosition);
		Material material = itemStack.getType();

		WeaponIcon weaponIcon = WeaponIcons.TryGetMaterialIconFromMcMaterial(material);
		int weaponId = weaponIcon.GetUniqueId();
		int weaponCount = WeaponIcons.DbGetSpecificAmount(weaponIcon, uuid);

		List<CraftCost> craftCosts = GetCraftingCosts(weaponIcon, uuid);

		for (int i = 0; i < craftCosts.size(); i++) {
			CraftCost crafCost = craftCosts.get(i);

			int amount = crafCost.GetAmount();
			int available = crafCost.GetAvailable();

			// Too little material cannot craft
			if (amount > available) {
				player.sendMessage(ChatColor.RED + "Too little material cannot craft");
				return;
			}

		}

		HashMap<MaterialIcon, Integer> reducedAmounts = new HashMap<MaterialIcon, Integer>();

		for (int i = 0; i < craftCosts.size(); i++) {
			CraftCost crafCost = craftCosts.get(i);

			int amount = crafCost.GetAmount();
			int available = crafCost.GetAvailable();
			MaterialIcon curMaterial = crafCost.GetMaterialIcon();
			reducedAmounts.put(curMaterial, available - amount);
		}

		weaponCount++;
		MaterialIcons.DbSetSpecificAmounts(reducedAmounts, uuid);
		WeaponIcons.DbSetSpecificAmount(weaponIcon, weaponCount, uuid);

		// UpdateToolTips
		UpdateWeaponIcon(openInventory, weaponId, weaponIcon, uuid);
		UpdateCraftIcon(openInventory, weaponId, weaponIcon, uuid);

	}

	private static List<CraftCost> GetCraftingCosts(WeaponIcon weaponIcon, UUID uuid) throws SQLException {

		ArrayList<Cost> costs = weaponIcon.GetCosts();

		ArrayList<MaterialIcon> costMaterialIcons = new ArrayList<MaterialIcon>();

		for (int i = 0; i < costs.size(); i++) {
			Cost cost = costs.get(i);
			MaterialIcon curMaterialIcon = MaterialIcons.GetMaterialIconFromId(cost.GetUniqueId());
			costMaterialIcons.add(curMaterialIcon);
		}

		HashMap<MaterialIcon, Integer> amounts = MaterialIcons.DbGetSpecificAmounts(costMaterialIcons, uuid);

		List<CraftCost> craftCosts = new ArrayList<CraftCost>();

		for (int i = 0; i < costs.size(); i++) {
			Cost cost = costs.get(i);
			MaterialIcon curMaterial = MaterialIcons.GetMaterialIconFromId(cost.GetUniqueId());

			int amount = cost.GetAmount();
			int available = amounts.get(curMaterial);

			CraftCost craftCost = new CraftCost(curMaterial, amount, available);
			craftCosts.add(craftCost);
		}

		return craftCosts;
	}

	private static List<String> GetCraftLore(WeaponIcon weaponIcon, UUID uuid) throws SQLException {

		List<CraftCost> craftCosts = GetCraftingCosts(weaponIcon, uuid);
		List<String> lore = new ArrayList<String>();

		for (int i = 0; i < craftCosts.size(); i++) {
			CraftCost crafCost = craftCosts.get(i);

			int amount = crafCost.GetAmount();
			int available = crafCost.GetAvailable();
			MaterialIcon curMaterial = crafCost.GetMaterialIcon();

			String stockColor;
			if (amount > available)
				stockColor = "§c"; // red
			else
				stockColor = "§a";// green

			String line = String.format("§7%d x %s (%s%d§7)", amount, curMaterial.GetDisplayName(), stockColor, available);
			lore.add(line);

		}

		return lore;
	}

}
