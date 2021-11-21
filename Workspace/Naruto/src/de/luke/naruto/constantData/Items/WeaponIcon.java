package de.luke.naruto.constantData.Items;

import java.util.HashMap;

public class WeaponIcon extends BaseIcon {

	private int _weaponGroupId;
	private HashMap<Integer, Integer> _costs;

	public WeaponIcon(int uniqueId, int weaponGroupId, int position, String displayName, String dbAccessName, HashMap<Integer, Integer> costs) {

		super(uniqueId, position, displayName, dbAccessName);
		_weaponGroupId = weaponGroupId;
		_costs = costs;
	}

	public int GetWeaponGroupId() {
		return _weaponGroupId;
	}

	public HashMap<Integer, Integer> GetCosts() {
		return _costs;
	}

}
