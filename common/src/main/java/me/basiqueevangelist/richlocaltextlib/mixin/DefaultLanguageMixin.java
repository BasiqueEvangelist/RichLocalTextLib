package me.basiqueevangelist.richlocaltextlib.mixin;

import me.basiqueevangelist.richlocaltextlib.access.DefaultLanguageAccess;
import me.basiqueevangelist.richlocaltextlib.access.TextLanguage;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(targets = "net.minecraft.util.Language$1")
public class DefaultLanguageMixin implements TextLanguage, DefaultLanguageAccess {
    @Unique Map<String, Text> textMap;

    @Inject(method = "hasTranslation", at = @At("HEAD"), cancellable = true)
    private void hasTranslation(String key, CallbackInfoReturnable<Boolean> cir) {
        if (textMap.containsKey(key))
            cir.setReturnValue(true);
    }

    @Inject(method = "get", at = @At("HEAD"), cancellable = true)
    private void get(String key, CallbackInfoReturnable<String> cir) {
        if (textMap.containsKey(key))
            cir.setReturnValue(textMap.get(key).asString());
    }

    @Override
    public @Nullable Text getText(String key) {
        return textMap.get(key);
    }

    @Override
    public void setTextMap(Map<String, Text> textMap) {
        this.textMap = textMap;
    }
}
