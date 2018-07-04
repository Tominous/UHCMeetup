package com.isnakebuzz.meetup.j;

import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.d.GamePlayer;
import com.isnakebuzz.meetup.i.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Set;

public class MenuCreator extends Menu {

    private Main plugin;

    public MenuCreator(Player player, Main plugin, String _name) {
        super(plugin, _name);
        this.plugin = plugin;
        Configuration config = plugin.getConfigUtils().getConfig(plugin, "Utils/MenuCreator");
        String path = "MenuCreator." + _name + ".";
        if (config.getBoolean(path + "update.enabled")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!player.getOpenInventory().getTitle().equalsIgnoreCase(c(config.getString(path + "title")))) {
                        this.cancel();
                    }
                    updateInv(player, _name);
                }
            }.runTaskTimerAsynchronously(plugin, 1, config.getInt(path + "update.time"));
        } else {
            this.updateInv(player, _name);
        }
    }

    @Override
    public void onClick(final Player p, final ItemStack itemStack, String _name) {
        Configuration config = plugin.getConfigUtils().getConfig(plugin, "Utils/MenuCreator");
        String menu_path = "MenuCreator." + _name + ".";
        Set<String> key = config.getConfigurationSection(menu_path + "items").getKeys(false);
        for (String _item : key) {
            String path = menu_path + "items." + _item + ".";
            String item = config.getString(path + "item");
            int amount = config.getInt(path + "amount");
            String name = config.getString(path + "name");
            List<String> lore = config.getStringList(path + "lore");
            String action = config.getString(path + "action");
            ItemStack itemStack1 = ItemBuilder.crearItem1(Integer.valueOf(item.split(":")[0]), amount, Integer.valueOf(item.split(":")[1]), name, lore);
            if (itemStack.equals(itemStack1)) {
                if (action.split(":")[0].equalsIgnoreCase("menu")) {
                    new MenuCreator(p, plugin, action.split(":")[1]).o(p);
                } else if (action.split(":")[0].equalsIgnoreCase("cmd")) {
                    String cmd = "/" + action.split(":")[1];
                    p.chat(cmd);
                } else if (action.split(":")[0].equalsIgnoreCase("server")) {
                    GamePlayer gamePlayer = plugin.getPlayerManager().getUuidGamePlayerMap().get(p.getUniqueId());
                    gamePlayer.connect(action.split(":")[1]);
                } else if (action.split(":")[0].equalsIgnoreCase("lobby")) {
                    GamePlayer gamePlayer = plugin.getPlayerManager().getUuidGamePlayerMap().get(p.getUniqueId());
                    gamePlayer.sendToLobby();
                }
            }
        }

    }

    private void updateInv(Player p, String _name) {
        Configuration config = plugin.getConfigUtils().getConfig(plugin, "Utils/MenuCreator");
        String menu_path = "MenuCreator." + _name + ".";
        Set<String> key = config.getConfigurationSection(menu_path + "items").getKeys(false);
        for (String _item : key) {
            String path = menu_path + "items." + _item + ".";
            String item = config.getString(path + "item");
            int slot = config.getInt(path + "slot");
            int amount = config.getInt(path + "amount");
            String name = config.getString(path + "name");
            List<String> lore = config.getStringList(path + "lore");
            String action = config.getString(path + "action");
            ItemStack itemStack = ItemBuilder.crearItem1(Integer.valueOf(item.split(":")[0]), amount, Integer.valueOf(item.split(":")[1]), name, lore);
            this.s(slot, itemStack);
        }
    }

    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }

}
