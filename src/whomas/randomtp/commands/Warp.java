package whomas.randomtp.commands;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import whomas.randomtp.configs.WarpConfig;
import whomas.randomtp.plugin.TeleportMe;

public class Warp implements CommandExecutor {
	TeleportMe configGetter;

	public Warp(TeleportMe plugin) {
		this.configGetter = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return true;
		{
			Player p = (Player) sender;
			String prefixColour = configGetter.getConfig().getString("PrefixColour", "WHITE");
			String prefix = configGetter.getConfig().getString("Prefix");
			String messageColour = configGetter.getConfig().getString("MessageColour", "GREEN");
			if (configGetter.getConfig().getBoolean("WarpsEnabled")) {
				if (label.equalsIgnoreCase("warp")) {
					if (args.length == 0) {
						p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED + "Invalid Arguments");
						p.sendMessage(
								ChatColor.valueOf(prefixColour) + prefix + ChatColor.WHITE + "Please specifiy an action");
						p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
								+ "Available Actions: "+ChatColor.YELLOW+"Set, List, Go, Delete, Reload");
					}

//########################################################################################################################################################################################################
//Set Warp
//########################################################################################################################################################################################################			

					if (args.length > 0) {
						if (args[0].equalsIgnoreCase("set")) {
							if (configGetter.getConfig().getBoolean("SetWarpEnabled")) {
								if (p.hasPermission("player.SetWarp")) {
									if (args.length == 2) {
										String warpname = args[1].toString().toLowerCase();
										WarpConfig.get().set("Warps" + "." + warpname, p.getLocation());
										WarpConfig.save();
										if (configGetter.getConfig().getBoolean("DisplayWarpSet")) {
											p.sendMessage(ChatColor.valueOf(prefixColour) + prefix
													+ ChatColor.valueOf(messageColour)
													+ "A Warp Has Been Created Called: " + warpname);
										}

									} else {
										p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
												+ "Please enter a name for the warp");
									}
								} else {
									p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
											+ "You don't have permission to use this");
								}

							} else {
								p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
										+ "This command is currently disabled");
							}
						}

//########################################################################################################################################################################################################
//List Warps
//########################################################################################################################################################################################################

						if (args[0].equalsIgnoreCase("list")) {
							if (WarpConfig.get().contains("Warps")) {
								if (p.hasPermission("player.ListWarps")) {
									Set<String> playerHouses = WarpConfig.get().getConfigurationSection("Warps")
											.getKeys(false);
									if (playerHouses == null) {
										p.sendMessage("It returned null");
										return true;
									}

									p.sendMessage(ChatColor.valueOf(prefixColour) + prefix
											+ ChatColor.valueOf(messageColour) + "Warps Found: " + playerHouses);
								} else {
									p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
											+ "You don't have permission to use this");
								}
							}

							else {
								p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
										+ "No Warps Were Found");
							}
						}

//########################################################################################################################################################################################################
//Go Warp
//########################################################################################################################################################################################################

						if (args[0].equalsIgnoreCase("go")) {
							if (configGetter.getConfig().getBoolean("GoWarpEnabled")) {
								if (p.hasPermission("player.GoWarp")) {
									if (args.length == 2) {
										String warpname = args[1].toString().toLowerCase();
										Location location = (Location) WarpConfig.get().get("Warps" + "." + warpname);
										if (location == null) {
											if (configGetter.getConfig().getBoolean("NoWarp")) {
												p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
														+ " You currently have no warp location set");
											}

											return true;
										}

										p.teleport(location);
										if (configGetter.getConfig().getBoolean("GoWarp")) {
											p.sendMessage(ChatColor.valueOf(prefixColour) + prefix
													+ ChatColor.valueOf(messageColour) + "Successfully sent to: "
													+ warpname);
										}

									} else {
										p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
												+ "Please enter the name of the warp");
									}
									return true;
								} else {
									p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
											+ "You don't have permission to use this");
								}

							} else {
								p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
										+ "This command is currently disabled");
							}
						}

//########################################################################################################################################################################################################
//Delete Warps
//########################################################################################################################################################################################################

						if (args[0].equalsIgnoreCase("delete")) {
							if (configGetter.getConfig().getBoolean("DelHomeEnabled")) {
								if (p.hasPermission("player.DelHome")) {
									if (args.length == 2) {
										String warpname = args[1].toString().toLowerCase();
										Location location = (Location) WarpConfig.get().get("Warps" + "." + warpname);
										if (location == null) {
											if (configGetter.getConfig().getBoolean("NoWarp")) {
												p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
														+ " No warp of that name found");
											}

											return true;
										}

										WarpConfig.get().set("Warps" + "." + warpname, null);
										WarpConfig.save();
										if (configGetter.getConfig().getBoolean("DelWarp")) {
											p.sendMessage(ChatColor.valueOf(prefixColour) + prefix
													+ ChatColor.valueOf(messageColour) + "Warp successfully removed");
										}

									} else {
										p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
												+ "Please enter the name of the warp");
									}
									return true;
								} else {
									p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
											+ "You don't have permission to use this");
								}

							} else {
								p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
										+ "This command is currently disabled");
							}
						}

//########################################################################################################################################################################################################
//Reload Warp Config
//########################################################################################################################################################################################################

						if (args[0].equalsIgnoreCase("reload")) {
							if (configGetter.getConfig().getBoolean("CFGReloadEnabled")) {
								if (p.hasPermission("player.ConfigReload")) {
									WarpConfig.reload();
									p.sendMessage(ChatColor.valueOf(prefixColour) + prefix
											+ ChatColor.valueOf(messageColour) + "Warp Config reloaded");
								} else {
									p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
											+ "You don't have permission to use this");
								}

							} else {
								p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
										+ "This command is currently disabled");
							}
						} 

					}

				} else {
					p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
							+ "This should never appear! Please contact BigWhomas on Spigot ASAP!");
				}
			} else {
				p.sendMessage(
						ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED + "Warps are currently disabled");
			}

		}

		return true;
	}
}