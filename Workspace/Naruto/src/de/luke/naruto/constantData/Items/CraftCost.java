package de.luke.naruto.constantData.Items;

public class CraftCost extends Cost {

	private int _available;
	private MaterialIcon _materialIcon;

	public CraftCost(MaterialIcon materialIcon, int amount, int available) {
		super(materialIcon.GetUniqueId(), amount);
		_materialIcon = materialIcon;
		_available = available;
	}

	public int GetAvailable() {
		return _available;
	}

	public MaterialIcon GetMaterialIcon() {
		return _materialIcon;
	}

}
