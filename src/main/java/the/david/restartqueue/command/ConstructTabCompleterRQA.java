package the.david.restartqueue.command;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import the.david.restartqueue.Restartqueue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ConstructTabCompleterRQA implements TabCompleter {
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            list.add("player");
            list.add("tp");
        } else if (args.length == 3 && args[0].equalsIgnoreCase("tp")) {
            JSONArray json = Restartqueue.storadge.getArrayFromFile();
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
            if (offlinePlayer.hasPlayedBefore()) {
                UUID uuid = offlinePlayer.getUniqueId();
                for (Object o : json) {
                    JSONObject obj = (JSONObject) o;
                    if (uuid.toString().equalsIgnoreCase(obj.get("creator").toString())) {
                        list.add(obj.get("name").toString());
                    }
                }
            } else {
                return list;
            }
        }
        return list;
    }
}
