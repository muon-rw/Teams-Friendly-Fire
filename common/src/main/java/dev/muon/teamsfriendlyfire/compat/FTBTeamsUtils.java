package dev.muon.teamsfriendlyfire.compat;

import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.Team;
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

        Optional<Team> team1Opt = resolveTeam(playerId1, level);
        Optional<Team> team2Opt = resolveTeam(playerId2, level);
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

    private static Optional<Team> resolveTeam(UUID playerId, Level level) {
        if (level.isClientSide()) {
            if (!FTBTeamsAPI.api().isClientManagerLoaded()) return Optional.empty();
            var manager = FTBTeamsAPI.api().getClientManager();
            return manager.getKnownPlayer(playerId)
                    .flatMap(known -> manager.getTeamByID(known.teamId()));
        }
        if (!FTBTeamsAPI.api().isManagerLoaded()) return Optional.empty();
        return FTBTeamsAPI.api().getManager().getTeamForPlayerID(playerId);
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
