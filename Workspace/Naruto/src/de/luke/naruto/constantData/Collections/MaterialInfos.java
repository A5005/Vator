package de.luke.naruto.constantData.Collections;

import java.util.HashMap;
import org.bukkit.Material;

import de.luke.naruto.constantData.Ids.UniqueIds;
import de.luke.naruto.constantData.Items.MaterialInfo;

public class MaterialInfos {

	

	private static HashMap<Integer, MaterialInfo> _materialInfos;

	public static void Create() {

		_materialInfos = new HashMap< Integer, MaterialInfo>();

		//MaterialGroupIcons
		AddMaterialItem(UniqueIds.CommonMat, Material.INK_SACK, 8);
		AddMaterialItem(UniqueIds.UnCommonMat, Material.INK_SACK, 10);
		AddMaterialItem(UniqueIds.RareMat, Material.INK_SACK, 12);
		AddMaterialItem(UniqueIds.EpicMat, Material.INK_SACK, 5);
		
		//WeaponGroupIcons
		AddMaterialItem(UniqueIds.CommonWeap, Material.INK_SACK, 15);
		AddMaterialItem(UniqueIds.UnCommonWeap, Material.INK_SACK, 2);
		AddMaterialItem(UniqueIds.RareWeap, Material.INK_SACK, 5);
		AddMaterialItem(UniqueIds.LegendWeap, Material.INK_SACK, 11);
		AddMaterialItem(UniqueIds.ExclusiveWeap, Material.INK_SACK, 1);

		//CommonMat
		AddMaterialItem(UniqueIds.Stick, Material.STICK, 0);
		AddMaterialItem(UniqueIds.WoodenBtn, Material.WOOD_BUTTON, 0);
		AddMaterialItem(UniqueIds.StoneBtn, Material.STONE_BUTTON, 0);
		AddMaterialItem(UniqueIds.WoodenPresPlate, Material.WOOD_PLATE, 0);
		AddMaterialItem(UniqueIds.StonePresPlate, Material.STONE_PLATE, 0);
		AddMaterialItem(UniqueIds.Feather, Material.FEATHER, 0);
		AddMaterialItem(UniqueIds.Leather, Material.LEATHER, 0);
		AddMaterialItem(UniqueIds.Paper, Material.PAPER, 0);
		AddMaterialItem(UniqueIds.Book, Material.BOOK, 0);
		
		//UnCommonMat
		AddMaterialItem(UniqueIds.String, Material.STRING, 0);
		AddMaterialItem(UniqueIds.Bone, Material.BONE, 0);
		AddMaterialItem(UniqueIds.Brick, Material.BRICK, 0);
		AddMaterialItem(UniqueIds.NeBrick, Material.NETHER_BRICK, 0);
		AddMaterialItem(UniqueIds.Clay, Material.CLAY, 0);
		AddMaterialItem(UniqueIds.Coal, Material.COAL, 0);
		AddMaterialItem(UniqueIds.Flint, Material.FLINT, 0);
		//AddMaterialItem(UniqueIds.Creeper, Material.GUNPOWDER, 0);
		//AddMaterialItem(UniqueIds.Firework, Material.FIREWORK_STAR, 0);
		
		//RareMat
		AddMaterialItem(UniqueIds.Spider, Material.SPIDER_EYE, 0);
		AddMaterialItem(UniqueIds.Glowstone, Material.GLOWSTONE_DUST, 0);
		//AddMaterialItem(UniqueIds.Quartz, Material.NETHER_QUARTZ, 0);
		//AddMaterialItem(UniqueIds.Cream, Material.MAGMA_CREAM, 0);
		AddMaterialItem(UniqueIds.Iron, Material.IRON_INGOT, 0);
		AddMaterialItem(UniqueIds.Gold, Material.GOLD_INGOT, 0);
		AddMaterialItem(UniqueIds.PrisShard, Material.PRISMARINE_SHARD, 0);
		AddMaterialItem(UniqueIds.PrisCrystal, Material.PRISMARINE_CRYSTALS, 0);
		AddMaterialItem(UniqueIds.Compass, Material.COMPASS, 0);
		
		//EpicMat
		AddMaterialItem(UniqueIds.Tear, Material.GHAST_TEAR, 0);
		AddMaterialItem(UniqueIds.Blaze, Material.BLAZE_POWDER, 0);
		AddMaterialItem(UniqueIds.Slime, Material.SLIME_BALL, 0);
		AddMaterialItem(UniqueIds.Ender, Material.EYE_OF_ENDER, 0);
		AddMaterialItem(UniqueIds.Diamond, Material.DIAMOND, 0);
		AddMaterialItem(UniqueIds.Emerald, Material.EMERALD, 0);
		AddMaterialItem(UniqueIds.Bottle, Material.EXP_BOTTLE, 0);
		AddMaterialItem(UniqueIds.Star, Material.NETHER_STAR, 0);
		//AddMaterialItem(UniqueIds.Crystal, Material.END_CRYSTAL, 0);
		

		//CommonWeap
		AddMaterialItem(UniqueIds.Arrow, Material.ARROW, 0);
		AddMaterialItem(UniqueIds.WoodAxe, Material.WOOD_AXE, 0);
		
		//UnCommonWeap
		AddMaterialItem(UniqueIds.StoneAxe, Material.STONE_AXE, 0);

		//RareWeap
		AddMaterialItem(UniqueIds.IronAxe, Material.IRON_AXE, 0);
		
		//ExclusiveWeap
		AddMaterialItem(UniqueIds.DiamondAxe, Material.DIAMOND_AXE, 0);
		
		//Fill
		AddMaterialItem(UniqueIds.Glass, Material.STAINED_GLASS_PANE, 15);

		//Crafting
		AddMaterialItem(UniqueIds.Workbench, Material.WORKBENCH, 0);
		AddMaterialItem(UniqueIds.GreenWool, Material.WOOL , 5);
		
	}

	private static void AddMaterialItem(int uniqueId, Material material, int byteValue) {

		MaterialInfo materialItem = new MaterialInfo(uniqueId, material, byteValue);
		_materialInfos.put(uniqueId, materialItem);

	}

	public static MaterialInfo GetMaterialItem(int uniqueId) {

		return _materialInfos.get(uniqueId);
	}

}
