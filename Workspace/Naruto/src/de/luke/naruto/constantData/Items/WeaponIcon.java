package de.luke.naruto.constantData.Items;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;

public class WeaponIcon extends BaseIcon {

	private int _weaponGroupId;
	private ArrayList<Cost> _costs;

	public WeaponIcon(int uniqueId, int weaponGroupId, int position, String displayName, String dbAccessName, ArrayList<Cost> costs, ChatColor chatColor) {

		super(uniqueId, position, displayName, dbAccessName, chatColor);
		_weaponGroupId = weaponGroupId;
		_costs = costs;
	}

	public int GetWeaponGroupId() {
		return _weaponGroupId;
	}

	public ArrayList<Cost> GetCosts() {
		return _costs;
	}

}
