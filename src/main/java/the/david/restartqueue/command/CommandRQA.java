package the.david.restartqueue.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
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

public class CommandRQA implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command Command, String label, String[] args) {
        Player player = (Player) sender;
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("player")) {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                if (offlinePlayer.hasPlayedBefore()) {
                    UUID uuid = offlinePlayer.getUniqueId();
                    JSONArray json = Restartqueue.storadge.getArrayFromFile();
                    for (Object o : json) {
                        JSONObject obj = (JSONObject) o;
                        if (uuid.toString().equalsIgnoreCase(obj.get("creator").toString())) {
                            List<String> location = new ArrayList<>();
                            JSONArray loc = (JSONArray) obj.get("location");
                            for (int i = 0;i<3; i++) {
                                location.add(loc.get(i).toString());
                            }
                            player.sendMessage(obj.get("name").toString() + ", XYZ: " + location.get(0) + " " + location.get(1) + " " + location.get(2));
                        }
                    }
                    return true;
                } else {
                    player.sendMessage("That player has not played before.");
                    return true;
                }
            }
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("tp")) {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                if (offlinePlayer.hasPlayedBefore()) {
                    UUID uuid = offlinePlayer.getUniqueId();
                    JSONArray json = Restartqueue.storadge.getArrayFromFile();
                    for (Object o : json) {
                        JSONObject obj = (JSONObject) o;
                        if (uuid.toString().equalsIgnoreCase(obj.get("creator").toString())) {
                            if (args[2].equalsIgnoreCase(obj.get("name").toString())) {
                                List<String> location = new ArrayList<>();
                                JSONArray loc = (JSONArray) obj.get("location");
                                for (int i = 0;i<3; i++) {
                                    location.add(loc.get(i).toString());
                                }
                                World world = Bukkit.getWorld(obj.get("world_name").toString());
                                Location loca = new Location(world, Integer.parseInt(location.get(0)) + 0.5, Integer.parseInt(location.get(1)), Integer.parseInt(location.get(2)) + 0.5, 0, 90);
                                player.teleport(loca);
                                return true;
                            }
                        }
                    }
                } else {
                    player.sendMessage("That player has not played before.");
                    return true;
                }
            }
        }
        return false;
    }
}
