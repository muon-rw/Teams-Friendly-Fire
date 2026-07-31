package dev.muon.teamsfriendlyfire;

import dev.ftb.mods.ftbteams.api.event.CollectTeamPropertiesEvent;
import dev.muon.teamsfriendlyfire.config.ConfigTFF;
import dev.muon.teamsfriendlyfire.property.TeamPropertiesTFF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * Teams Friendly Fire - integrates FTB Teams with vanilla allied/PvP checks.
 * Adds configurable "PvP between members" and "PvP between allies" team properties.
 */
public class TeamsFriendlyFire {
    public static final String MOD_ID = "teamsfriendlyfire";
    public static final Logger LOG = LoggerFactory.getLogger("Teams Friendly Fire");

    /**
     * Called by each loader entrypoint with a registrar that subscribes the
     * given listener to the platform-native CollectTeamProperties event
     * (NeoForge: {@code FTBTeamsEvent.CollectTeamProperties}; Fabric:
     * {@code FTBTeamsEvents.COLLECT_TEAM_PROPERTIES}).
     */
    public static void init(Consumer<CollectTeamPropertiesEvent> registerPropertyCollector) {
        ConfigTFF.register();
        TeamPropertiesTFF.bootstrap();
        registerPropertyCollector.accept(data -> {
            data.addProperty(TeamPropertiesTFF.PVP_BETWEEN_MEMBERS);
            data.addProperty(TeamPropertiesTFF.PVP_BETWEEN_ALLIES);
        });
    }
}
