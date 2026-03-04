package dev.muon.teamsfriendlyfire.compat;

import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.Team;
import dev.ftb.mods.ftbteams.api.TeamManager;
import dev.muon.teamsfriendlyfire.property.TeamPropertiesTFF;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.UUID;

/**
 * Utilities for checking FTB Teams alliance with our custom PvP flags.
 * Two entities are "protected allies" if neither team allows PvP between them.
 */
public final class FTBTeamsUtils {

    /**
     * Check if two players should be treated as allied (no PvP) based on FTB Teams
     * and our pvp_between_members / pvp_between_allies flags.
     *
     * @return true if they are protected (cannot hurt each other)
     */
    public static boolean arePlayersProtectedAllies(UUID playerId1, UUID playerId2, Level level) {
        if (playerId1 == null || playerId2 == null) return false;
        if (playerId1.equals(playerId2)) return true;

        if (level.isClientSide()) {
            return checkClientRelation(playerId1, playerId2);
        }
        return checkServerRelation(playerId1, playerId2);
    }

    private static boolean checkClientRelation(UUID playerId1, UUID playerId2) {
        if (!FTBTeamsAPI.api().isClientManagerLoaded()) return false;
        var manager = FTBTeamsAPI.api().getClientManager();
        var p1 = manager.getKnownPlayer(playerId1);
        var p2 = manager.getKnownPlayer(playerId2);
        if (p1.isEmpty() || p2.isEmpty()) return false;
        return p1.get().teamId().equals(p2.get().teamId());
    }

    private static boolean checkServerRelation(UUID playerId1, UUID playerId2) {
        if (!FTBTeamsAPI.api().isManagerLoaded()) return false;
        TeamManager manager = FTBTeamsAPI.api().getManager();
        Optional<Team> team1Opt = manager.getTeamForPlayerID(playerId1);
        Optional<Team> team2Opt = manager.getTeamForPlayerID(playerId2);
        if (team1Opt.isEmpty() || team2Opt.isEmpty()) return false;

        Team team1 = team1Opt.get();
        Team team2 = team2Opt.get();

        if (team1.getId().equals(team2.getId())) {
            return !team1.getProperty(TeamPropertiesTFF.PVP_BETWEEN_MEMBERS);
        }
        if (team1.getRankForPlayer(playerId2).isAllyOrBetter() || team2.getRankForPlayer(playerId1).isAllyOrBetter()) {
            return !team1.getProperty(TeamPropertiesTFF.PVP_BETWEEN_ALLIES)
                    && !team2.getProperty(TeamPropertiesTFF.PVP_BETWEEN_ALLIES);
        }
        return false;
    }

    /**
     * Resolve the effective player owner of an entity.
     * Walks ownership chain (OwnableEntity, etc.) until a Player is found.
     */
    public static Player getEffectivePlayerOwner(Entity entity) {
        int maxDepth = 10;
        Entity current = entity;

        for (int i = 0; i < maxDepth && current != null; i++) {
            if (current instanceof Player player) return player;
            Entity owner = null;
            if (current instanceof OwnableEntity ownable) {
                owner = ownable.getOwner();
            }
            if (owner == null) return null;
            current = owner;
        }
        return null;
    }
}
