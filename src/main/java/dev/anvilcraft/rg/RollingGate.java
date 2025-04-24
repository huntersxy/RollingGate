package dev.anvilcraft.rg;

import com.mojang.logging.LogUtils;
import dev.anvilcraft.rg.api.RGAdditional;
import dev.anvilcraft.rg.api.server.ServerRGRuleManager;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Optional;

@Mod(RollingGate.MODID)
public class RollingGate {
    public static final String MODID = "rolling_gate";
    public static final Logger LOGGER = LogUtils.getLogger();
    private static final ServerRGRuleManager SERVER_RULE_MANAGER = new ServerRGRuleManager(RollingGate.MODID);

    public RollingGate(@NotNull IEventBus modEventBus, @SuppressWarnings("unused") @NotNull ModContainer modContainer) throws ClassNotFoundException {
        modEventBus.addListener(this::onLoadComplete);
        NeoForge.EVENT_BUS.addListener(this::onServerStarting);
        NeoForge.EVENT_BUS.addListener(this::registerCommand);
        SERVER_RULE_MANAGER.compileContent();
    }

    @SubscribeEvent
    public void onLoadComplete(FMLLoadCompleteEvent event) {
        ModList.get().forEachModContainer((modId, modContainer) -> {
            RollingGate.SERVER_RULE_MANAGER.setNamespace(modId);
            @SuppressWarnings("deprecation")
            Optional<RGAdditional> additional = modContainer.getCustomExtension(RGAdditional.class);
            additional.ifPresent(add -> add.loadServerRules(RollingGate.SERVER_RULE_MANAGER));
        });
    }

    @SubscribeEvent
    public void onServerStarting(@NotNull ServerStartingEvent event) {
        RollingGate.SERVER_RULE_MANAGER.reInit(event.getServer());
    }

    @SubscribeEvent
    public void registerCommand(@NotNull RegisterCommandsEvent event) {
        RollingGate.SERVER_RULE_MANAGER.generateCommand(event.getDispatcher(), MODID, "rg");
    }

    public static @NotNull ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
