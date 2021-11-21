package de.luke.naruto.constantData.Collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.inventory.Inventory;

import de.luke.naruto.constantData.Ids.UniqueIds;
import de.luke.naruto.constantData.Items.BaseIcon;
import de.luke.naruto.constantData.Items.MaterialIcon;
import de.luke.naruto.constantData.Items.WeaponIcon;

public class WeaponIcons extends BaseIcons {

	private static HashMap<Integer, WeaponIcon> _weaponIcons;

	private static boolean _isInit = false;

	@SuppressWarnings("serial")
	public static void Create() {

		_weaponIcons = new HashMap<Integer, WeaponIcon>();

		HashMap<Integer, Integer> costs;

		costs = new HashMap<Integer, Integer>() {
			{
				put(UniqueIds.Stick, 2);
				put(UniqueIds.Feather, 5);
			}
		};

		// CommonWeap
		AddIcon(UniqueIds.Arrow, UniqueIds.CommonWeap, 18, "§f§lThrowing Knife", "number", costs);

		costs = new HashMap<Integer, Integer>() {
			{
				put(UniqueIds.Stick, 3);
				put(UniqueIds.Leather, 5);
			}
		};

		AddIcon(UniqueIds.WoodAxe, UniqueIds.CommonWeap, 19, "§f§lThrowing Axe", "number", costs);

		// UnCommonWeap

		costs = new HashMap<Integer, Integer>() {
			{
				put(UniqueIds.Stick, 20);
				put(UniqueIds.Clay, 5);
				put(UniqueIds.StonePresPlate, 3);
			}
		};

		AddIcon(UniqueIds.StoneAxe, UniqueIds.UnCommonWeap, 18, "§f§lThrowing Axe", "number", costs);

		// RareWeap
		costs = new HashMap<Integer, Integer>() {
			{
				put(UniqueIds.Stick, 100);
				put(UniqueIds.Clay, 20);
				put(UniqueIds.Spider, 2);
				put(UniqueIds.Iron, 3);
			}
		};

		AddIcon(UniqueIds.IronAxe, UniqueIds.RareWeap, 18, "§f§lThrowing Axe", "number", costs);

		// LegendWeap
		costs = new HashMap<Integer, Integer>() {
			{
				put(UniqueIds.Stick, 500);
				put(UniqueIds.Clay, 100);
				put(UniqueIds.Spider, 15);
				put(UniqueIds.Slime, 1);
				put(UniqueIds.Diamond, 3);
			}
		};

		AddIcon(UniqueIds.DiamondAxe, UniqueIds.LegendWeap, 18, "§f§lThrowing Axe", "number", costs);

		_isInit = true;
	}

	public static boolean GetIsInit() {
		return _isInit;
	}

	private static void AddIcon(int uniqueId, int weaponGroupId, int position, String displayName, String dbAccessName, HashMap<Integer, Integer> costs) {

		WeaponIcon weaponIcon = new WeaponIcon(uniqueId, weaponGroupId, position, displayName, dbAccessName, costs);
		_weaponIcons.put(uniqueId, weaponIcon);
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

	public static WeaponIcon GetWeaponIconFromId(int uniqueId) {
		return _weaponIcons.get(uniqueId);
	}

	public static BaseIcon GetBaseIconFromId(int uniqueId) {
		return _weaponIcons.get(uniqueId);
	}
	
	public static void AddToInventory(Inventory inventory, int uniqueId, int position, int typeId) {

		BaseIcon baseIcon = GetBaseIconFromId(uniqueId);
		BaseIcons.AddToInventory(baseIcon, inventory, uniqueId, position, typeId);
	}

	public static void ListWeaponIcons() {

		for (HashMap.Entry<Integer, WeaponIcon> entry : _weaponIcons.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
	}

}
