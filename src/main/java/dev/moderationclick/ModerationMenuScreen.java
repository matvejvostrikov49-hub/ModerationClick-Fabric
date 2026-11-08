package dev.moderationclick;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class ModerationMenuScreen extends Screen {
    private final String playerName;
    private String reason = "";
    private String duration = "";
    private ButtonWidget confirm;

    public ModerationMenuScreen(String playerName) {
        super(Text.literal("Модерация: " + playerName));
        this.playerName = playerName;
    }

    @Override
    protected void init() {
        sendCommand("work");
        String[] reasons = {"2.2","2.3","2.4","2.5","2.6","2.7","2.8","2.9","2.10","2.11",
                "2.12","2.13","2.14","2.15","2.16","2.17","2.18","2.19","2.20"};
        int left = width / 2 - 155;
        int top = 55;
        for (int i = 0; i < reasons.length; i++) {
            String value = reasons[i];
            int x = left + (i % 5) * 62;
            int y = top + (i / 5) * 26;
            addDrawableChild(ButtonWidget.builder(Text.literal(value), b -> {
                reason = value;
                updateConfirm();
            }).dimensions(x, y, 58, 21).build());
        }

        String[] durations = {"30m", "60m", "180m", "6h", "12h"};
        int durationY = top + 5 * 26 + 12;
        for (int i = 0; i < durations.length; i++) {
            String value = durations[i];
            int x = left + i * 62;
            addDrawableChild(ButtonWidget.builder(Text.literal(value), b -> {
                duration = value;
                updateConfirm();
            }).dimensions(x, durationY, 58, 21).build());
        }

        confirm = addDrawableChild(ButtonWidget.builder(Text.literal("Подтвердить"), b -> {
            if (!reason.isEmpty() && !duration.isEmpty()) {
                sendCommand("tempmute " + playerName + " " + duration + " " + reason);
                close();
            }
        }).dimensions(width / 2 - 100, height - 34, 200, 20).build());
        updateConfirm();
    }

    private void updateConfirm() {
        if (confirm != null) confirm.active = !reason.isEmpty() && !duration.isEmpty();
    }

    private void sendCommand(String command) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null && client.getNetworkHandler() != null) {
            client.getNetworkHandler().sendChatCommand(command);
        }
    }

    @Override
    public void close() {
        sendCommand("work");
        super.close();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, "Игрок: " + playerName, width / 2, 28, 0xFFFFFF);
        context.drawCenteredTextWithShadow(textRenderer, "Выбери правило", width / 2, 42, 0xFFFFFF);
        context.drawCenteredTextWithShadow(textRenderer, "Длительность мута", width / 2, 203, 0xFFFFFF);
        context.drawCenteredTextWithShadow(textRenderer,
                "Правило: " + (reason.isEmpty() ? "—" : reason) + "    Время: " + (duration.isEmpty() ? "—" : duration),
                width / 2, height - 52, 0xCCCCCC);
        super.render(context, mouseX, mouseY, delta);
    }
}
