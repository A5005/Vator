package de.luke.naruto;

import de.luke.weapons.WeaponHelix;
import de.luke.weapons.WeaponLinear;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

//https://helpch.at/docs/1.8.8/

public class AttackListener implements Listener {

	private Plugin _plugin;

	public AttackListener(Plugin plugin) {
		_plugin = plugin;
	}

	@EventHandler
	public void onLeftClick(PlayerInteractEvent event) {

		Player player = event.getPlayer();
		if (player == null)
			return;

		Action action = event.getAction();

		if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {

			// TODO Differentiate weapons
			WeaponHelix weaponHelix = new WeaponHelix();
			weaponHelix.execute(true, player, _plugin);

			//WeaponLinear weaponLinear = new WeaponLinear();
			//weaponLinear.execute(true, player, _plugin);
		}

	}

}
