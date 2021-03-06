package com.isnakebuzz.meetup.Utils.Managers;

import com.isnakebuzz.meetup.Main;
import com.isnakebuzz.meetup.Player.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class VoteManager {

    private Main plugin;

    private List<GamePlayer> bowless;
    private List<GamePlayer> default_;
    private List<GamePlayer> fireless;
    private List<GamePlayer> noclean;

    public VoteManager(Main plugin) {
        this.plugin = plugin;
        this.noclean = new ArrayList<>();
        this.bowless = new ArrayList<>();
        this.default_ = new ArrayList<>();
        this.fireless = new ArrayList<>();
    }

    public List<GamePlayer> getFireless() {
        return fireless;
    }

    public List<GamePlayer> getBowless() {
        return bowless;
    }

    public List<GamePlayer> getDefault() {
        return default_;
    }

    public List<GamePlayer> getNoClean() {
        return noclean;
    }

    public void checkVoteWin() {
        Configuration lang = plugin.getConfigUtils().getConfig(plugin, "Lang");

        if (bowless.size() > default_.size() && bowless.size() > fireless.size() && bowless.size() > noclean.size()) {
            this.bowless();
            plugin.broadcast(lang.getString("VoteAnnounce")
                    .replaceAll("%votes%", String.valueOf(this.getBowless().size()))
                    .replaceAll("%type%", "Bowless")
            );
        }
        if (default_.size() > bowless.size() && default_.size() > fireless.size() && default_.size() > noclean.size()) {
            plugin.broadcast(lang.getString("VoteAnnounce")
                    .replaceAll("%votes%", String.valueOf(this.getDefault().size()))
                    .replaceAll("%type%", "Default")
            );
        }
        if (fireless.size() > default_.size() && fireless.size() > bowless.size() && fireless.size() > noclean.size()) {
            plugin.getEventManager().registerListener(plugin.getVoteEventManager().getEventFireless());
            plugin.broadcast(lang.getString("VoteAnnounce")
                    .replaceAll("%votes%", String.valueOf(this.getFireless().size()))
                    .replaceAll("%type%", "Fireless")
            );
        }
        if (noclean.size() > default_.size() && noclean.size() > fireless.size() && noclean.size() > bowless.size()) {
            plugin.getEventManager().registerListener(plugin.getVoteEventManager().getEventNoClean());
            plugin.broadcast(lang.getString("VoteAnnounce")
                    .replaceAll("%votes%", String.valueOf(this.getNoClean().size()))
                    .replaceAll("%type%", "NoClean")
            );
        }
    }

    private void bowless() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.getInventory().remove(Material.BOW);
            all.updateInventory();
        }
    }

    public boolean setVote(GamePlayer gamePlayer, VoteType voteType) {
        // Removing vote, if gameplayer exist.
        if (this.bowless.contains(gamePlayer)) this.bowless.remove(gamePlayer);
        if (this.default_.contains(gamePlayer)) this.default_.remove(gamePlayer);
        if (this.fireless.contains(gamePlayer)) this.fireless.remove(gamePlayer);
        if (this.noclean.contains(gamePlayer)) this.noclean.remove(gamePlayer);

        if (voteType.equals(VoteType.BOWLESS)) {
            this.bowless.add(gamePlayer);
            return true;
        } else if (voteType.equals(VoteType.DEFAULT)) {
            this.default_.add(gamePlayer);
            return true;
        } else if (voteType.equals(VoteType.FIRELESS)) {
            this.fireless.add(gamePlayer);
            return true;
        } else if (voteType.equals(VoteType.NOCLEAN)) {
            this.noclean.add(gamePlayer);
            return true;
        }
        return false;
    }

    public enum VoteType {
        BOWLESS, DEFAULT, FIRELESS, NOCLEAN
    }
}
