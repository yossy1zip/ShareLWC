package com.yiorno.sharelwc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

public class Team {

    public static Map<UUID, ArrayList<UUID>> map = new HashMap<>();

    public boolean isMember(UUID owner, UUID member){
        if(map.containsKey(owner)){
            return map.get(owner).contains(member);
        }
        return false;
    }

    public void addMember(UUID owner, UUID member){

        Player player = Bukkit.getPlayer(owner);
        assert player != null;

        if(isMember(owner, member)){
            player.sendMessage(ChatColor.RED + "すでにそのプレイヤーと共有しています！");
            return;
        }

        if(map.containsKey(owner)){

            ArrayList<UUID> l = map.get(owner);
            l.add(member);
            map.put(owner, l);

        }else{

            ArrayList<UUID> l = new ArrayList<>();
            l.add(member);
            map.put(owner, l);

        }

        player.sendMessage("チェストなどを共有し始めました！");

    }


    public void showMember(UUID owner){

        Player player = Bukkit.getPlayer(owner);
        assert player != null;

        if(map.containsKey(owner)){

            ArrayList<UUID> l = map.get(owner);
            String listStr = null;
            StringJoiner sj = new StringJoiner(",");

            for(UUID u : l) {

                if(listStr!=null) {

                    Player p = Bukkit.getPlayer(u);
                    assert p != null;
                    sj.add(p.getName());

                }else{

                    Player p = Bukkit.getPlayer(u);
                    assert p != null;
                    listStr = p.getName();

                }

            }

            player.sendMessage(ChatColor.AQUA + "以下のプレイヤーと共有しています");
            player.sendMessage("" + listStr);

        }else{
            player.sendMessage(ChatColor.RED + "そのプレイヤーとはチェストを共有していません！");
        }
    }

    //public void showOwner(UUID owner, UUID member){
    //}

    public void removeMember(UUID owner, UUID member){

        Player player = Bukkit.getPlayer(owner);
        assert player != null;

        if(!(isMember(owner, member))){
            player.sendMessage(ChatColor.RED + "そのプレイヤーとはチェストを共有していません！");
            return;
        }

        if(map.containsKey(owner)){

            ArrayList<UUID> l = map.get(owner);
            l.add(member);
            map.put(owner, l);
            l = null;

        }else{

            ArrayList<UUID> l = map.get(owner);
            l.add(member);
            map.put(owner, l);
            l = null;

        }
        player.sendMessage("チェストなどの共有をやめました");

    }
}
