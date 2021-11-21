package de.luke.naruto.constantData.Items;

import org.bukkit.Material;


public class MaterialInfo {

	
	public MaterialInfo(int uniqueId, Material material, int byteValue) {

		_uniqueId = uniqueId;
		_material = material;
		_byteValue = (byte) byteValue;
	}

	private int _uniqueId;
	private Material _material;
	private byte _byteValue;
	
	
	public int GetUniqueId() {
		return _uniqueId;
	}


	public Material GetMaterial() {
		return _material;
	}

	public byte GetbyteValue() {
		return _byteValue;
	}

}
