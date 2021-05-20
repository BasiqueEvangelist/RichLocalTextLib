package me.basiqueevangelist.texttranslations.forge;

import me.basiqueevangelist.texttranslations.TextTranslations;
import me.shedaniel.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;

@Mod(TextTranslations.MOD_ID)
public class TextTranslationsForge {
    public TextTranslationsForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(TextTranslations.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        TextTranslations.init();

        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
    }
}
