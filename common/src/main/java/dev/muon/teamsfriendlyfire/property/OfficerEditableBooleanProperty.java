package dev.muon.teamsfriendlyfire.property;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.config.ConfigValue;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.client.ClientTeamManager;
import dev.ftb.mods.ftbteams.api.client.KnownClientPlayer;
import dev.ftb.mods.ftbteams.api.property.BooleanProperty;
import dev.ftb.mods.ftbteams.api.property.TeamPropertyType;
import dev.ftb.mods.ftbteams.api.property.TeamPropertyValue;
import dev.muon.teamsfriendlyfire.TeamsFriendlyFire;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class OfficerEditableBooleanProperty extends BooleanProperty {

    // Custom type so network sync reconstructs this class on the client;
    // the vanilla "boolean" type deserializes to plain BooleanProperty,
    // discarding the config() override
    public static final TeamPropertyType<Boolean> TYPE = TeamPropertyType.register(
            ResourceLocation.fromNamespaceAndPath(TeamsFriendlyFire.MOD_ID, "officer_boolean"),
            OfficerEditableBooleanProperty::fromNetwork);

    public OfficerEditableBooleanProperty(ResourceLocation id, Boolean def) {
        super(id, def);
    }

    private static OfficerEditableBooleanProperty fromNetwork(ResourceLocation id, RegistryFriendlyByteBuf buf) {
        return new OfficerEditableBooleanProperty(id, buf.readBoolean());
    }

    @Override
    public TeamPropertyType<Boolean> getType() {
        return TYPE;
    }

    // The server drops property updates from non-officers
    // (UpdatePropertiesRequestMessage.handle), but the vanilla GUI entry still
    // edits the client-side copy, desyncing it until the next full sync.
    // Graying out the entry for non-officers prevents that.
    @Override
    public ConfigValue<?> config(ConfigGroup config, TeamPropertyValue<Boolean> value) {
        return config.addBool(getId().getPath(), value.getValue(), value::setValue, getDefaultValue())
                .setCanEdit(clientPlayerIsOfficerOrBetter());
    }

    private static boolean clientPlayerIsOfficerOrBetter() {
        if (!FTBTeamsAPI.api().isClientManagerLoaded()) return false;
        ClientTeamManager manager = FTBTeamsAPI.api().getClientManager();
        KnownClientPlayer self = manager.self();
        return self != null && manager.selfTeam().getRankForPlayer(self.id()).isOfficerOrBetter();
    }
}
