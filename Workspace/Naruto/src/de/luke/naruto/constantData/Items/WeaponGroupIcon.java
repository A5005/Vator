package de.luke.naruto.constantData.Items;

public class WeaponGroupIcon {

	private int _uniqueId;
	private int _position;
	private String _displayName;
	private int[] _weaponIconIds;

	public WeaponGroupIcon(int uniqueId, int position, String displayName, String dbAccessName, int[] weaponIconIds) {

		_uniqueId = uniqueId;
		_weaponIconIds = weaponIconIds;
		_displayName = displayName;
		_position = position;
	}

	public int GetUniqueId() {
		return _uniqueId;
	}

	public int GetPosition() {
		return _position;
	}
	
	public String GetDisplayName() {
		return _displayName;
	}

	public int[] GetWeaponIconIds() {
		return _weaponIconIds;
	}
}
