package wontairr.playerlogger.mixin;


import net.minecraft.core.block.BlockChest;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wontairr.playerlogger.PlayerLogger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mixin(BlockChest.class)
public abstract class LoggerMixinChests {
    @Inject(at = @At("HEAD"), method = "blockActivated", remap = false)
    public void blockActivated(World world, int x, int y, int z, EntityPlayer player, CallbackInfoReturnable<Boolean> cir){
        if(world == null) {return;}
        String nameIn = player.username;
        String name = nameIn.replaceAll("[^a-zA-Z0-9]", "");
        String eventLog = name + " opened a chest at: " + x + " " + y + " " + z + " ";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        PlayerLogger.LOGGER.info(eventLog);
        PlayerLogger.logFile(dtf.format(now) + " || " + eventLog,  name + "_ChestRecord");
        PlayerLogger.logFile(dtf.format(now) + " || " + eventLog,  "FullChestRecord");
    }
}
