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
import org.bukkit.inventory.InventoryView;
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

	public static Inventory OpenInventory(Player player, Inventory openInventory, int weaponId) throws SQLException {

		if (openInventory != null)
			player.closeInventory();

		WeaponIcon weaponIcon = WeaponIcons.GetWeaponIconFromId(weaponId);
		Material weaponMaterial = weaponIcon.GetMaterialInfo().GetMaterial();

		Inventory inventory = Bukkit.createInventory(null, 36, weaponIcon.GetDisplayName());
		BaseIcons.AddToInventory(inventory, UniqueIds.Barrier, 27, TypeIds.BackIcon, null);
		InventoryView inventoryView = player.openInventory(inventory);

		Inventory topInventory = inventoryView.getTopInventory();
		Inventory bottomInventory = inventoryView.getBottomInventory();

		List<String> lore = new ArrayList<>();
		lore.add("§7§lClick to open the menu!");

		UUID uuid = player.getUniqueId();
		boolean hasFreeSlot = HasFreeSlotForWeapon(bottomInventory, weaponMaterial);

		UpdateWeaponIcon(topInventory, weaponId, weaponIcon, uuid);
		UpdateCraftIcon(topInventory, weaponId, weaponIcon, uuid);
		UpdateClaimIcon(hasFreeSlot, topInventory, bottomInventory, weaponMaterial);

		return inventory;
	}

	private static void UpdateClaimIcon(boolean hasFreeSlot, Inventory topInventory, Inventory bottomInventory, Material weaponMaterial) {

		List<String> weaponLore = new ArrayList<String>();

		if (hasFreeSlot) {
			BaseIcons.AddToInventory(topInventory, UniqueIds.GreenWool, 31, TypeIds.ClaimIcon, null);
		}

		else {
			weaponLore.add("§4 Inventory is full");
			BaseIcons.AddToInventory(topInventory, UniqueIds.RedWool, 31, TypeIds.ClaimIcon, weaponLore);
		}

	}

	private static void UpdateCraftIcon(Inventory topInventory, int weaponId, WeaponIcon weaponIcon, UUID uuid) throws SQLException {

		List<String> craftLore = GetCraftLore(weaponIcon, uuid);
		BaseIcons.AddToInventory(topInventory, UniqueIds.Workbench, 20, TypeIds.WorkBenchIcon, craftLore);
	}

	private static void UpdateWeaponIcon(Inventory topInventory, int weaponId, WeaponIcon weaponIcon, UUID uuid) throws SQLException {

		List<String> weaponLore = new ArrayList<String>();

		int amount = WeaponIcons.DbGetSpecificAmount(weaponIcon, uuid);
		weaponLore.add("§7§lYou have: §f§l" + amount);
		WeaponIcons.AddToInventory(topInventory, weaponId, weaponPosition, TypeIds.CraftWeaponIcon, weaponLore);
	}

	public static void Craft(Player player, Inventory topInventory) throws SQLException {

		UUID uuid = player.getUniqueId();
		ItemStack itemStack = topInventory.getItem(weaponPosition);
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
		UpdateWeaponIcon(topInventory, weaponId, weaponIcon, uuid);
		UpdateCraftIcon(topInventory, weaponId, weaponIcon, uuid);

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

	public static void Claim(Player player, Inventory topInventory, Inventory bottomInventory) throws SQLException {

		ItemStack weaponItemStack = topInventory.getItem(weaponPosition);

		Material material = weaponItemStack.getType();

		if (!HasFreeSlotForWeapon(bottomInventory, material))
			return;

		ItemStack newItemStack = new ItemStack(weaponItemStack.getType(), 1);

		bottomInventory.addItem(newItemStack);

		boolean hasFreeSlot = HasFreeSlotForWeapon(bottomInventory, material);

		if (!hasFreeSlot) {
			UpdateClaimIcon(hasFreeSlot, topInventory, bottomInventory, material);
		}

	}

	private static boolean HasFreeSlotForWeapon(Inventory inventory, Material weaponMaterial) {
		for (ItemStack itemStack : inventory) {

			if (itemStack == null)
				return true;

			Material stackMaterial = itemStack.getType();
			if (!stackMaterial.equals(weaponMaterial))
				continue;

			int amount = itemStack.getAmount();
			int maxStackSize = itemStack.getMaxStackSize();

			if (amount < maxStackSize)
				return true;
		}

		return false;
	}

}
