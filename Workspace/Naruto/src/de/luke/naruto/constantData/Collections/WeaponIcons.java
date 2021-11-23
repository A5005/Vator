package de.luke.naruto.constantData.Collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import de.luke.naruto.constantData.Ids.UniqueIds;
import de.luke.naruto.constantData.Items.BaseIcon;
import de.luke.naruto.constantData.Items.Cost;
import de.luke.naruto.constantData.Items.MaterialIcon;
import de.luke.naruto.constantData.Items.MaterialInfo;
import de.luke.naruto.constantData.Items.WeaponIcon;

public class WeaponIcons extends BaseIcons {

	private static HashMap<Integer, WeaponIcon> _weaponIcons;
	private static HashMap<Material, WeaponIcon> _mcMaterialDict;

	private static boolean _isInit = false;

	@SuppressWarnings("serial")
	public static void Create() {

		_weaponIcons = new HashMap<Integer, WeaponIcon>();
		_mcMaterialDict = new HashMap<Material, WeaponIcon>();

		ArrayList<Cost> costs;

		// CommonWeap
		costs = new ArrayList<Cost>(Arrays.asList(new Cost(UniqueIds.Stick, 2), new Cost(UniqueIds.Feather, 5)));
		AddIcon(UniqueIds.Arrow, UniqueIds.CommonWeap, 18, "Throwing Knife", "number", costs, ChatColor.WHITE);

		costs = new ArrayList<Cost>(Arrays.asList(new Cost(UniqueIds.Stick, 2), new Cost(UniqueIds.Leather, 5)));
		AddIcon(UniqueIds.WoodAxe, UniqueIds.CommonWeap, 19, "Throwing Axe", "number", costs, ChatColor.WHITE);

		// UnCommonWeap
		costs = new ArrayList<Cost>(Arrays.asList(new Cost(UniqueIds.Stick, 20), new Cost(UniqueIds.Clay, 5), new Cost(UniqueIds.StonePresPlate, 3)));
		AddIcon(UniqueIds.StoneAxe, UniqueIds.UnCommonWeap, 18, "Throwing Axe", "number", costs, ChatColor.WHITE);

		// RareWeap
		costs = new ArrayList<Cost>(Arrays.asList(new Cost(UniqueIds.Stick, 100), new Cost(UniqueIds.Clay, 20), new Cost(UniqueIds.Spider, 2), new Cost(UniqueIds.Iron, 3)));
		AddIcon(UniqueIds.IronAxe, UniqueIds.RareWeap, 18, "Throwing Axe", "number", costs, ChatColor.WHITE);

		// LegendWeap
		costs = new ArrayList<Cost>(Arrays.asList(new Cost(UniqueIds.Stick, 500), new Cost(UniqueIds.Clay, 100), new Cost(UniqueIds.Spider, 15), new Cost(UniqueIds.Slime, 1), new Cost(UniqueIds.Diamond, 3)));
		AddIcon(UniqueIds.DiamondAxe, UniqueIds.LegendWeap, 18, "Throwing Axe", "number", costs, ChatColor.WHITE);

		_isInit = true;
	}

	public static boolean GetIsInit() {
		return _isInit;
	}

	private static void AddIcon(int uniqueId, int weaponGroupId, int position, String displayName, String dbAccessName, ArrayList<Cost> costs, ChatColor chatColor) {

		WeaponIcon weaponIcon = new WeaponIcon(uniqueId, weaponGroupId, position, displayName, dbAccessName, costs, chatColor);
		MaterialInfo materialInfo = MaterialInfos.GetMaterialItem(uniqueId);
		_weaponIcons.put(uniqueId, weaponIcon);
		_mcMaterialDict.put(materialInfo.GetMaterial(), weaponIcon);
	}

	public static int[] FindMaterialGroupIcons(int weaponGroupId) {

		List<Integer> _weaponGroupIcons = new ArrayList<Integer>();

		_weaponIcons.entrySet().forEach(entry -> {

			int curWeaponGroupId = entry.getValue().GetWeaponGroupId();
			if (curWeaponGroupId == weaponGroupId)
				_weaponGroupIcons.add(entry.getKey());
		});

		int[] weaponGroupIconsArray = _weaponGroupIcons.stream().mapToInt(i -> i).toArray();

		return weaponGroupIconsArray;

	}

	public static WeaponIcon TryGetMaterialIconFromMcMaterial(Material mcMaterial) {

		if (!_mcMaterialDict.containsKey(mcMaterial))
			return null;

		return _mcMaterialDict.get(mcMaterial);
	}

	public static WeaponIcon GetWeaponIconFromId(int uniqueId) {
		return _weaponIcons.get(uniqueId);
	}

	public static BaseIcon GetBaseIconFromId(int uniqueId) {
		return _weaponIcons.get(uniqueId);
	}

	public static void AddToInventory(Inventory inventory, int uniqueId, int position, int typeId, List<String> lore) {

		BaseIcon baseIcon = GetBaseIconFromId(uniqueId);
		BaseIcons.AddToInventory(baseIcon, inventory, uniqueId, position, typeId, lore);
	}

	public static void ListWeaponIcons() {

		for (HashMap.Entry<Integer, WeaponIcon> entry : _weaponIcons.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
	}

}
