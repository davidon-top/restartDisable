package the.david.restartqueue;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Powerable;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import the.david.restartqueue.command.*;
import the.david.restartqueue.db.LeverStoradge;

import java.util.ArrayList;
import java.util.List;

public final class Restartqueue extends JavaPlugin {
    //init storadge and make it accesible in other classes
    public static LeverStoradge storadge = new LeverStoradge();
    @Override
    public void onEnable() {
        //init commands and tab completors
        this.getCommand("rq").setExecutor(new CommandRQ());
        this.getCommand("rq").setTabCompleter(new ConstructTabCompleter());
        this.getCommand("rqoff").setExecutor(new CommandRQOff());
        this.getCommand("rqon").setExecutor(new CommandRQOn());
        this.getCommand("rqhelp").setExecutor(new CommandHelp());
        this.getCommand("rqa").setExecutor(new CommandRQA());
        this.getCommand("rqa").setTabCompleter(new ConstructTabCompleterRQA());
    }

    @Override
    public void onDisable() {
        //TODO decide if we want to add shutdown hook that stops farms before restart
    }

    public static void leversOff() {
        JSONArray json = storadge.getArrayFromFile();
        List<Object> toRemove = new ArrayList<>();
        List<JSONObject> toAdd = new ArrayList<>();
        for (Object o : json) {
            JSONObject obj = (JSONObject) o;
            World world = Bukkit.getWorld(obj.get("world_name").toString());
            JSONArray loc = (JSONArray) obj.get("location");
            List<String> location = new ArrayList<>();
            for (int i = 0;i<3; i++) {
                location.add(loc.get(i).toString());
            }
            Block block = world.getBlockAt(Integer.parseInt(location.get(0)), Integer.parseInt(location.get(1)), Integer.parseInt(location.get(2)));
            if (block.getType() != Material.LEVER) {
                toRemove.add(o);
                continue;
            }
            BlockData data = block.getBlockData();
            if (data instanceof Powerable) {
                Powerable powerable = (Powerable) data;
                if (powerable.isPowered()) {
                    block.getChunk().load();
                    ((Powerable) data).setPowered(false);
                    block.setBlockData(data);
                    obj.remove("todo");
                    obj.put("todo", 1);
                    toRemove.add(o);
                    toAdd.add(obj);
                }
            }
        }
        json.removeAll(toRemove);
        json.addAll(toAdd);
        storadge.writeFile(json);
    }

    public static void leversOff(boolean shouldIgnorerqon) {
        JSONArray json = storadge.getArrayFromFile();
        List<Object> toRemove = new ArrayList<>();
        List<JSONObject> toAdd = new ArrayList<>();
        for (Object o : json) {
            JSONObject obj = (JSONObject) o;
            World world = Bukkit.getWorld(obj.get("world_name").toString());
            JSONArray loc = (JSONArray) obj.get("location");
            List<String> location = new ArrayList<>();
            for (int i = 0;i<3; i++) {
                location.add(loc.get(i).toString());
            }
            Block block = world.getBlockAt(Integer.parseInt(location.get(0)), Integer.parseInt(location.get(1)), Integer.parseInt(location.get(2)));
            if (block.getType() != Material.LEVER) {
                toRemove.add(o);
                continue;
            }
            BlockData data = block.getBlockData();
            if (data instanceof Powerable) {
                Powerable powerable = (Powerable) data;
                if (powerable.isPowered()) {
                    block.getChunk().load();
                    ((Powerable) data).setPowered(false);
                    block.setBlockData(data);
                    if (!shouldIgnorerqon) {
                        obj.remove("todo");
                        obj.put("todo", 1);
                        toRemove.add(o);
                        toAdd.add(obj);
                    }
                }
            }
        }
        json.removeAll(toRemove);
        json.addAll(toAdd);
        storadge.writeFile(json);
    }

    public static void leversOn() {
        JSONArray json = storadge.getArrayFromFile();
        List<Object> toRemove = new ArrayList<>();
        List<JSONObject> toAdd = new ArrayList<>();
        for (Object o : json) {
            JSONObject obj = (JSONObject) o;
            World world = Bukkit.getWorld(obj.get("world_name").toString());
            JSONArray loc = (JSONArray) obj.get("location");
            List<String> location = new ArrayList<>();
            for (int i = 0;i<3; i++) {
                location.add(loc.get(i).toString());
            }
            Block block = world.getBlockAt(Integer.parseInt(location.get(0)), Integer.parseInt(location.get(1)), Integer.parseInt(location.get(2)));
            if (block.getType() != Material.LEVER) {
                toRemove.add(o);
            }
            BlockData data = block.getBlockData();
            if (data instanceof Powerable) {
                Powerable powerable = (Powerable) data;
                if (!powerable.isPowered()) {
                    if (obj.get("todo").toString().equalsIgnoreCase("1")) {
                        block.getChunk().load();
                        ((Powerable) data).setPowered(true);
                        block.setBlockData(data);
                        obj.remove("todo");
                        obj.put("todo", 0);
                        toRemove.add(o);
                        toAdd.add(obj);
                    }
                }
            }
        }
        json.removeAll(toRemove);
        json.addAll(toAdd);
        storadge.writeFile(json);
    }
}
