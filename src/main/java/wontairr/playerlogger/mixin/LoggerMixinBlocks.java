package wontairr.playerlogger.mixin;


import net.minecraft.core.block.Block;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.lang.Language;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wontairr.playerlogger.PlayerLogger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Mixin(Block.class)
public abstract class LoggerMixinBlocks {

    @Inject(at = @At("HEAD"), method = "onBlockPlaced", remap = false)
    public void onBlockPlaced(World world, int x, int y, int z, Side side, EntityLiving entity, double sideHeight, CallbackInfo ci){
        if(world == null) {return;}
        String nameIn = entity.getDisplayName();
        String nameIn2 = nameIn.replaceAll("[^a-zA-Z0-9]", "");
        String name = nameIn2.substring(1);

        String blockInQuestion = world.getBlock(x,y,z).toString();
        int blockId = world.getBlock(x,y,z).id;
        String eventLog = name + " placed a block of id '" + blockId + "' at: " + x + " " + y + " " + z + " ";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        PlayerLogger.LOGGER.info(eventLog);
        if(blockId == 580){
            PlayerLogger.logFile(dtf.format(now) + " || " + eventLog + " !!!BLOCK WAS TNT!!!",  name + "_BlockPlaceRecord");
            PlayerLogger.logFile(dtf.format(now) + " || " + eventLog + " !!!BLOCK WAS TNT!!!",  "FullBlockPlaceRecord");
        } else if(blockId == 550){
            PlayerLogger.logFile(dtf.format(now) + " || " + eventLog + " !!!BLOCK WAS SPIKES!!! ",  name + "_BlockPlaceRecord");
            PlayerLogger.logFile(dtf.format(now) + " || " + eventLog + " !!!BLOCK WAS SPIKES!!! ",  "FullBlockPlaceRecord");
        } else {
            PlayerLogger.logFile(dtf.format(now) + " || " + eventLog,  name + "_BlockPlaceRecord");
            PlayerLogger.logFile(dtf.format(now) + " || " + eventLog,  "FullBlockPlaceRecord");
        }
    }

    @Inject(at = @At("HEAD"), method = "onBlockDestroyedByExplosion", remap = false)
    public void onBlockDestroyedByExplosion(World world, int x, int y, int z, CallbackInfo ci){
        String eventLog = "A block exploded at: " + x + " " + y + " " + z + " ";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        PlayerLogger.LOGGER.info(eventLog);
        PlayerLogger.logFile(dtf.format(now) + " || " + eventLog,  "Block_Explosion_Record");
    }

}
