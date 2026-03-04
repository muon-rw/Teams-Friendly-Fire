package dev.muon.teamsfriendlyfire.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.muon.teamsfriendlyfire.compat.FTBTeamsUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Mixin to make Entity.isAlliedTo respect FTB Teams and our PvP flags.
 * Returns true if two entities' effective owners are protected allies.
 */
@Mixin(Entity.class)
public class EntityMixin {

    @ModifyReturnValue(
            method = "isAlliedTo(Lnet/minecraft/world/entity/Entity;)Z",
            at = @At(value = "RETURN")
    )
    private boolean teamsfriendlyfire$checkFTBTeamsAlliance(boolean original, Entity other) {
        if (original) return true;

        Entity self = (Entity) (Object) this;
        Player selfOwner = FTBTeamsUtils.getEffectivePlayerOwner(self);
        Player otherOwner = FTBTeamsUtils.getEffectivePlayerOwner(other);

        if (selfOwner != null && otherOwner != null) {
            return FTBTeamsUtils.arePlayersProtectedAllies(
                    selfOwner.getUUID(), otherOwner.getUUID(), self.level());
        }
        return false;
    }
}
