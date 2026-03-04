package dev.muon.teamsfriendlyfire;

import dev.muon.teamsfriendlyfire.property.TeamPropertiesTFF;
import dev.ftb.mods.ftbteams.api.event.TeamCollectPropertiesEvent;
import dev.ftb.mods.ftbteams.api.event.TeamEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Teams Friendly Fire - integrates FTB Teams with vanilla allied/PvP checks.
 * Adds configurable "PvP between members" and "PvP between allies" team properties.
 */
public class TeamsFriendlyFire {
    public static final String MOD_ID = "teamsfriendlyfire";
    public static final Logger LOG = LoggerFactory.getLogger("Teams Friendly Fire");

    public static void init() {
        TeamEvent.COLLECT_PROPERTIES.register(TeamsFriendlyFire::registerProperties);
    }

    private static void registerProperties(TeamCollectPropertiesEvent event) {
        event.add(TeamPropertiesTFF.PVP_BETWEEN_MEMBERS);
        event.add(TeamPropertiesTFF.PVP_BETWEEN_ALLIES);
    }
}
