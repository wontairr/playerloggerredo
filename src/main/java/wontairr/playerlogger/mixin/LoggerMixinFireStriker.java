package wontairr.playerlogger.mixin;


import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemFirestriker;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wontairr.playerlogger.PlayerLogger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mixin(ItemFirestriker.class)
public class LoggerMixinFireStriker {
    @Inject(at = @At("HEAD"), method = "onItemUse", remap = false)
    public void onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced, CallbackInfoReturnable<Boolean> cir){
        String name = entityplayer.username;


        String eventLog = name + " used a Fire Striker at: " + entityplayer.x + " " + entityplayer.y + " " + entityplayer.z + " ";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        PlayerLogger.logFile(dtf.format(now) + " || " + eventLog,  name + "_BlockPlaceRecord");
        PlayerLogger.logFile(dtf.format(now) + " || " + eventLog,  "FullBlockPlaceRecord");
    }
}
