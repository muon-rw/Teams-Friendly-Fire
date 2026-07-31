package dev.muon.teamsfriendlyfire.property;

import dev.ftb.mods.ftbteams.api.property.TeamProperty;
import net.minecraft.resources.Identifier;

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
            new OfficerEditableBooleanProperty(rl("pvp_between_members"), false);

    /**
     * Allow PvP between allied teams.
     * Default false = allies cannot hurt each other.
     */
    public static final TeamProperty<Boolean> PVP_BETWEEN_ALLIES =
            new OfficerEditableBooleanProperty(rl("pvp_between_allies"), false);

    // Forces class init at mod init, on both sides: the custom property type must
    // be registered before the first team sync packet is decoded on the client
    public static void bootstrap() {}

    private static Identifier rl(String path) {
        return Identifier.fromNamespaceAndPath(NAMESPACE, path);
    }

    private TeamPropertiesTFF() {}
}
