package me.basiqueevangelist.richlocaltextlib.forge;

import me.basiqueevangelist.richlocaltextlib.RichLocalTextLib;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;

@Mod(RichLocalTextLib.MOD_ID)
public class RichLocalTextLibForge {
    public RichLocalTextLibForge() {
        // Submit our event bus to let architectury register our content on the right time
        RichLocalTextLib.init();

        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
    }
}
