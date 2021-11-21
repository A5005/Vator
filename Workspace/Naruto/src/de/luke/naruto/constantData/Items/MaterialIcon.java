package de.luke.naruto.constantData.Items;

public class MaterialIcon extends BaseIcon {

	private int _materialId;

	public MaterialIcon(int uniqueId, int materialGroupId, int position, String displayName, String dbAccessName) {
		super(uniqueId, position, displayName, dbAccessName);
		_materialId = materialGroupId;
	}

	public int GetMaterialGroupId() {
		return _materialId;
	}

}
