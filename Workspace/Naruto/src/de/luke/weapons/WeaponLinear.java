package de.luke.weapons;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class WeaponLinear extends WeaponBase {

	public WeaponLinear() {

	}
	
	@Override
	protected String getFileName() {
		return "customConfig.yml";
	}

	@Override
	public  void execute(Boolean loadParamsFromConfig, Player player, Plugin plugin) {
		super.execute(loadParamsFromConfig, player, plugin);

	}

	@Override
	protected void LoadParamsFromConfig(YamlConfiguration yamlConfiguration) {
		super.LoadParamsFromConfig(yamlConfiguration);
		
		
	}
}
