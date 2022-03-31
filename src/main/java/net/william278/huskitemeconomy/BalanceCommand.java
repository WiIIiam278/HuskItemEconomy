package net.william278.huskitemeconomy;

import de.themoep.minedown.MineDown;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand implements CommandExecutor {

    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            sender.spigot().sendMessage(new MineDown(HuskItemEconomy.getSettings().getBalanceMessage()
                    .replace("%1%", new EconomyProvider().format(DataManager.getPlayerBalance((Player) sender)))).toComponent());
            return true;
        }
        return false;
    }
}
