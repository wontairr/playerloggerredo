package wontairr.playerlogger.mixin;


import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.net.packet.Packet255KickDisconnect;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wontairr.playerlogger.PlayerLogger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mixin(World.class)
public class LoggerMixinPlayer {
    int counter = 0;
    @Inject(at = @At("HEAD"), method = "tick", remap = false)
    public void tick(CallbackInfo ci){
        // Get the currently active world
        World world = (World)(Object)this;
        List<EntityPlayer> players = world.players;
        counter = counter + 1;
        if(counter >= 2500){
            counter = 0;
            for (EntityPlayer player : players) {
                if(player == null) { return; }
                String nameIn = player.username;
                String name = nameIn.replaceAll("[^a-zA-Z0-9]", "");
                String eventLog = name + " is at position " + player.x + " " + player.y + " " + player.z;
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();

                //PlayerLogger.LOGGER.info(eventLog);
                PlayerLogger.logFile(dtf.format(now) + " || " + eventLog,  name + "_PositionRecord");
                PlayerLogger.logFile(dtf.format(now) + " || " + eventLog,  "FullPositionRecord");
            }
        }
    }
}
