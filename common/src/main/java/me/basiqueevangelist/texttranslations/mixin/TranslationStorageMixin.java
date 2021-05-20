package me.basiqueevangelist.texttranslations.mixin;

import me.basiqueevangelist.texttranslations.access.LanguageAccess;
import me.basiqueevangelist.texttranslations.access.TextLanguage;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(TranslationStorage.class)
public class TranslationStorageMixin implements TextLanguage {
    @Shadow @Final private Map<String, String> translations;
    @Unique private static Map<String, Text> buildingTextMap;

    @Unique private Map<String, Text> textMap;

    @Inject(method = "load(Lnet/minecraft/resource/ResourceManager;Ljava/util/List;)Lnet/minecraft/client/resource/language/TranslationStorage;", at = @At("HEAD"))
    private static void initTextMap(CallbackInfoReturnable<TranslationStorage> cir) {
        buildingTextMap = new HashMap<>();
        LanguageAccess.textConsumer = buildingTextMap::put;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        textMap = buildingTextMap;
        buildingTextMap = null;
    }

    @Inject(method = "hasTranslation", at = @At("HEAD"), cancellable = true)
    private void hasTranslation(String key, CallbackInfoReturnable<Boolean> cir) {
        if (textMap.containsKey(key))
            cir.setReturnValue(true);
    }

    @Inject(method = "get", at = @At("HEAD"), cancellable = true)
    private void get(String key, CallbackInfoReturnable<String> cir) {
        if (textMap.containsKey(key))
            cir.setReturnValue(textMap.get(key).getString());
    }

    @Override
    public @Nullable Text getText(String key) {
        return textMap.get(key);
    }
}
