package de.luke.naruto.constantData.Items;

import org.bukkit.ChatColor;

public class WeaponGroupIcon extends BaseIcon {

	private int[] _weaponIconIds;

	public WeaponGroupIcon(int uniqueId, int position, String displayName, String dbAccessName, int[] weaponIconIds, ChatColor chatColor) {

		super(uniqueId, position, displayName, dbAccessName, chatColor);
		_weaponIconIds = weaponIconIds;

	}

	public int[] GetWeaponIconIds() {
		return _weaponIconIds;
	}
}
