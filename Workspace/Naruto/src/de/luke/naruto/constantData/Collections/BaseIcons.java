package de.luke.naruto.constantData.Collections;

import java.util.HashMap;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import de.luke.naruto.constantData.Ids.MetaDataIds;
import de.luke.naruto.constantData.Ids.UniqueIds;
import de.luke.naruto.constantData.Items.BaseIcon;
import de.luke.naruto.constantData.Items.MaterialIcon;
import de.luke.naruto.constantData.Items.MaterialInfo;
import de.luke.naruto.tools.ItemMetadata;

public class BaseIcons {

	private static HashMap<Integer, BaseIcon> _baseIcons;

	@SuppressWarnings("serial")
	public static void Create() {
		_baseIcons = new HashMap<Integer, BaseIcon>();

		AddIcon(UniqueIds.Workbench, 0, "§f§lWorkbench", "");
		AddIcon(UniqueIds.GreenWool, 0, "§f§lWool", "");
	}

	private static void AddIcon(int uniqueId, int position, String displayName, String dbAccessName) {

		BaseIcon baseIcon = new BaseIcon(uniqueId, position, displayName, dbAccessName);
		_baseIcons.put(uniqueId, baseIcon);
	}

	public static void AddToInventory(Inventory inventory, int uniqueId, int position, int typeId) {

		MaterialInfo materialInfo = MaterialInfos.GetMaterialItem(uniqueId);
		ItemStack itemStack = new MaterialData(materialInfo.GetMaterial(), materialInfo.GetbyteValue()).toItemStack(1);

		ItemMeta itemMeta = itemStack.getItemMeta();
		BaseIcon baseIcon = GetBaseIconFromId(uniqueId);
		itemMeta.setDisplayName(baseIcon.GetDisplayName());

		itemStack.setItemMeta(itemMeta);

		// Important!!! Metadata only in the return value
		itemStack = ItemMetadata.setMetadata(itemStack, MetaDataIds.TypeMetaData, typeId);
		itemStack = ItemMetadata.setMetadata(itemStack, MetaDataIds.UniqueIdMetaData, uniqueId);
		inventory.setItem(position, itemStack);

	}

	public static BaseIcon GetBaseIconFromId(int uniqueId) {
		return _baseIcons.get(uniqueId);
	}

	public static void AddToInventory(BaseIcon baseIcon, Inventory inventory, int uniqueId, int position, int typeId) {

		MaterialInfo materialInfo = MaterialInfos.GetMaterialItem(uniqueId);
		ItemStack itemStack = new MaterialData(materialInfo.GetMaterial(), materialInfo.GetbyteValue()).toItemStack(1);

		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(baseIcon.GetDisplayName());

		itemStack.setItemMeta(itemMeta);

		// Important!!! Metadata only in the return value
		itemStack = ItemMetadata.setMetadata(itemStack, MetaDataIds.TypeMetaData, typeId);
		itemStack = ItemMetadata.setMetadata(itemStack, MetaDataIds.UniqueIdMetaData, uniqueId);
		inventory.setItem(position, itemStack);

	}

}
