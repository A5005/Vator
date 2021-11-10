package de.luke.naruto.constantData.Collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.luke.naruto.constantData.Ids.UniqueIds;
import de.luke.naruto.constantData.Items.WeaponIcon;

public class WeaponIcons {

	private static HashMap<Integer, WeaponIcon> _weaponIcons;

	private static boolean _isInit = false;

	@SuppressWarnings("serial")
	public static void Create() {

		_weaponIcons = new HashMap<Integer, WeaponIcon>();

		HashMap<Integer, Integer> costs;

		costs = new HashMap<Integer, Integer>() {
			{
				put(UniqueIds.Stick, 1);
				put(UniqueIds.Paper, 3);
			}
		};

		// TODO One table for each weapon group
		AddIcon(UniqueIds.Arrow, UniqueIds.CommonWeap, 0, "§f§lThrowing Knife", "number", costs);

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

}
