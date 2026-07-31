package dev.muon.teamsfriendlyfire.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.ftb.mods.ftbteams.api.property.TeamProperty;
import dev.ftb.mods.ftbteams.api.property.TeamPropertyType;
import dev.muon.teamsfriendlyfire.property.TeamPropertiesTFF;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * TeamPropertyType.read rebuilds synced properties as vanilla instances,
 * which discards the OfficerEditableBooleanProperty GUI override on the
 * client; swap our properties back to the registered instances.
 */
@Mixin(value = TeamPropertyType.class, remap = false)
public class TeamPropertyTypeMixin {

    @ModifyReturnValue(method = "read", at = @At("RETURN"))
    private static TeamProperty<?> teamsfriendlyfire$restoreRegisteredInstance(TeamProperty<?> original) {
        return TeamPropertiesTFF.canonical(original);
    }
}
