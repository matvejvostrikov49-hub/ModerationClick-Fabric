package dev.moderationclick.mixin;

import dev.moderationclick.ModerationMenuScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.text.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin {
    @Shadow protected MinecraftClient client;

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void moderationclick$openOnNickname(double mouseX, double mouseY, int button,
                                                CallbackInfoReturnable<Boolean> cir) {
        if (button != 0 || client == null || client.inGameHud == null) return;
        Style style = client.inGameHud.getChatHud().getTextStyleAt(mouseX, mouseY);
        if (style == null) return;
        String insertion = style.getInsertion();
        if (insertion == null) return;
        String candidate = insertion.trim();
        if (!candidate.matches("[A-Za-z0-9_]{1,16}")) return;
        client.setScreen(new ModerationMenuScreen(candidate));
        cir.setReturnValue(true);
    }
}
