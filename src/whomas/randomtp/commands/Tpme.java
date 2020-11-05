package whomas.randomtp.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import whomas.randomtp.plugin.AntiDeath;
import whomas.randomtp.plugin.TeleportMe;
import whomas.randomtp.plugin.TeleportUtils;

public class Tpme implements CommandExecutor {
	TeleportMe configGetter;

	public Tpme(TeleportMe plugin) {
		this.configGetter = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("tpme") && sender instanceof Player) {
			Player p = (Player) sender;
			Location ogloc = p.getLocation();
			String prefixColour = configGetter.getConfig().getString("PrefixColour", "WHITE");
			String messageColour = configGetter.getConfig().getString("MessageColour", "GREEN");
			String prefix = configGetter.getConfig().getString("Prefix");
			if (configGetter.getConfig().getBoolean("TeleportsEnabled")) {
				if (label.equalsIgnoreCase("tpme")) {
					if (args.length == 0) {
						p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED + "Invalid Arguments");
						p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.WHITE
								+ "Please specifiy an action");
						p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED + "Available Actions: "
								+ ChatColor.YELLOW + "Random, Temp, GoTemp, Death, Coords, Reload");
					}
					if (args.length > 0) {

//########################################################################################################################################################################################################
//Random TP
//########################################################################################################################################################################################################	

						if (args[0].equalsIgnoreCase("random")) {
							if (configGetter.getConfig().getBoolean("TpmeEnabled")) {
								if (p.hasPermission("player.TPME")) {
									Location oldloc = p.getLocation();
									Location randomLocation = TeleportUtils.findSafeLocation(p);

									/// Possibly Add A Countdown Delay Between The Command & Actual Teleportation

									p.teleport(randomLocation);

									if (configGetter.getConfig().getBoolean("TeleportDistance")) {
										p.sendMessage(ChatColor.valueOf(prefixColour) + prefix
												+ ChatColor.valueOf(messageColour)
												+ "Teleport Successful, You have travelled "
												+ (int) randomLocation.distance(oldloc)
												+ " Blocks Away From Your Previous Location.");
										if (p.hasPermission("player.GoBack")) {
											AntiDeath.back.put(p, oldloc);
											p.sendMessage(ChatColor.valueOf(prefixColour) + prefix
													+ ChatColor.valueOf(messageColour)
													+ "Do /tpme back to return to your previous location");
										}
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
//Set Temp TP
//########################################################################################################################################################################################################		

						if (args[0].equalsIgnoreCase("request")) {
							if (configGetter.getConfig().getBoolean("TpReqEnabled")) {
								if (p.hasPermission("player.TPR")) {
									if (AntiDeath.temploc.containsKey(p)) {
										if (configGetter.getConfig().getBoolean("TmpSaved")) {
											p.sendMessage(ChatColor.valueOf(prefixColour) + prefix
													+ ChatColor.valueOf(messageColour)
													+ "Your one time teleport has been created.");
										}
									} else {
										p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
												+ "Oops, this should not have happened, oh dear.");
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
//TP to Temp
//########################################################################################################################################################################################################	

						if (args[0].equalsIgnoreCase("gotemp")) {
							if (configGetter.getConfig().getBoolean("TPTempEnabled")) {
								if (p.hasPermission("player.TPTemp")) {
									if (AntiDeath.temploc.containsKey(p)) {
										if (configGetter.getConfig().getBoolean("TPTemp")) {
											p.sendMessage(ChatColor.valueOf(prefixColour) + prefix
													+ ChatColor.valueOf(messageColour)
													+ " Your one time teleport has been used.");
										}
										if (p.hasPermission("player.GoBack")) {
											AntiDeath.back.put(p, ogloc);
										}
										p.teleport(AntiDeath.temploc.get(p));
										AntiDeath.temploc.remove(p);
									} else {
										p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
												+ "Oops, it doesn't look like you have a location saved.");
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
//TP Death
//########################################################################################################################################################################################################	

						if (args[0].equalsIgnoreCase("death")) {
							if (configGetter.getConfig().getBoolean("TpdeathEnabled")) {
								if (p.hasPermission("player.TpDeath")) {
									if (AntiDeath.death.containsKey(p)) {
										p.teleport(AntiDeath.death.get(p));
										AntiDeath.death.remove(p);
										if (configGetter.getConfig().getBoolean("DisplayDeathloc")) {
											p.sendMessage(ChatColor.valueOf(prefixColour) + prefix
													+ ChatColor.valueOf(messageColour) + "Returned to death location.");
										}
										return true;
									} else {
										if (configGetter.getConfig().getBoolean("DisplayDeathloc")) {
											p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
													+ "You have no previous death locations.");
										}
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
//Return To Previous Location
//########################################################################################################################################################################################################
						if (args[0].equalsIgnoreCase("back")) {
							if (configGetter.getConfig().getBoolean("GobackEnabled")) {
								if (p.hasPermission("player.GoBack")) {
									if (AntiDeath.back.containsKey(p)) {
										p.teleport(AntiDeath.back.get(p));
										AntiDeath.back.remove(p);
										if (configGetter.getConfig().getBoolean("DisplaySuccess")) {
											p.sendMessage(ChatColor.valueOf(prefixColour) + prefix
													+ ChatColor.valueOf(messageColour)
													+ "Teleport Successful, You have travelled back to your original location.");
										}
										return true;
									} else {
										if (configGetter.getConfig().getBoolean("DisplaySuccess")) {
											p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
													+ "You have no previous teleport locations.");
										}
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
//Display Co-ords
//########################################################################################################################################################################################################

						if (args[0].equalsIgnoreCase("coords")) {
							if (configGetter.getConfig().getBoolean("CoordsEnabled")) {
								if (p.hasPermission("player.Coords")) {
									World ogworl = p.getWorld();
									int x = ogloc.getBlockX();
									int y = ogloc.getBlockY();
									int z = ogloc.getBlockZ();
									Biome biome = ogworl.getBiome(ogloc.getBlockX(), ogloc.getBlockY(),
											ogloc.getBlockZ());
									if (configGetter.getConfig().getBoolean("DisplayCoords")) {
										p.sendMessage(ChatColor.valueOf(prefixColour) + prefix
												+ ChatColor.valueOf(messageColour) + "X: " + x + " " + "Y: " + y + " "
												+ "Z: " + z + " " + "Biome: " + biome);
									} else {

										p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
												+ "Oops, this should not have happened, oh dear.");
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
//Reload Main Config
//########################################################################################################################################################################################################

						if (args[0].equalsIgnoreCase("reload")) {
							if (configGetter.getConfig().getBoolean("CFGReloadEnabled")) {
								if (p.hasPermission("player.ConfigReload")) {
									configGetter.reloadConfig();
									p.sendMessage(ChatColor.valueOf(prefixColour) + prefix
											+ ChatColor.valueOf(messageColour) + "Main Config reloaded");
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
				p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
						+ "Teleport commands are currently disabled");
			}

		}
		return true;
	}

}
