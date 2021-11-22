package de.luke.naruto.constantData.Collections;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import de.luke.naruto.constantData.Ids.UniqueIds;
import de.luke.naruto.constantData.Items.BaseIcon;
import de.luke.naruto.constantData.Items.MaterialIcon;
import de.luke.naruto.constantData.Items.MaterialInfo;
import de.luke.naruto.database.NarutoDataBase;

public class MaterialIcons {

	private static HashMap<Integer, MaterialIcon> _materialIcons;
	private static HashMap<Material, MaterialIcon> _mcMaterialDict;

	private static boolean _isInit = false;
	public static final String TableName = "Material";
	public static final String UuidName = "ID";

	public static void Create() {

		_materialIcons = new HashMap<Integer, MaterialIcon>();
		_mcMaterialDict = new HashMap<Material, MaterialIcon>();

		// CommonMat
		AddIcon(UniqueIds.Stick, UniqueIds.CommonMat, 18, "Stick", "A1", ChatColor.WHITE);
		AddIcon(UniqueIds.WoodenBtn, UniqueIds.CommonMat, 19, "Wooden Button", "A2", ChatColor.WHITE);
		AddIcon(UniqueIds.StoneBtn, UniqueIds.CommonMat, 20, "Stone Button", "A3", ChatColor.WHITE);

		AddIcon(UniqueIds.WoodenPresPlate, UniqueIds.CommonMat, 21, "Wooden Pressure Plate", "A4", ChatColor.WHITE);
		AddIcon(UniqueIds.StonePresPlate, UniqueIds.CommonMat, 22, "Stone Pressure Plate", "A5", ChatColor.WHITE);
		AddIcon(UniqueIds.Feather, UniqueIds.CommonMat, 23, "Feather", "A6", ChatColor.WHITE);

		AddIcon(UniqueIds.Leather, UniqueIds.CommonMat, 24, "Leather", "A7", ChatColor.WHITE);
		AddIcon(UniqueIds.Paper, UniqueIds.CommonMat, 25, "Paper", "A8", ChatColor.WHITE);
		AddIcon(UniqueIds.Book, UniqueIds.CommonMat, 26, "Book", "A9", ChatColor.WHITE);

		// UnCommonMat
		AddIcon(UniqueIds.String, UniqueIds.UnCommonMat, 18, "Stick", "B1", ChatColor.WHITE);
		AddIcon(UniqueIds.Bone, UniqueIds.UnCommonMat, 19, "Wooden Button", "B2", ChatColor.WHITE);
		AddIcon(UniqueIds.Brick, UniqueIds.UnCommonMat, 20, "Stone Button", "B3", ChatColor.WHITE);

		AddIcon(UniqueIds.NeBrick, UniqueIds.UnCommonMat, 21, "Nether Brick", "B4", ChatColor.WHITE);
		AddIcon(UniqueIds.Clay, UniqueIds.UnCommonMat, 22, "Clay", "B5", ChatColor.WHITE);
		AddIcon(UniqueIds.Coal, UniqueIds.UnCommonMat, 23, "Coal", "B6", ChatColor.WHITE);

		AddIcon(UniqueIds.Flint, UniqueIds.UnCommonMat, 24, "Flint", "B7", ChatColor.WHITE);
		AddIcon(UniqueIds.Creeper, UniqueIds.UnCommonMat, 25, "Creeper", "B8", ChatColor.WHITE);
		AddIcon(UniqueIds.Compass, UniqueIds.UnCommonMat, 26, "Compass", "B9", ChatColor.WHITE);

		// RareMat
		AddIcon(UniqueIds.Spider, UniqueIds.RareMat, 18, "Mob Eye", "C1", ChatColor.WHITE);
		AddIcon(UniqueIds.Glowstone, UniqueIds.RareMat, 19, "Glowstone", "C2", ChatColor.WHITE);
		AddIcon(UniqueIds.Quartz, UniqueIds.RareMat, 20, "Quartz", "C3", ChatColor.WHITE);

		AddIcon(UniqueIds.Cream, UniqueIds.RareMat, 21, "Magma Cream", "C4", ChatColor.WHITE);
		AddIcon(UniqueIds.Iron, UniqueIds.RareMat, 22, "Iron", "C5", ChatColor.WHITE);
		AddIcon(UniqueIds.Gold, UniqueIds.RareMat, 23, "Gold", "C6", ChatColor.WHITE);

		AddIcon(UniqueIds.PrisShard, UniqueIds.RareMat, 24, "Prismarine Shard", "C7", ChatColor.WHITE);
		AddIcon(UniqueIds.PrisCrystal, UniqueIds.RareMat, 25, "Prismarine Crystal", "C8", ChatColor.WHITE);
		AddIcon(UniqueIds.Watch, UniqueIds.RareMat, 26, "Clock", "C9", ChatColor.WHITE);

		// EpicMat
		AddIcon(UniqueIds.Ferm, UniqueIds.EpicMat, 18, "Special Mob Eye", "D1", ChatColor.WHITE);
		AddIcon(UniqueIds.Tear, UniqueIds.EpicMat, 19, "Ghast Tear", "D2", ChatColor.WHITE);
		AddIcon(UniqueIds.Blaze, UniqueIds.EpicMat, 20, "Blaze Powder", "D3", ChatColor.WHITE);

		AddIcon(UniqueIds.Slime, UniqueIds.EpicMat, 21, "Slimeball", "D4", ChatColor.WHITE);
		AddIcon(UniqueIds.Ender, UniqueIds.EpicMat, 22, "Eye of Ender", "D5", ChatColor.WHITE);
		AddIcon(UniqueIds.Diamond, UniqueIds.EpicMat, 23, "Diamond", "D6", ChatColor.WHITE);

		AddIcon(UniqueIds.Emerald, UniqueIds.EpicMat, 24, "Emerald", "D7", ChatColor.WHITE);
		AddIcon(UniqueIds.Bottle, UniqueIds.EpicMat, 25, "Enchanting Bottle", "D8", ChatColor.WHITE);
		AddIcon(UniqueIds.Star, UniqueIds.EpicMat, 26, "Nether Star", "D9", ChatColor.WHITE);

		_isInit = true;
	}

