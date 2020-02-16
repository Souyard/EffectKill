package fr.bukkit.effectkill.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.bukkit.effectkill.Main;
import fr.bukkit.effectkill.utils.Messages;
import fr.bukkit.effectkill.utils.User;
import fr.bukkit.effectkill.utils.Utils;

public class Commands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if(arg0 instanceof Player) {
			Player player = (Player) arg0;
			if(arg3.length == 0) {
				Main.getManager().buildInventory(User.getUser(player.getUniqueId())).open(new Player[] { player });
			}
			if(arg3.length == 1) {
				if(arg3[0].equalsIgnoreCase("help")) {
					Messages.sendhelp(player);
				}
                if (arg3[0].equalsIgnoreCase("remove")) {
                    User user = User.getUser(player.getUniqueId());
                    if (user.getEffectKill() == null) {
                        return false;
                    }
                    user.getEffectKill().despawn(user);
                    user.setEffectKill(null);
                    player.getPlayer().sendMessage(Utils.colorize(((String)Utils.gfc("messages", "remove")).replace("%player%", user.getPlayer().getName()).replace("%prefix%", Main.prefix)));
                }
			}
		}
		return false;
	}
}
