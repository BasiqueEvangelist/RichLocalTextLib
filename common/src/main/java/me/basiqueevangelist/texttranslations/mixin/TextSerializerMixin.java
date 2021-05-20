package me.basiqueevangelist.texttranslations.mixin;

import com.google.gson.*;
import me.basiqueevangelist.texttranslations.InsertingText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.JsonHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Type;

@Mixin(Text.Serializer.class)
public abstract class TextSerializerMixin {
    @Shadow public abstract MutableText deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException;

    @Inject(method = "deserialize", at = @At(value = "INVOKE", target = "Lcom/google/gson/JsonObject;has(Ljava/lang/String;)Z"), cancellable = true)
    private void deserializeCustomText(JsonElement el, Type type, JsonDeserializationContext ctx, CallbackInfoReturnable<MutableText> cir) {
        JsonObject obj = el.getAsJsonObject();
        if (obj.has("index")) {
            InsertingText text = new InsertingText(obj.getAsJsonPrimitive("index").getAsInt());

            if (el.getAsJsonObject().has("extra")) {
                JsonArray extra = JsonHelper.getArray(obj, "extra");
                if (extra.size() <= 0) {
                    throw new JsonParseException("Unexpected empty array of components");
                }

                for (int j = 0; j < extra.size(); ++j) {
                    text.append(deserialize(extra.get(j), type, ctx));
                }
            }

            text.setStyle(ctx.deserialize(el, Style.class));

            cir.setReturnValue(text);
        }
    }
}
