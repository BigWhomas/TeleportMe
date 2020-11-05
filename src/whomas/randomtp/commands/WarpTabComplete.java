package whomas.randomtp.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class WarpTabComplete implements TabCompleter {

	@Override
	public  List<String> onTabComplete( CommandSender sender,  Command command, String alias,  String[] args) {
		if(args.length == 1) {
			List<String> parameters = new ArrayList<>();
			String[] tabcomplete = {"set", "go", "list", "delete", "reload"};
			for (int i = 0; i < tabcomplete.length; i++) {
				parameters.add(tabcomplete[i]);
			}
		return parameters;
		}
		return null;
	}
		
}
