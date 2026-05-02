package dev.sakura.client.modules.impl.combat;

import dev.sakura.client.events.bus.EventHandler;
import dev.sakura.client.events.impl.SwingHandEvent;
import dev.sakura.client.managers.RotationManager;
import dev.sakura.client.managers.TargetManager;
import dev.sakura.client.modules.Category;
import dev.sakura.client.modules.Module;
import dev.sakura.client.settings.impl.BoolSetting;
import dev.sakura.client.settings.impl.DoubleSetting;
import dev.sakura.client.settings.impl.IntSetting;
import dev.sakura.client.utils.rotation.Priority;
import dev.sakura.client.utils.rotation.RotationUtils;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
import org.joml.Vector2f;

public class SilentAim extends Module {

    public static final SilentAim INSTANCE = new SilentAim();

    private SilentAim() {
        super("Silent Aim", Category.COMBAT);
    }

    private final BoolSetting weaponOnly = boolSetting("Weapon Only", false);

    private final BoolSetting player = boolSetting("Player", true);
    private final BoolSetting mob = boolSetting("Mob", true);
    private final BoolSetting animal = boolSetting("Animal", true);
    private final BoolSetting villagers = boolSetting("Villagers", false);
    private final BoolSetting invisible = boolSetting("Invisible", true);

    private final DoubleSetting range = doubleSetting("Range", 3.0, 1.0, 6.0, 0.1);
    private final IntSetting fov = intSetting("FOV", 360, 10, 360, 1);

    private boolean redirecting;

    @EventHandler
    private void onSwingHand(SwingHandEvent event) {
        if (redirecting) return;

        if (weaponOnly.getValue() && !mc.player.getMainHandItem().has(DataComponents.WEAPON)) {
            return;
        }

        if (mc.hitResult == null || mc.hitResult.getType() != HitResult.Type.MISS) {
            return;
        }

        LivingEntity target = TargetManager.INSTANCE.acquirePrimary(TargetManager.TargetRequest.of(
                range.getValue(), fov.getValue(), player.getValue(), mob.getValue(), animal.getValue(), villagers.getValue(), invisible.getValue(), 1
        ));
        if (target == null) return;

        event.setCancelled(true);
        redirecting = true;

        Vector2f rotations = RotationUtils.calculate(target.getEyePosition());

        RotationManager.INSTANCE.applyRotation(rotations, 10, Priority.High, _ -> {
            if (!target.isAlive() || target.isDeadOrDying() || nullCheck()) {
                redirecting = false;
                return;
            }

            mc.gameMode.attack(mc.player, target);
            mc.player.swing(InteractionHand.MAIN_HAND);

            redirecting = false;
        });
    }

}
