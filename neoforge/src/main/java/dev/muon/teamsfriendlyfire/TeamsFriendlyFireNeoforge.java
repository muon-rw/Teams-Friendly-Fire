package dev.muon.teamsfriendlyfire;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(TeamsFriendlyFire.MOD_ID)
public class TeamsFriendlyFireNeoforge {

    public TeamsFriendlyFireNeoforge(IEventBus eventBus) {
        TeamsFriendlyFire.init();
    }
}
