package de.luke.naruto.constantData.Collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import de.luke.naruto.constantData.Ids.MetaDataIds;
import de.luke.naruto.constantData.Ids.UniqueIds;
import de.luke.naruto.constantData.Items.BaseIcon;
import de.luke.naruto.constantData.Items.MaterialIcon;
import de.luke.naruto.constantData.Items.MaterialInfo;
import de.luke.naruto.constantData.Items.WeaponIcon;
import de.luke.naruto.tools.ItemMetadata;

public class MaterialIcons {

	private static HashMap<Integer, MaterialIcon> _materialIcons;
	private static HashMap<Material, MaterialIcon> _mcMaterialDict;

	private static boolean _isInit = false;

	public static void Create() {

		_materialIcons = new HashMap<Integer, MaterialIcon>();
		_mcMaterialDict = new HashMap<Material, MaterialIcon>();

		// CommonMat
		AddIcon(UniqueIds.Stick, UniqueIds.CommonMat, 18, "§f§lStick", "A1");
		AddIcon(UniqueIds.WoodenBtn, UniqueIds.CommonMat, 19, "§f§lWooden Button", "A2");
		AddIcon(UniqueIds.StoneBtn, UniqueIds.CommonMat, 20, "§f§lStone Button", "A3");

		AddIcon(UniqueIds.WoodenPresPlate, UniqueIds.CommonMat, 21, "§f§lWooden Pressure Plate", "A4");
		AddIcon(UniqueIds.StonePresPlate, UniqueIds.CommonMat, 22, "§f§lStone Pressure Plate", "A5");
		AddIcon(UniqueIds.Feather, UniqueIds.CommonMat, 23, "§f§lFeather", "A6");

		AddIcon(UniqueIds.Leather, UniqueIds.CommonMat, 24, "§f§lLeather", "A7");
		AddIcon(UniqueIds.Paper, UniqueIds.CommonMat, 25, "§f§lPaper", "A8");
		AddIcon(UniqueIds.Book, UniqueIds.CommonMat, 26, "§f§lBook", "A9");

		// UnCommonMat
		AddIcon(UniqueIds.String, UniqueIds.UnCommonMat, 18, "§f§lStick", "B1");
		AddIcon(UniqueIds.Bone, UniqueIds.UnCommonMat, 19, "§f§lWooden Button", "B2");
		AddIcon(UniqueIds.Brick, UniqueIds.UnCommonMat, 20, "§f§lStone Button", "B3");

		AddIcon(UniqueIds.NeBrick, UniqueIds.UnCommonMat, 21, "§f§lNether Brick", "B4");
		AddIcon(UniqueIds.Clay, UniqueIds.UnCommonMat, 22, "§f§lClay", "B5");
		AddIcon(UniqueIds.Coal, UniqueIds.UnCommonMat, 23, "§f§lCoal", "B6");

		AddIcon(UniqueIds.Flint, UniqueIds.UnCommonMat, 24, "§f§lFlint", "B7");
		AddIcon(UniqueIds.Creeper, UniqueIds.UnCommonMat, 25, "§f§lCreeper", "B8");
		AddIcon(UniqueIds.Compass, UniqueIds.UnCommonMat, 26, "§f§lCompass", "B9");

		// RareMat
		AddIcon(UniqueIds.Spider, UniqueIds.RareMat, 18, "§f§lMob Eye", "C1");
		AddIcon(UniqueIds.Glowstone, UniqueIds.RareMat, 19, "§f§lGlowstone", "C2");
		AddIcon(UniqueIds.Quartz, UniqueIds.RareMat, 20, "§f§lQuartz", "C3");

		AddIcon(UniqueIds.Cream, UniqueIds.RareMat, 21, "§f§lMagma Cream", "C4");
		AddIcon(UniqueIds.Iron, UniqueIds.RareMat, 22, "§f§lIron", "C5");
		AddIcon(UniqueIds.Gold, UniqueIds.RareMat, 23, "§f§lGold", "C6");

		AddIcon(UniqueIds.PrisShard, UniqueIds.RareMat, 24, "§f§lPrismarine Shard", "C7");
		AddIcon(UniqueIds.PrisCrystal, UniqueIds.RareMat, 25, "§f§lPrismarine Crystal", "C8");
		AddIcon(UniqueIds.Watch, UniqueIds.RareMat, 26, "§f§lClock", "C9");

		// EpicMat
		AddIcon(UniqueIds.Ferm, UniqueIds.EpicMat, 18, "§f§lSpecial Mob Eye", "D1");
		AddIcon(UniqueIds.Tear, UniqueIds.EpicMat, 19, "§f§lGhast Tear", "D2");
		AddIcon(UniqueIds.Blaze, UniqueIds.EpicMat, 20, "§f§lBlaze Powder", "D3");

		AddIcon(UniqueIds.Slime, UniqueIds.EpicMat, 21, "§f§lSlimeball", "D4");
		AddIcon(UniqueIds.Ender, UniqueIds.EpicMat, 22, "§f§lEye of Ender", "D5");
		AddIcon(UniqueIds.Diamond, UniqueIds.EpicMat, 23, "§f§lDiamond", "D6");

		AddIcon(UniqueIds.Emerald, UniqueIds.EpicMat, 24, "§f§lEmerald", "D6");
		AddIcon(UniqueIds.Bottle, UniqueIds.EpicMat, 25, "§f§lEnchanting Bottle", "D7");
		AddIcon(UniqueIds.Star, UniqueIds.EpicMat, 26, "§f§lNether Star", "D8");

		_isInit = true;
	}

	private static void AddIcon(int uniqueId, int materialGroupId, int position, String displayName, String dbAccessName) {

		MaterialIcon materialIcon = new MaterialIcon(uniqueId, materialGroupId, position, displayName, dbAccessName);
		MaterialInfo materialInfo = MaterialInfos.GetMaterialItem(uniqueId);
		_materialIcons.put(uniqueId, materialIcon);
		_mcMaterialDict.put(materialInfo.GetMaterial(), materialIcon);
	}

	public static boolean GetIsInit() {
		return _isInit;
	}

	public static int[] FindMaterialGroupIcons(int materialGroupId) {

		List<Integer> materialGroupIcons = new ArrayList<Integer>();

		_materialIcons.entrySet().forEach(entry -> {

			int curMaterialGroupId = entry.getValue().GetMaterialGroupId();
			if (curMaterialGroupId == materialGroupId)
				materialGroupIcons.add(entry.getKey());
		});

		int[] materialGroupIconsArray = materialGroupIcons.stream().mapToInt(i -> i).toArray();

		return materialGroupIconsArray;

	}

	public static MaterialIcon GetMaterialIconFromId(int uniqueId) {
		return _materialIcons.get(uniqueId);
	}

	public static BaseIcon GetBaseIconFromId(int uniqueId) {
		return _materialIcons.get(uniqueId);
	}

	public static MaterialIcon TryGetMaterialIconFromMcMaterial(Material mcMaterial) {

		if (!_mcMaterialDict.containsKey(mcMaterial))
			return null;

		return _mcMaterialDict.get(mcMaterial);
	}

	public static void AddToInventory(Inventory inventory, int uniqueId, int position, int typeId) {

		BaseIcon baseIcon = GetBaseIconFromId(uniqueId);
		BaseIcons.AddToInventory(baseIcon, inventory, uniqueId, position, typeId);
	}

	public static void ListWeaponIcons() {

		for (HashMap.Entry<Integer, MaterialIcon> entry : _materialIcons.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
	}

}
