package de.luke.naruto.constantData.Items;

import java.util.HashMap;

public class WeaponIcon {

	private int _uniqueId;
	private int _position;
	private int _weaponGroupId;
	private String _displayName;
	private String _dbAccessName;
	private HashMap<Integer, Integer> _costs;

	public WeaponIcon(int uniqueId, int weaponGroupId, int position, String displayName, String dbAccessName,  HashMap<Integer, Integer> costs) {

		_uniqueId = uniqueId;
		_weaponGroupId = weaponGroupId;
		_displayName = displayName;
		_position = position;
		_dbAccessName = dbAccessName;
		_costs = costs;
	}

	public int GetUniqueId() {
		return _uniqueId;
	}

	public int GetWeaponGroupId() {
		return _weaponGroupId;
	}

	public int GetPosition() {
		return _position;
	}

	public String GetDisplayName() {
		return _displayName;
	}

	public String GetDbAccessName() {
		return _dbAccessName;
	}
	
	public HashMap<Integer, Integer> GetCosts()
	{
		return _costs; 
	}

}
