package whomas.randomtp.commands;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import whomas.randomtp.configs.CustomConfig;
import whomas.randomtp.plugin.TeleportMe;

public class Home implements CommandExecutor {
	TeleportMe configGetter;

	public Home(TeleportMe plugin) {
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
			String uuid = (String) p.getUniqueId().toString();
			boolean huuid = CustomConfig.get().contains(uuid);
			Integer housenum = configGetter.getConfig().getInt("HouseAmount");
			if (configGetter.getConfig().getBoolean("HomesEnabled")) {
				if (label.equalsIgnoreCase("home")) {
					if (args.length == 0) {
						p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED + "Invalid Arguments");
						p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.WHITE
								+ "Please specifiy an action");
						p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED + "Available Actions: "
								+ ChatColor.YELLOW + "Set, List, Go, Delete, Reload");
					}

//########################################################################################################################################################################################################
//Set Homes
//########################################################################################################################################################################################################			

					if (args.length > 0) {
						if (args[0].equalsIgnoreCase("set")) {
							if (configGetter.getConfig().getBoolean("SetHomeEnabled")) {
								if (p.hasPermission("player.SetHome")) {
									if (args.length == 2) {
										if (huuid == true) {
											Set<String> playerHouses = CustomConfig.get().getConfigurationSection(uuid)
													.getKeys(false);
											if (playerHouses == null) {
												p.sendMessage("It came back null");
											}
											if (playerHouses.size() < housenum) {
												String homename = args[1].toString().toLowerCase();
												CustomConfig.get().set(p.getUniqueId() + "." + homename,
														p.getLocation());
												CustomConfig.save();
												if (configGetter.getConfig().getBoolean("DisplayHomeSet")) {
													p.sendMessage(ChatColor.valueOf(prefixColour) + prefix
															+ ChatColor.valueOf(messageColour)
															+ "A Home Has Been Created Called: " + homename);
												}
											} else {
												p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
														+ "You already have the max number of homes");
											}
										}
										if (huuid == false) {
											String homename = args[1].toString().toLowerCase();
											CustomConfig.get().set(p.getUniqueId() + "." + homename, p.getLocation());
											CustomConfig.save();
											if (configGetter.getConfig().getBoolean("DisplayHomeSet")) {
												p.sendMessage(ChatColor.valueOf(prefixColour) + prefix
														+ ChatColor.valueOf(messageColour)
														+ "A Home Has Been Created Called: " + homename);
											}
										}

									} else {
										p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
												+ "Please enter a valid name for your house");

									}

								} else {
									p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
											+ "You don't currently have permission to use this");
								}
							} else {
								p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
										+ "This command is currently disabled");
							}

						}

//########################################################################################################################################################################################################
//List Homes
//########################################################################################################################################################################################################

						if (args[0].equalsIgnoreCase("list")) {
							if (CustomConfig.get().contains(uuid)) {
								Set<String> playerHouses = CustomConfig.get().getConfigurationSection(uuid)
										.getKeys(false);
								if (playerHouses == null) {
									p.sendMessage("You Have No Current Homes");
									return true;
								}
								if (p.hasPermission("player.ListHomes")) {

									p.sendMessage(ChatColor.valueOf(prefixColour) + prefix
											+ ChatColor.valueOf(messageColour) + "Homes Found: " + playerHouses);

								} else {
									p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
											+ "You don't have permission to use this");
								}
							} else {
								p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
										+ "You Have No Current Homes");
							}
						}

//########################################################################################################################################################################################################
//Go Home
//########################################################################################################################################################################################################

						if (args[0].equalsIgnoreCase("go")) {
							if (configGetter.getConfig().getBoolean("GoHomeEnabled")) {
								if (p.hasPermission("player.GoHome")) {
									if (args.length == 2) {
										String homename = args[1].toString().toLowerCase();
										Location location = (Location) CustomConfig.get()
												.get(p.getUniqueId() + "." + homename);
										if (location == null) {
											if (configGetter.getConfig().getBoolean("NoHome")) {
												p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
														+ " You currently have no home location set under that name");
											}

											return true;
										}

										p.teleport(location);
										if (configGetter.getConfig().getBoolean("GoHome")) {
											p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.valueOf(messageColour) + "Successfully sent home");
										}

									} else {
										p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
												+ "Please enter the name of the home");
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
//Delete Home
//########################################################################################################################################################################################################

						if (args[0].equalsIgnoreCase("delete")) {
							if (configGetter.getConfig().getBoolean("DelHomeEnabled")) {
								if (p.hasPermission("player.DelHome")) {
									if (args.length == 2) {
										String homename = args[1].toString().toLowerCase();
										Location location = (Location) CustomConfig.get()
												.get(p.getUniqueId() + "." + homename);
										if (location == null) {
											if (configGetter.getConfig().getBoolean("NoHome")) {
												p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
														+ " No home of that name found");
											}

											return true;
										}

										CustomConfig.get().set(uuid + "." + homename, null);
										CustomConfig.save();
										if (configGetter.getConfig().getBoolean("DelHome")) {
											p.sendMessage(ChatColor.valueOf(prefixColour) + prefix
													+ ChatColor.valueOf(messageColour) + "Home successfully removed");
										}

									} else {
										p.sendMessage(ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED
												+ "Please enter the name of the home");
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
//Reload Homes Config
//########################################################################################################################################################################################################

						if (args[0].equalsIgnoreCase("reload")) {
							if (configGetter.getConfig().getBoolean("CFGReloadEnabled")) {
								if (p.hasPermission("player.ConfigReload")) {
									CustomConfig.reload();
									p.sendMessage(ChatColor.valueOf(prefixColour) + prefix
											+ ChatColor.valueOf(messageColour) + "Home Config reloaded");
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
						ChatColor.valueOf(prefixColour) + prefix + ChatColor.RED + "Homes are currently disabled");
			}

		}

		return true;
	}
}