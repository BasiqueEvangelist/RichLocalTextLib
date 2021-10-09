package me.basiqueevangelist.richlocaltextlib.forge;

import me.basiqueevangelist.richlocaltextlib.RichLocalTextLib;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.network.FMLNetworkConstants;

@Mod(RichLocalTextLib.MOD_ID)
public class RichLocalTextLibForge {
    public RichLocalTextLibForge() {
        // Submit our event bus to let architectury register our content on the right time
        RichLocalTextLib.init();

        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(
            () -> FMLNetworkConstants.IGNORESERVERONLY,
            (a, b) -> true
        ));
    }
}