	private static void AddIcon(int uniqueId, int materialGroupId, int position, String displayName, String dbAccessName, ChatColor chatColor) {

		MaterialIcon materialIcon = new MaterialIcon(uniqueId, materialGroupId, position, displayName, dbAccessName, chatColor);
		MaterialInfo materialInfo = MaterialInfos.GetMaterialItem(uniqueId);
		_materialIcons.put(uniqueId, materialIcon);
		_mcMaterialDict.put(materialInfo.GetMaterial(), materialIcon);

	}

	public static boolean GetIsInit() {
		return _isInit;
	}

	public static int[] FindMaterialGroupIcons(int materialGroupId) {

		List<Integer> materialGroupIcons = new ArrayList<Integer>();

		_materialIcons.entrySet().forEach(entry -> {

			int curMaterialGroupId = entry.getValue().GetMaterialGroupId();
			if (curMaterialGroupId == materialGroupId)
				materialGroupIcons.add(entry.getKey());
		});

		int[] materialGroupIconsArray = materialGroupIcons.stream().mapToInt(i -> i).toArray();

		return materialGroupIconsArray;

	}

	public static MaterialIcon GetMaterialIconFromId(int uniqueId) {
		return _materialIcons.get(uniqueId);
	}

	public static BaseIcon GetBaseIconFromId(int uniqueId) {
		return _materialIcons.get(uniqueId);
	}

	public static MaterialIcon TryGetMaterialIconFromMcMaterial(Material mcMaterial) {

		if (!_mcMaterialDict.containsKey(mcMaterial))
			return null;

		return _mcMaterialDict.get(mcMaterial);
	}

	public static void AddToInventory(Inventory inventory, int uniqueId, int position, int typeId) {

		BaseIcon baseIcon = GetBaseIconFromId(uniqueId);
		BaseIcons.AddToInventory(baseIcon, inventory, uniqueId, position, typeId);
	}

	public static void ListWeaponIcons() {

		for (HashMap.Entry<Integer, MaterialIcon> entry : _materialIcons.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
	}

	public static void DbSetAllAmountsToValue(UUID uuid, int value) throws SQLException {

		String uuidString = uuid.toString();
		StringBuilder sb = new StringBuilder();
		StringBuilder sbValues = new StringBuilder();
		sbValues.append(String.format("VALUES (\"%s\"", uuidString));

		sb.append(String.format("INSERT INTO `%s`(`%s`", TableName, UuidName));

		_materialIcons.entrySet().forEach(entry -> {

			MaterialIcon materialIcon = entry.getValue();
			sb.append(String.format(",`%s`", materialIcon.GetDbAccessName()));
			sbValues.append(String.format(",%s", value));

		});

		sbValues.append(")");
		sb.append(") ");
		sb.append(sbValues.toString());

		PreparedStatement preparedStatement = NarutoDataBase.mysql.getConnection().prepareStatement(sb.toString());
		preparedStatement.execute();

	}

	public static HashMap<Material, Integer> DbReadAllAmounts(UUID uuid) throws SQLException {

		String sqlString = String.format("SELECT * FROM `%s` WHERE ID = '%s'", TableName, uuid.toString());

		PreparedStatement st = NarutoDataBase.mysql.getConnection().prepareStatement(sqlString);
		ResultSet rs = st.executeQuery();

		HashMap<Material, Integer> amounts = new HashMap<Material, Integer>();

		while (rs.next()) {

			_materialIcons.entrySet().forEach(entry -> {

				MaterialIcon materialIcon = entry.getValue();

				try {
					int amount = rs.getInt(materialIcon.GetDbAccessName());
					amounts.put(materialIcon.GetMaterialInfo().GetMaterial(), amount);
					System.out.println("DB EXISTING: " + materialIcon.GetDisplayName() + " " + amount);
				} catch (SQLException e) {
					//
					e.printStackTrace();
				}

			});
		}

		return amounts;
	}

	public static void DbWriteAmounts(HashMap<MaterialIcon, Integer> amounts, UUID uuid) throws SQLException {

		StringBuilder sb = new StringBuilder();
		sb.append(String.format("UPDATE `%s` SET ", TableName));

		boolean firstEnty = true;

		for (HashMap.Entry<MaterialIcon, Integer> entry : amounts.entrySet()) {

			MaterialIcon materialIcon = entry.getKey();
			int amount = entry.getValue();

			if (firstEnty) {
				sb.append(String.format("`%s`=%d", materialIcon.GetDbAccessName(), amount));
				firstEnty = false;
			} else {
				sb.append(String.format(",`%s`=%d", materialIcon.GetDbAccessName(), amount));
			}

		}

		if (firstEnty) {
			// Nothing to do
			return;
		}

		sb.append(String.format(" WHERE `id`=\"%s\"", uuid.toString()));
		System.out.println(sb.toString());

		PreparedStatement preparedStatement = NarutoDataBase.mysql.getConnection().prepareStatement(sb.toString());
		preparedStatement.execute();

	}

}
