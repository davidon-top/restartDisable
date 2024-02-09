package the.david.restartqueue.command;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import the.david.restartqueue.Restartqueue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommandRQ implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1){
            Player player = (Player) sender;
            //rq list
            if (args[0].equalsIgnoreCase("list")) {
                JSONArray json = Restartqueue.storadge.getArrayFromFile();
                for (Object o : json) {
                    JSONObject obj = (JSONObject) o;
                    if (player.getUniqueId().toString().equalsIgnoreCase(obj.get("creator").toString())) {
                        player.sendMessage("Name: " + obj.get("name").toString());
                    }
                }
                return true;
            }
        }else if (args.length != 2) {
            return false;
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
            //rq add
            if (args[0].equalsIgnoreCase("add")) {
                Block block = player.getTargetBlock(null, 5);

                if (block.getType() == Material.LEVER) {
                    UUID uuid = player.getUniqueId();
                    Restartqueue.storadge.addLever(block, args[1], uuid.toString());
                } else {
                    return false;
                }
                return true;
                //rq remove
            } else if (args[0].equalsIgnoreCase("remove")) {
                JSONArray json = Restartqueue.storadge.getArrayFromFile();
                for (Object o : json) {
                    JSONObject obj = (JSONObject) o;
                    if (player.getUniqueId().toString().equalsIgnoreCase(obj.get("creator").toString())) {
                        if (args[1].equalsIgnoreCase(obj.get("name").toString())) {
                            json.remove(o);
                            Restartqueue.storadge.writeFile(json);
                            return true;
                        }
                    }
                }
                return false;
            } else if (args[0].equalsIgnoreCase("location")) {
                JSONArray json = Restartqueue.storadge.getArrayFromFile();
                for (Object o : json) {
                    JSONObject obj = (JSONObject) o;
                    if (player.getUniqueId().toString().equalsIgnoreCase(obj.get("creator").toString())) {
                        if (args[1].equalsIgnoreCase(obj.get("name").toString())) {
                            List<String> location = new ArrayList<>();
                            JSONArray loc = (JSONArray) obj.get("location");
                            for (int i = 0;i<3; i++) {
                                location.add(loc.get(i).toString());
                            }
                            player.sendMessage("XYZ: " + location.get(0) + " " + location.get(1) + " " + location.get(2));
                            return true;
                        }
                    }
                }
            }
        }
        // if incorect numbers of arguments or somethink else bad happenes let paper automaticly say corect usage to user
        return false;
    }
}
