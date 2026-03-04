package dev.muon.teamsfriendlyfire.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.muon.teamsfriendlyfire.compat.FTBTeamsUtils;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Hooks the vanilla PvP gate so FTB Teams alliance is respected
 * in code paths that call Player.canHarmPlayer directly
 * (e.g. ServerPlayer.hurt, Wolf.wantsToAttack, AbstractArrow damage).
 */
@Mixin(Player.class)
public class PlayerMixin {

    @ModifyReturnValue(method = "canHarmPlayer", at = @At("RETURN"))
    private boolean teamsfriendlyfire$preventFTBTeamPvP(boolean original, Player other) {
        if (!original) return false;
        Player self = (Player) (Object) this;
        return !FTBTeamsUtils.arePlayersProtectedAllies(self.getUUID(), other.getUUID(), self.level());
    }
}
