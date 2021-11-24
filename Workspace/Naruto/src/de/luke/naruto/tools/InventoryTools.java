package de.luke.naruto.tools;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.luke.naruto.constantData.Collections.WeaponIcons;
import de.luke.naruto.constantData.Items.WeaponIcon;

public class InventoryTools {

	
	public static void AdItemSave(Player player, Inventory topInventory, Inventory bottomInventory) throws SQLException {

		UUID uuid = player.getUniqueId();
		HashMap<WeaponIcon, Integer> allWeaponIcons = WeaponIcons.DbReadAllIconAmounts(uuid);

		// fill existing ItemStacks

		int count = 0;
		for (ItemStack itemStack : bottomInventory) {

			if (itemStack == null)
				continue;

			Material material = itemStack.getType();

			System.out.println(String.format("ItemStack pos:%d  Material:%s Amount:%d  MaxCount:%d", count++, material.toString(), itemStack.getAmount(), itemStack.getMaxStackSize()));

			WeaponIcon stackWeaponIcon = WeaponIcons.TryGetMaterialIconFromMcMaterial(material);
			if (stackWeaponIcon == null)
				continue;

			if (!allWeaponIcons.containsKey(stackWeaponIcon)) {
				System.out.println("Not a Naruto Material");
				continue;
			}

			int weaponStackAmount = itemStack.getAmount();

			int maxStackSize = itemStack.getMaxStackSize();

			if (weaponStackAmount >= maxStackSize) {
				System.out.println("Stack is full");
				continue;
			}

			int weaponDbAmount = allWeaponIcons.get(stackWeaponIcon);

			int toFill = maxStackSize - weaponStackAmount;

			int rest = 0;

			// maxStackSize=64, weaponStackAmount=60, toFill=4, weaponDbAmount = 10
			// 10 > 4
			if (weaponDbAmount > toFill) {

				System.out.println(String.format("Inc Itemstack to"));
				itemStack.setAmount(maxStackSize); // weaponStackAmount + toFill
				// 10 -4 = 6
				rest = weaponDbAmount - toFill;
			} else {

				// maxStackSize=64, weaponStackAmount=30, toFill=34, weaponDbAmount = 10
				itemStack.setAmount(weaponDbAmount);
				rest = 0;
			}

			System.out.println(String.format("weaponStackAmount:%d  weaponDbAmount:%d  toFill:%d  rest:%d", weaponStackAmount, weaponDbAmount, toFill, rest));

			allWeaponIcons.put(stackWeaponIcon, rest);

		}

		// TODO fill existing new ItemStacks

		// TODO Output not claimed Item Stacks

		/*
		 * for (HashMap.Entry<WeaponIcon, Integer> entry : allWeaponIcons.entrySet()) {
		 * int amount = entry.getValue(); if (amount > 0) {
		 * 
		 * bottomInventory.addItem(new
		 * ItemStack(entry.getKey().GetMaterialInfo().GetMaterial(), 1)); } }
		 */
	}

}
