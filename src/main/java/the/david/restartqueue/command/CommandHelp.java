package the.david.restartqueue.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHelp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //final (sortof)
        Player player = (Player) sender;
        player.sendMessage("This plugin allowes you to make levers be turned off before a restart, and back on after a restart, so that your farm doesnt break.");
        player.sendMessage("To use it look at a lever and run /rq add, this will save the lever. To remove it look at the block again and run /rq remove");
        player.sendMessage("If the lever is removed from the world it will automaticly remove it on the next restart of the server");
        return true;
    }
}
