package de.luke.naruto.constantData.Collections;

import java.util.HashMap;
import de.luke.naruto.constantData.Ids.UniqueIds;
import de.luke.naruto.constantData.Items.WeaponGroupIcon;

public class WeaponGroupIcons {

	private static HashMap<Integer, WeaponGroupIcon> _weaponGroupIcons;

	public static void Create() throws Exception {

		if (!WeaponIcons.GetIsInit()) {
			throw new Exception("Call WeaponIcons.Create() first!");
		}

		_weaponGroupIcons = new HashMap<Integer, WeaponGroupIcon>();
		AddIcon(UniqueIds.CommonWeap, 4, "§f§lCommon Weapons", "Materialnumber");
		AddIcon(UniqueIds.UnCommonWeap, 5, "§2§lUncommon Weapons", "Materialnumber");
		AddIcon(UniqueIds.RareWeap, 6, "§1§lRare Weapons", "Materialnumber");
		AddIcon(UniqueIds.LegendWeap, 7, "§e§lLegendary Weapon", "Materialnumber");
		AddIcon(UniqueIds.ExclusiveWeap, 8, "§4§lExclusive Weapons", "Materialnumber");

	}

	private static void AddIcon(int materialInfoId, int position, String displayName, String dbAccessName) {

		// Backward Pointer
		int[] weaponIconIds = WeaponIcons.FindMaterialGroupIcons(materialInfoId);
		_weaponGroupIcons.put(materialInfoId, new WeaponGroupIcon(materialInfoId, position, displayName, dbAccessName, weaponIconIds));
	}

}
