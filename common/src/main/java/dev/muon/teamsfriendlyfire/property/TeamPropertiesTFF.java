package dev.muon.teamsfriendlyfire.property;

import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.property.BooleanProperty;
import dev.ftb.mods.ftbteams.api.property.TeamProperty;
import net.minecraft.resources.ResourceLocation;

/**
 * Team properties added by Teams Friendly Fire.
 * - pvp_between_members: when false, team members are treated as allied (no PvP)
 * - pvp_between_allies: when false, allied teams are treated as allied (no PvP)
 */
public final class TeamPropertiesTFF {
    private static final String NAMESPACE = "teamsfriendlyfire";

    /**
     * Allow PvP between members of the same team.
     * Default false = members cannot hurt each other.
     */
    public static final TeamProperty<Boolean> PVP_BETWEEN_MEMBERS =
            new BooleanProperty(rl("pvp_between_members"), false);

    /**
     * Allow PvP between allied teams.
     * Default false = allies cannot hurt each other.
     */
    public static final TeamProperty<Boolean> PVP_BETWEEN_ALLIES =
            new BooleanProperty(rl("pvp_between_allies"), false);

    private static ResourceLocation rl(String path) {
        return new ResourceLocation(NAMESPACE, path);
    }

    private TeamPropertiesTFF() {}
}
