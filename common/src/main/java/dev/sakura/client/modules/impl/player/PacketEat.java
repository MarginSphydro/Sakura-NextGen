package dev.sakura.client.modules.impl.player;

import dev.sakura.client.events.bus.EventHandler;
import dev.sakura.client.events.impl.PacketEvent;
import dev.sakura.client.events.impl.TickEvent;
import dev.sakura.client.modules.Category;
import dev.sakura.client.modules.Module;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.world.item.ItemStack;

public class PacketEat extends Module {

    public static final PacketEat INSTANCE = new PacketEat();

    private PacketEat() {
        super("Packet Eat", Category.PLAYER);
    }

    private ItemStack item;

    @EventHandler
    private void onClientTickPost(TickEvent.Post event) {
        if (nullCheck()) return;
        if (mc.player.isUsingItem()) {
            item = mc.player.getUseItem();
        }
    }

    @EventHandler
    private void onPacket(PacketEvent.Send event) {
        if (event.getPacket() instanceof ServerboundPlayerActionPacket packet && packet.getAction() == ServerboundPlayerActionPacket.Action.RELEASE_USE_ITEM) {
            if (item.get(DataComponents.FOOD).canAlwaysEat()) {
                event.setCancelled(true);
            }
        }
    }

}
