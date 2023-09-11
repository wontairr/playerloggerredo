package wontairr.playerlogger.mixin;


import net.minecraft.core.block.BlockFluidFlowing;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemBucket;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wontairr.playerlogger.PlayerLogger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Mixin(ItemBucket.class)
public class LoggerMixinLavaItem {
    @Inject(at = @At("HEAD"), method = "onItemRightClick", remap = false)
    public void onItemRightClick(ItemStack stack, World world, EntityPlayer player, CallbackInfoReturnable<ItemStack> cir){
        String name = player.username;


        String eventLog = name + " used a bucket at: " + player.x + " " + player.y + " " + player.z + " ";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        PlayerLogger.logFile(dtf.format(now) + " || " + eventLog,  name + "_BlockPlaceRecord");
        PlayerLogger.logFile(dtf.format(now) + " || " + eventLog,  "FullBlockPlaceRecord");
    }
}
