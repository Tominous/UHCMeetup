package com.isnakebuzz.meetup.e;

import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.b.States;
import com.isnakebuzz.meetup.f.Finish;
import com.isnakebuzz.meetup.f.Starting;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.server.v1_8_R3.BiomeBase;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.block.Beacon;
import org.bukkit.block.BlockState;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Furnace;
import org.bukkit.block.Hopper;
import org.bukkit.block.Jukebox;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class API {
    
    public static ArrayList<Player> ALivePs = new ArrayList<>();
    public static ArrayList<Player> Specs = new ArrayList<>();
    public static ArrayList<Player> NoBorder = new ArrayList<>();
    
    public static HashMap<Player, Integer> ReRoll = new HashMap<>();
    
    public static ArrayList<Player> KitMode = new ArrayList<>();
    
    public static ArrayList<Player> MLG = new ArrayList<>();
    public static boolean MLGe = false;
    
    public static HashMap<Player, Integer> Kills = new HashMap<>();
    
    public static ArrayList<Player> Voted = new ArrayList<>();
        
    public static Boolean started = false;
    
    public static ArrayList<Player> Teleported = new ArrayList<>();
    
    public static int need = Main.plugin.getConfig().getInt("MinPlayers");
    public static int votos = Main.plugin.getConfig().getInt("MinVotes");
    public static int nextborder = 60;
    
    
    public static void CheckStart(){
        if (need == 0){
            new Starting(Main.plugin).runTaskTimer(Main.plugin, 02L, 20L);
            Bukkit.broadcastMessage(c(Main.plugin.getConfig().getString("StartMSG")
                    .replaceAll("%time%", ""+Starting.time)
            ));
            started = true;
        }
    }
    
    public static int GetKills(Player p){
        if (API.Kills.get(p) == null){
            return 0;
        }else{
            return API.Kills.get(p);
        }
    }
    
    public static void CleanPlayer(Player p){
        p.setLevel(0);
        p.setExp(0);
        p.setHealth(p.getMaxHealth());
        p.setFoodLevel(40);
        p.setFlying(false);
        p.setAllowFlight(false);
        p.setFireTicks(0);
        p.getActivePotionEffects().stream().forEach((effect) -> {
            p.removePotionEffect(effect.getType());
        });
    }
    
    public static void JoinClean(Player p){
        p.setLevel(0);
        p.setExp(0);
        p.setHealth(p.getMaxHealth());
        p.setFoodLevel(40);
        p.setFireTicks(0);
        p.getInventory().setArmorContents(null);
        p.getActivePotionEffects().stream().forEach((effect) -> {
            p.removePotionEffect(effect.getType());
        });
    }
    
    public static void sendLobby(Player player) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(Main.plugin.getConfig().getString("Lobby"));

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        player.sendPluginMessage(Main.plugin, "BungeeCord", b.toByteArray());
    }
    
    public static void CheckWin(Player p){
        if (API.ALivePs.size() == 1){
            Bukkit.broadcastMessage(c(Main.plugin.getConfig().getString("WinMSG")
                    .replaceAll("%player%", p.getName())
            ));
            new Finish(Main.plugin).runTaskTimer(Main.plugin, 02L, 20L);
            //p.sendMessage(c(Main.plugin.getConfig().getString("MlgMSG")));
            States.state = States.FINISH;
        }
    }
    
    public static void Generating(){
        Bukkit.getConsoleSender().sendMessage("§aInjectando biomas...");
        Field biomesField = null;
        try{
          biomesField = BiomeBase.class.getDeclaredField("biomes");
          biomesField.setAccessible(true);
          if ((biomesField.get(null) instanceof BiomeBase[])){
              
            BiomeBase[] biomes = (BiomeBase[])biomesField.get(null);
            biomes[BiomeBase.DEEP_OCEAN.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.OCEAN.id] = BiomeBase.DESERT;
            biomes[BiomeBase.JUNGLE.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.ICE_PLAINS.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.JUNGLE_EDGE.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.JUNGLE_HILLS.id] = BiomeBase.DESERT;
            biomes[BiomeBase.COLD_TAIGA.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.ICE_MOUNTAINS.id] = BiomeBase.DESERT;
            biomes[BiomeBase.FROZEN_RIVER.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.EXTREME_HILLS.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.EXTREME_HILLS_PLUS.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.SWAMPLAND.id] = BiomeBase.DESERT;
            biomes[BiomeBase.TAIGA_HILLS.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.TAIGA.id] = BiomeBase.DESERT;
            biomes[BiomeBase.RIVER.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.SWAMPLAND.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.BEACH.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.ROOFED_FOREST.id] = BiomeBase.DESERT;
            biomes[BiomeBase.COLD_TAIGA_HILLS.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.COLD_TAIGA.id] = BiomeBase.DESERT;
            biomes[BiomeBase.BIRCH_FOREST.id] = BiomeBase.DESERT;
            biomes[BiomeBase.BIRCH_FOREST_HILLS.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.FOREST_HILLS.id] = BiomeBase.DESERT;
            biomes[BiomeBase.SAVANNA.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.SAVANNA_PLATEAU.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.FOREST.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.FOREST_HILLS.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.MEGA_TAIGA.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.MEGA_TAIGA_HILLS.id] = BiomeBase.PLAINS;            
            biomes[BiomeBase.MUSHROOM_ISLAND.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.MUSHROOM_SHORE.id] = BiomeBase.PLAINS;
            
            biomesField.set(null, biomes);
          }
        }
        catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException localException) {
        }
        Bukkit.getConsoleSender().sendMessage("§aBiomas injectados correctamente");
    }
    
    public static String c(String c){
        return ChatColor.translateAlternateColorCodes('&', c);
    }
    
    public static void DeleteWorld(String world) {
        File toReset = new File(world);
        World w2 = Bukkit.getWorld(world);
        Bukkit.getServer().unloadWorld(w2, true);
        Bukkit.getConsoleSender().sendMessage("§aReseteando mundo...");
        try {
            FileUtils.deleteDirectory(toReset);
            Bukkit.getConsoleSender().sendMessage("§aMundo borrado");
        } catch (IOException e) {
        }
        
        WorldCreator wc = new WorldCreator("world");
        wc.type(WorldType.LARGE_BIOMES);
        wc.createWorld();
        w2.regenerateChunk(0, 0);
        API.cleanMap();
    }
    
    public static void CheckStartVote(){
        if (votos == 0){
            new Starting(Main.plugin).runTaskTimer(Main.plugin, 02L, 20L);
            Bukkit.broadcastMessage(c(Main.plugin.getConfig().getString("StartMSG")
                    .replaceAll("%time%", ""+Starting.time)
            ));
            started = true;
        }else if (votos > 0){
            Bukkit.broadcastMessage(c(Main.plugin.getConfig().getString("VotesMSG")
                    .replaceAll("%votes%", ""+votos)
            ));
        }
    }
    
    public static void cleanMap() {
        World w = Bukkit.getWorld(Main.world);
        Chunk[] chunks = w.getLoadedChunks();
        for (Chunk chunk : chunks) {
            BlockState[] tileEntities = chunk.getTileEntities();
            for (BlockState i : tileEntities) {
                if (i instanceof Beacon) {
                    Beacon blockState = ((Beacon) i);
                    blockState.getInventory().clear();
                } else if (i instanceof BrewingStand) {
                    BrewingStand blockState = ((BrewingStand) i);
                    blockState.getInventory().clear();
                } else if (i instanceof Chest) {
                    Chest blockState = ((Chest) i);
                    blockState.getInventory().clear();
                } else if (i instanceof Dispenser) {
                    Dispenser blockState = ((Dispenser) i);
                    blockState.getInventory().clear();
                } else if (i instanceof Furnace) {
                    Furnace blockState = ((Furnace) i);
                    blockState.getInventory().clear();
                } else if (i instanceof Hopper) {
                    Hopper blockState = ((Hopper) i);
                 blockState.getInventory().clear();
                } else if (i instanceof Jukebox) {
                    Jukebox blockState = ((Jukebox) i);
                    blockState.eject();
                }
            }
        }

        for (Entity entity : w.getEntities()) {
            if (entity.getType() == EntityType.DROPPED_ITEM) {
                entity.remove();
            }
        }

        clearNonPlayerEntities();
    }

    private static void clearNonPlayerEntities() {
        World w = Bukkit.getWorld(Main.world);
        for (Entity entity : w.getEntities()) {
            if (entity instanceof LivingEntity && !(entity instanceof Player) && !entity.hasMetadata("CombatLoggerNPC")) {
                    entity.remove();
            }
        }
    }
    
    static int Online(){
        if (Bukkit.getOnlinePlayers().size() > 9){
            return 9 * 2;
        } else if (Bukkit.getOnlinePlayers().size() > 9 * 2){
            return 9 * 3;
        } else if (Bukkit.getOnlinePlayers().size() > 9 * 3){
            return 9 * 4;
        } else if (Bukkit.getOnlinePlayers().size() > 9 * 4){
            return 9 * 5;
        }else{
            return 9;
        }
    }
    
    private static List<String> benable = Main.plugin.getConfig().getStringList("Items.Border.Enable");
    private static List<String> bdisable = Main.plugin.getConfig().getStringList("Items.Border.Disable");
    
    public static void SendOptionMenu(Player p){
        Inventory menu = Bukkit.createInventory(null, Online(), c(Main.plugin.getConfig().getString("OptionsMenu")));
        if (!NoBorder.contains(p)){
            ItemStack border = new ItemStack(351, 1, (short)10);
            ItemMeta b = border.getItemMeta();
            b.setDisplayName(c(Main.plugin.getConfig().getString("Items.BorderI")));
            final List<String> lore = new ArrayList<>();
            benable.stream().map((s) -> c(s).replaceAll("&", "§")).forEach((ss) -> {
                lore.add(ss);
            });
            b.setLore(lore);
            border.setItemMeta(b);
            menu.setItem(0, border);
        }else{
            ItemStack border = new ItemStack(351, 1, (short)8);
            ItemMeta b = border.getItemMeta();
            b.setDisplayName(c(Main.plugin.getConfig().getString("Items.BorderI")));
            final List<String> lore = new ArrayList<>();
            bdisable.stream().map((s) -> c(s).replaceAll("&", "§")).forEach((ss) -> {
                lore.add(ss);
            });
            b.setLore(lore);
            border.setItemMeta(b);
            menu.setItem(0, border);
        }
        p.openInventory(menu);
    }
    
    private static final Inventory alive = Bukkit.createInventory(null, Online(), c(Main.plugin.getConfig().getString("MenuName")));
  
    public static Inventory getAlive(){
        API.alive.clear();
        for (Player p : ALivePs){
            Player localPlayer = Bukkit.getServer().getPlayer(p.getName());
            if (localPlayer != null) {
                API.alive.addItem(new ItemStack[] { API.newItem(Material.SKULL_ITEM, p, 3) });
            }
        }
        return API.alive;
    }
    
    
    public static void mlg1(final Player p, int locs) {
        final Random r = new Random();
        final int x = r.nextInt(locs);
        final int z = -r.nextInt(locs);
        final int y = p.getWorld().getHighestBlockYAt(x, z) + 30;
        final World world = Bukkit.getWorld(Main.world);
        final Location randomLoc = new Location(world, (double)x, (double)y, (double)z);
        p.teleport(randomLoc);
    }
    
    public static void mlg2(final Player p, int locs) {
        final Random r = new Random();
        final int x = r.nextInt(locs);
        final int z = -r.nextInt(locs);
        final int y = p.getWorld().getHighestBlockYAt(x, z) + 60;
        final World world = Bukkit.getWorld(Main.world);
        final Location randomLoc = new Location(world, (double)x, (double)y, (double)z);
        p.teleport(randomLoc);
    }
    
    public static void mlg3(final Player p, int locs) {
        final Random r = new Random();
        final int x = r.nextInt(locs);
        final int z = -r.nextInt(locs);
        final int y = p.getWorld().getHighestBlockYAt(x, z) + 90;
        final World world = Bukkit.getWorld(Main.world);
        final Location randomLoc = new Location(world, (double)x, (double)y, (double)z);
        p.teleport(randomLoc);
    }
    
    public static ItemStack newItem(Material paramMaterial, Player paramString, int paramInt){
        ItemStack localItemStack = new ItemStack(paramMaterial, 1, (short)paramInt);
        SkullMeta  meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
        meta.setOwner(paramString.getName());
        meta.setDisplayName("§a"+paramString.getName());
        localItemStack.setItemMeta(meta);

        return localItemStack;
    }
}
