package minecaft.openirondoors;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Door;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class OpenIronDoors extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getLogger().info("[OpenIronDoors] OpenIronDoors has been Enabled");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("[OpenIronDoors] OpenIronDoors has been Disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false; // このプラグインではコマンドは処理しない
    }

    @EventHandler
    public void onInteractEvent(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) {
            return;
        }

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.IRON_DOOR) {
            Player player = e.getPlayer();
            Block block = e.getClickedBlock();
            Door door = (Door) block.getBlockData();

            if (!door.isOpen()) {
                // ドアを開けてサウンドを再生
                door.setOpen(true);
                block.setBlockData(door);
                player.playSound(block.getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 1.0f, 1.0f);

                // 2秒後にドアを閉めてサウンドを再生
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        door.setOpen(false);
                        block.setBlockData(door);
                        player.playSound(block.getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 1.0f, 1.0f);
                    }
                }.runTaskLater(this, 40L); // 40 ticks = 2 seconds
            }
        }
    }
}
