package dev.muon.teamsfriendlyfire.property;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.client.ClientTeamManager;
import dev.ftb.mods.ftbteams.api.client.KnownClientPlayer;
import dev.ftb.mods.ftbteams.api.property.BooleanProperty;
import dev.ftb.mods.ftbteams.api.property.TeamPropertyValue;
import net.minecraft.resources.ResourceLocation;

public class OfficerEditableBooleanProperty extends BooleanProperty {

    public OfficerEditableBooleanProperty(ResourceLocation id, Boolean def) {
        super(id, def);
    }

    // The server drops property updates from non-officers
    // (UpdatePropertiesRequestMessage.handle), but the vanilla GUI entry still
    // edits the client-side copy, desyncing it until the next full sync.
    // Graying out the entry for non-officers prevents that.
    @Override
    public void config(ConfigGroup config, TeamPropertyValue<Boolean> value) {
        config.addBool(getId().getPath(), value.getValue(), value::setValue, getDefaultValue())
                .setCanEdit(clientPlayerIsOfficerOrBetter());
    }

    private static boolean clientPlayerIsOfficerOrBetter() {
        if (!FTBTeamsAPI.api().isClientManagerLoaded()) return false;
        ClientTeamManager manager = FTBTeamsAPI.api().getClientManager();
        KnownClientPlayer self = manager.self();
        return self != null && manager.selfTeam().getRankForPlayer(self.id()).isOfficerOrBetter();
    }
}
