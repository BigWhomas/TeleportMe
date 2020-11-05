package whomas.randomtp.plugin;

import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class AntiDeath implements Listener {
	public static HashMap<Player, Location> back = new HashMap<Player, Location>();
	public static HashMap<Player, Location> death = new HashMap<Player, Location>();
	public static HashMap<Player, Location> temploc = new HashMap<Player, Location>();
	public static HashMap<Player, Player> tpaloc = new HashMap<Player, Player>();
	TeleportMe configGetter;
	public AntiDeath(TeleportMe plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.configGetter = plugin;
	}

	@EventHandler
	public void onTPME(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		configGetter.getConfig().getString("PrefixColour");
		configGetter.getConfig().getString("Prefix");
		configGetter.getConfig().getString("MessageColour");
		if(event.getMessage().equalsIgnoreCase("/tpme random")) {
			if(configGetter.getConfig().getBoolean("TpmeEnabled")) {
				if (player.hasPermission("player.TPME")) {
					back.remove(player);
					if (event.getPlayer().getVelocity().getY() < 0) {
						back.put(player, player.getLocation());
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		if(configGetter.getConfig().getBoolean("TpdeathEnabled")) {
			Player player = event.getEntity();
			death.put(player, player.getLocation());
		}
			
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		String prefixColour = configGetter.getConfig().getString("PrefixColour");
		String prefix = configGetter.getConfig().getString("Prefix");
		String messageColour = configGetter.getConfig().getString("MessageColour");
		if(configGetter.getConfig().getBoolean("TpdeathEnabled")) {
			if (player.hasPermission("player.TpDeath")) {
				if(configGetter.getConfig().getBoolean("AnnounceDeathloc")) {
					player.sendMessage(ChatColor.valueOf(prefixColour)+prefix+ChatColor.valueOf(messageColour)+"Your death location has been saved, do /tpme death to return to it.");
					}
				}
		
			}
		
		}
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		if(event.getMessage().equalsIgnoreCase("/tpme temp")) {
			temploc.put(player, player.getLocation());		
			back.remove(player);
		}
			
	
	}
		
}

