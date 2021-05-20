package me.basiqueevangelist.texttranslations.fabric;

import me.basiqueevangelist.texttranslations.TextTranslations;
import net.fabricmc.api.ModInitializer;

public class TextTranslationsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        TextTranslations.init();
    }
}
