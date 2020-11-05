package whomas.randomtp.plugin;

import java.io.File;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import whomas.randomtp.commands.Home;
import whomas.randomtp.commands.HomeTabComplete;
import whomas.randomtp.commands.Tpme;
import whomas.randomtp.commands.TpmeTabComplete;
import whomas.randomtp.commands.Warp;
import whomas.randomtp.commands.WarpTabComplete;
import whomas.randomtp.configs.CustomConfig;
import whomas.randomtp.configs.WarpConfig;

public class TeleportMe extends JavaPlugin {
	
	public Permission playerPerm1 = new Permission("player.TPME");
	public Permission playerPerm2 = new Permission("player.GoBack");
	public Permission playerPerm3 = new Permission("player.TpDeath");
	public Permission playerPerm4 = new Permission("player.Coords");
	public Permission playerPerm5 = new Permission("player.TMP");
	public Permission playerPerm6 = new Permission("player.TPTemp");
	public Permission playerPerm7 = new Permission("player.SetHome");
	public Permission playerPerm8 = new Permission("player.GoHome");
	public Permission playerPerm9 = new Permission("player.ConfigReload");
	public Permission playerPerm10 = new Permission("player.SetWarp");
	public Permission playerPerm11 = new Permission("player.GoWarp");
	public Permission playerPerm12 = new Permission("player.UpdateWarning");
	public Permission playerPerm13 = new Permission("player.ListHomes");
	public Permission playerPerm14 = new Permission("player.ListWarps");
	public Permission playerPerm15 = new Permission("player.DelHome");
	public Permission PlayerPerm16 = new Permission("player.TPA");
	
	@Override
	public void onEnable() {
		
		//Calls Plugin Manager as pm
		PluginManager pm = getServer().getPluginManager();
		//Uses
		//Adds previously set permissions
		pm.addPermission(playerPerm1);
		pm.addPermission(playerPerm2);
		pm.addPermission(playerPerm3);
		pm.addPermission(playerPerm4);
		pm.addPermission(playerPerm5);
		pm.addPermission(playerPerm6);
		pm.addPermission(playerPerm7);
		pm.addPermission(playerPerm8);
		pm.addPermission(playerPerm9);
		pm.addPermission(playerPerm10);
		pm.addPermission(playerPerm11);
		pm.addPermission(playerPerm12);
		pm.addPermission(playerPerm13);
		pm.addPermission(playerPerm14);
		pm.addPermission(playerPerm15);
		pm.addPermission(PlayerPerm16);
		//Creates an instance of the main class within each class
		new AntiDeath(this);
		new TeleportUtils(this);
		getCommand("tpme").setExecutor(new Tpme(this));
		getCommand("home").setExecutor(new Home(this));
		getCommand("warp").setExecutor(new Warp(this));
		getCommand("home").setTabCompleter(new HomeTabComplete());
		getCommand("tpme").setTabCompleter(new TpmeTabComplete());
		getCommand("warp").setTabCompleter(new WarpTabComplete());
		//Saves the default config stored within the main directory
		if (new File(getDataFolder(), "config.yml").exists()) {	
			getLogger().info("> !!Config file already exists, skipping creation!!");
			getLogger().info("> !!Remember to delete old config files upon updating!!");
		}
		else {
			saveDefaultConfig();
			getLogger().info("> !!Config file not found, creating new one!!");
			getLogger().info("> !!Remember to delete old config files upon updating!!");
		}
		getConfig();
		
		CustomConfig.setup();
		WarpConfig.setup();

		//Sends Messages To Console
		 getLogger().info("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		 getLogger().info("~~TeleportMe By BigWhomas~~");
		 getLogger().info("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		 getLogger().info("> Please Check The Config File To Alter Settings,");
		 getLogger().info("> When Changing Settings Read The Config Carefully,");
		 getLogger().info("> Please Report Any Bugs or Errors Found To The Spigot Disscusion Page,");
		 getLogger().info("> Thank You For Downloading My Plugin, Enjoy!");
		 getLogger().info("> Special Thanks To FireBlade For MCSS As It Made Development Much Easier ;).");
	}
	
	
	@Override
	public void onDisable() {
		
	}
	


}

