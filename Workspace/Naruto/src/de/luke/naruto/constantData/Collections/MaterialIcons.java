package de.luke.naruto.constantData.Collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	private static boolean _isInit = false;

	public static void Create() {

		_materialIcons = new HashMap<Integer, MaterialIcon>();

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

		// RareMat
		AddIcon(UniqueIds.Spider, UniqueIds.RareMat, 18, "§f§lSpider", "C1");
		AddIcon(UniqueIds.Glowstone, UniqueIds.RareMat, 19, "§f§lGlowstone", "C2");
		AddIcon(UniqueIds.Iron, UniqueIds.RareMat, 20, "§f§lIron", "C3");
		AddIcon(UniqueIds.Gold, UniqueIds.RareMat, 21, "§f§lGold", "C4");
		AddIcon(UniqueIds.PrisShard, UniqueIds.RareMat, 22, "§f§lPrismarine Shard", "C5");
		AddIcon(UniqueIds.PrisCrystal, UniqueIds.RareMat, 23, "§f§lPrismarine Crystal", "C6");
		AddIcon(UniqueIds.Compass, UniqueIds.RareMat, 24, "§f§lCompass", "C7");

		// EpicMat
		AddIcon(UniqueIds.Tear, UniqueIds.EpicMat, 18, "§f§lGhast Tear", "D1");
		AddIcon(UniqueIds.Blaze, UniqueIds.EpicMat, 19, "§f§lBlaze Powder", "D2");
		AddIcon(UniqueIds.Slime, UniqueIds.EpicMat, 20, "§f§lSlimeball", "D3");
		AddIcon(UniqueIds.Ender, UniqueIds.EpicMat, 21, "§f§lEye of Ender", "D4");
		AddIcon(UniqueIds.Diamond, UniqueIds.EpicMat, 22, "§f§lDiamond", "D5");
		AddIcon(UniqueIds.Emerald, UniqueIds.EpicMat, 23, "§f§lEmerald", "D6");
		AddIcon(UniqueIds.Bottle, UniqueIds.EpicMat, 24, "§f§lEnchanting Bottle", "D7");

		_isInit = true;
	}

	private static void AddIcon(int uniqueId, int materialGroupId, int position, String displayName, String dbAccessName) {

		MaterialIcon materialIcon = new MaterialIcon(uniqueId, materialGroupId, position, displayName, dbAccessName);
		_materialIcons.put(uniqueId, materialIcon);
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
