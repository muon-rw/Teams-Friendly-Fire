package dev.muon.teamsfriendlyfire;

import dev.ftb.mods.ftbteams.api.fabric.FTBTeamsEvents;
import net.fabricmc.api.ModInitializer;

public class TeamsFriendlyFireFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        TeamsFriendlyFire.init(FTBTeamsEvents.COLLECT_TEAM_PROPERTIES::register);
    }
}
