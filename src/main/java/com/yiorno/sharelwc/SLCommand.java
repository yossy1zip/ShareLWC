package com.yiorno.sharelwc;

import org.apache.commons.io.IOUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

public class SLCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String commandLabel, String[] args){
        if(cmd.getName().equalsIgnoreCase("cshare")){

            if(args.length==0){
                sender.sendMessage(ChatColor.RED + "プレイヤーが不適切です！");
                return true;
            }

            if(!(sender instanceof Player)){
                return true;
            }

            Team team = new Team();
            Player player = (Player)sender;

            switch (args[0]){

                case "add":

                    if(getUUID(args[1])==null){
                        sender.sendMessage(ChatColor.RED + "プレイヤーが不適切です！");
                        return true;
                    }
                    team.addMember(player.getUniqueId(), getUUID(args[1]));
                    break;

                case "remove":
                    if(getUUID(args[1])==null){
                        sender.sendMessage(ChatColor.RED + "プレイヤーが不適切です！");
                        return true;
                    }
                    team.removeMember(player.getUniqueId(), getUUID(args[1]));
                    break;

                case "list":
                    team.showMember(player.getUniqueId());
                    break;
            }
        }
        return false;
    }

    //Quoted from Bukkit forum.
    public UUID getUUID(String name) {
        String url = "https://api.mojang.com/users/profiles/minecraft/"+name;
        try {
            @SuppressWarnings("deprecation")

            String UUIDJson = IOUtils.toString(new URL(url));
            if(UUIDJson.isEmpty()) return null;
            JSONObject UUIDObject = (JSONObject) JSONValue.parseWithException(UUIDJson);
            String uuidStr = UUIDObject.get("id").toString();
            return UUID.fromString(uuidStr);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
