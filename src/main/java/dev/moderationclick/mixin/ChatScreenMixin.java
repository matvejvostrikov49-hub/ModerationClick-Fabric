package dev.moderationclick.mixin;

import dev.moderationclick.ModerationMenuScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatHud.class)
public abstract class ChatScreenMixin {
    @Shadow
    public abstract Style getTextStyleAt(double x, double y);

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void moderationclick$openOnNickname(double mouseX, double mouseY,
                                                 CallbackInfoReturnable<Boolean> cir) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.getNetworkHandler() == null) return;

        Style style = getTextStyleAt(mouseX, mouseY);
        if (style == null) return;

        String candidate = style.getInsertion();
        if (candidate == null && style.getClickEvent() != null) {
            String value = style.getClickEvent().getValue();
            if (value != null) {
                candidate = value.replaceFirst("^/+", "").trim();
                if (candidate.startsWith("msg ")) candidate = candidate.substring(4).split("\\s+")[0];
            }
        }
        if (candidate == null) return;

        candidate = candidate.trim();
        if (!candidate.matches("[A-Za-z0-9_]{1,16}")) return;

        client.setScreen(new ModerationMenuScreen(candidate));
        cir.setReturnValue(true);
    }
}
