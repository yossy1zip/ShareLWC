package com.yiorno.sharelwc;

import com.griefcraft.lwc.LWC;
import com.griefcraft.lwc.LWCPlugin;
import com.griefcraft.model.Protection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.io.IOUtils;
import org.json.simple.parser.ParseException;

public final class ShareLWC extends JavaPlugin implements Listener {

    private FileConfiguration config = null;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();
        if (this.config != null) { // configが非null == リロードで呼び出された
            this.reloadConfig();
        }
        config = this.getConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("cshare").setExecutor(new SLCommand());
        Map<String, Object> loadMap = this.getConfig().getConfigurationSection("saves").getValues(false);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        //getConfig().set("saves", Team.map);
        config.createSection("saves", Team.map);
        saveConfig();
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e){

        if(!(e.getAction().equals(Action.LEFT_CLICK_BLOCK))){
            return;
        }

        Plugin lwcp = Bukkit.getPluginManager().getPlugin("LWC");
        assert lwcp != null;
        LWC lwc = ((LWCPlugin)lwcp).getLWC();

        Protection protection = lwc.findProtection(e.getClickedBlock());

        if(lwc.canAccessProtection(e.getPlayer(), protection)){
            return;
        }

        UUID owner = protection.getBukkitOwner().getUniqueId();

        Team team = new Team();
        if(team.isMember(owner, e.getPlayer().getUniqueId())) {
            lwc.enforceAccess(protection.getBukkitOwner(), protection, e.getClickedBlock(), true);
        }
    }

}
