package de.luke.naruto.constantData.Collections;

public class CollectionInitializer {

	
	
	public static void InitializeCollections() throws Exception
	{
		
		//Don't change the order
		MaterialIcons.Create();
		WeaponIcons.Create();
		
		
		//construct MaterialIcons as sub objects
		MaterialGroupIcons.Create();
		
		//construct WeaponIcons as sub objects
		WeaponGroupIcons.Create();
		

	}
	
}
