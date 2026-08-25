package dev.anvilcraft.rg.mixin;

import dev.anvilcraft.rg.api.event.ServerAboutToStopEvent;
import dev.anvilcraft.rg.api.event.ServerLoadedLevelEvent;
import net.minecraft.server.MinecraftServer;
//? if <1.21.8 {
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
//?}
//? if >=1.21.8 && <1.21.10
/*import net.neoforged.fml.loading.FMLLoader;*/
//? if >=1.21.10
/*import net.neoforged.fml.loading.FMLEnvironment;*/
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
abstract class MinecraftServerMixin {
    //? if <1.21.8
    @OnlyIn(Dist.DEDICATED_SERVER)
    @Inject(
        method = "stopServer", at = @At("HEAD")
    )
    private void serverClosed(CallbackInfo ci) {
        //? if >=1.21.8 && <1.21.10 {
        /*if (!FMLLoader.getDist().isDedicatedServer()) {
            return;
        }
         *///?}
        //? if >=1.21.10 {
        /*if (!FMLEnvironment.getDist().isDedicatedServer()) {
            return;
        }
         *///?}
        MinecraftServer server = (MinecraftServer) (Object) this;
        NeoForge.EVENT_BUS.post(new ServerAboutToStopEvent(server));
    }

    @Inject(
        method = "loadLevel", at = @At("RETURN")
    )
    private void serverLoadedWorlds(CallbackInfo ci) {
        NeoForge.EVENT_BUS.post(new ServerLoadedLevelEvent((MinecraftServer) (Object) this));
    }
}
