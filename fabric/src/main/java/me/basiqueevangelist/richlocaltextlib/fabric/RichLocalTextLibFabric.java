package me.basiqueevangelist.richlocaltextlib.fabric;

import me.basiqueevangelist.richlocaltextlib.RichLocalTextLib;
import net.fabricmc.api.ModInitializer;

public class RichLocalTextLibFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        RichLocalTextLib.init();
    }
}
