package dev.muon.teamsfriendlyfire;

import dev.ftb.mods.ftbteams.api.neoforge.FTBTeamsEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(TeamsFriendlyFire.MOD_ID)
public class TeamsFriendlyFireNeoforge {

    public TeamsFriendlyFireNeoforge(IEventBus eventBus) {
        TeamsFriendlyFire.init(listener ->
                NeoForge.EVENT_BUS.addListener(FTBTeamsEvent.CollectTeamProperties.class,
                        event -> listener.accept(event.getEventData())));
    }
}
