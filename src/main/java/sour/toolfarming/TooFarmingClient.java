package sour.toolfarming;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;


@Environment(EnvType.CLIENT)
public class TooFarmingClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

       // EntityRendererRegistry.register(ModEntities.FAT_BOY, FatBoyRenderer::new);

}}
