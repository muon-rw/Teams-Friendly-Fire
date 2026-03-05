package dev.muon.teamsfriendlyfire;


import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.common.Mod;

@Mod(TeamsFriendlyFire.MOD_ID)
public class TeamsFriendlyFireForge {

    public TeamsFriendlyFireForge() {
        TeamsFriendlyFire.init();
    }
}
