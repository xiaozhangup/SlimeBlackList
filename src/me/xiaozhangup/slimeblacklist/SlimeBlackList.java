package me.xiaozhangup.slimeblacklist;

import io.github.thebusybiscuit.slimefun4.api.events.ExplosiveToolBreakBlocksEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class SlimeBlackList extends JavaPlugin implements Listener {

    public static List<Material> blackList = new ArrayList<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();
        org.bukkit.configuration.file.@org.jetbrains.annotations.NotNull FileConfiguration config = getConfig();
        try {
            config.getStringList("BlackList").forEach(s -> {
                blackList.add(Material.valueOf(s));
            });
        } catch (Exception e) {
            getLogger().warning("配置文件中有无法识别的物品!");
        }
        StringBuilder lists = new StringBuilder();
        blackList.forEach(material -> {
            lists.append(material.toString()).append(" ");
        });
        getLogger().info("加载完成,目前的黑名单物品有: " + lists);

        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void on(ExplosiveToolBreakBlocksEvent e) {
        e.getAdditionalBlocks().removeIf(block -> blackList.contains(block.getType()));
    }

}
