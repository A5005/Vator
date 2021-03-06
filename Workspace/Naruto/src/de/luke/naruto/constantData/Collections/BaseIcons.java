package de.luke.naruto.constantData.Collections;

import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import de.luke.naruto.constantData.Ids.MetaDataIds;
import de.luke.naruto.constantData.Ids.UniqueIds;
import de.luke.naruto.constantData.Items.BaseIcon;
import de.luke.naruto.constantData.Items.MaterialInfo;
import de.luke.naruto.tools.ItemMetadata;

public class BaseIcons {

	private static HashMap<Integer, BaseIcon> _baseIcons;

	public static void Create() {
		_baseIcons = new HashMap<Integer, BaseIcon>();

		AddIcon(UniqueIds.Workbench, 0, "Craft", "", ChatColor.WHITE, 0);
		AddIcon(UniqueIds.GreenWool, 0, "Claim", "", ChatColor.GREEN, 0);
		AddIcon(UniqueIds.RedWool, 0, "Claim", "", ChatColor.RED, 0);
		AddIcon(UniqueIds.Barrier, 0, "< Back", "", ChatColor.RED, 0);
	}

	private static void AddIcon(int uniqueId, int position, String displayName, String dbAccessName, ChatColor chatColor, int priority) {

		BaseIcon baseIcon = new BaseIcon(uniqueId, position, displayName, dbAccessName, chatColor, priority);
		_baseIcons.put(uniqueId, baseIcon);
	}

	public static void AddToInventory(Inventory inventory, int uniqueId, int position, int typeId, List<String> lore) {

		MaterialInfo materialInfo = MaterialInfos.GetMaterialInfo(uniqueId);

		ItemStack existingItemStack = inventory.getItem(position);

		if (existingItemStack != null) {
			inventory.clear(position);
		}

		ItemStack itemStack = new MaterialData(materialInfo.GetMaterial(), materialInfo.GetbyteValue()).toItemStack(1);

		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		BaseIcon baseIcon = GetBaseIconFromId(uniqueId);

		itemMeta.setDisplayName(baseIcon.GetColoredDisplayName());
		itemMeta.setLore(lore);
		itemStack.setItemMeta(itemMeta);

		// Important!!! Metadata only in the return value
		itemStack = ItemMetadata.setMetadata(itemStack, MetaDataIds.TypeMetaData, typeId);
		itemStack = ItemMetadata.setMetadata(itemStack, MetaDataIds.UniqueIdMetaData, uniqueId);

		inventory.setItem(position, itemStack);

	}

	public static BaseIcon GetBaseIconFromId(int uniqueId) {
		return _baseIcons.get(uniqueId);
	}

	public static void AddToInventory(BaseIcon baseIcon, Inventory inventory, int uniqueId, int position, int typeId, List<String> lore) {

		MaterialInfo materialInfo = MaterialInfos.GetMaterialInfo(uniqueId);
		@SuppressWarnings("deprecation")
		ItemStack itemStack = new MaterialData(materialInfo.GetMaterial(), materialInfo.GetbyteValue()).toItemStack(1);

		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemMeta.setDisplayName(baseIcon.GetColoredDisplayName());
		itemMeta.setLore(lore);
		itemStack.setItemMeta(itemMeta);

		// Important!!! Metadata only in the return value
		itemStack = ItemMetadata.setMetadata(itemStack, MetaDataIds.TypeMetaData, typeId);
		itemStack = ItemMetadata.setMetadata(itemStack, MetaDataIds.UniqueIdMetaData, uniqueId);
		inventory.setItem(position, itemStack);

	}

}
