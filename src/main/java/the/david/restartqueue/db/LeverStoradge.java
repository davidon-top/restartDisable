package the.david.restartqueue.db;

import org.bukkit.block.Block;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LeverStoradge {
    private File file;
    private JSONArray json;
    private JSONParser parser = new JSONParser();

    public LeverStoradge() {
        //check if dir exists if not create one
        if (!Files.exists(Paths.get("plugins/restartqueue"))) {
            new File("plugins/restartqueue").mkdirs();
        }
        //check if levers file exists if not create it
        file = new File("plugins/restartqueue/levers.json");
        if (!Files.exists(Paths.get("plugins/restartqueue/levers.json"))) {
            try {
                file.createNewFile();

                //write init stuff to file so that parser can read it as JSONArray, only happenes if file was created now
                FileWriter fw = new FileWriter("plugins/restartqueue/levers.json");
                fw.write("[]");
                fw.close();
            } catch (IOException e) {
                System.out.println("Critical error occurred when trying to create a file to store lever data in.");
                e.printStackTrace();
            }
        }
        //read and parse json file (not realy nececery
        try {
            json = (JSONArray) parser.parse(new FileReader("plugins/restartqueue/levers.json"));
        } catch (IOException | ParseException e) {
            System.out.println("Critical errors occured when trying to read levers.json");
            e.printStackTrace();
        }
    }

    public void addLever(Block lever, String name, String uuid) {
        //create JSONObject that will be saved into levers.json
        JSONObject obj = new JSONObject();
        obj.put("name", name);
        obj.put("todo", 0);
        obj.put("creator", uuid);
        obj.put("world_name", lever.getWorld().getName());
        List<String> location = new ArrayList<>();
        location.add(String.valueOf(lever.getX()));
        location.add(String.valueOf(lever.getY()));
        location.add(String.valueOf(lever.getZ()));
        obj.put("location", location);

        //read the file so that its updated
        try {
            json = (JSONArray) parser.parse(new FileReader("plugins/restartqueue/levers.json"));
        } catch (IOException | ParseException e) {
            System.out.println("Critical errors occured when trying to read levers.json");
            e.printStackTrace();
        }
        //add JSONObject created in this function
        json.add(obj);
        //write and !!close!! this file so that if the server crashes the new lever is saved
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(json.toJSONString());
            fw.close();
        } catch (IOException e) {
            System.out.println("Critical errors occured when trying to write to levers.json");
            e.printStackTrace();
        }
    }
    public JSONArray getArrayFromFile() {
        //read and return whole json so that it can be edited and saved later
        try {
            json = (JSONArray) parser.parse(new FileReader("plugins/restartqueue/levers.json"));
        } catch (IOException | ParseException e) {
            System.out.println("Critical errors occured when trying to read levers.json");
            e.printStackTrace();
        }
        return json;
    }

    public void writeFile(JSONArray json) {
        //save edited JSONArray
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(json.toJSONString());
            fw.close();
        } catch (IOException e) {
            System.out.println("Critical errors occured when trying to write to levers.json");
            e.printStackTrace();
        }
    }
}
