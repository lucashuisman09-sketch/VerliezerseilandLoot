
package nl.lucashuisman.verliezerseilandloot;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseLootEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class VerliezerseilandLoot extends JavaPlugin implements Listener {

    private static final String WORLD_NAME = "verliezerseiland";

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("VerliezerseilandLoot is ingeschakeld!");
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onVaultLoot(BlockDispenseLootEvent event) {

        // Alleen actief in Verliezerseiland
        if (!event.getBlock().getWorld().getName().equalsIgnoreCase(WORLD_NAME)) {
            return;
        }

        String lootTable = event.getLootTable().getKey().toString();

        // Bewaar alle originele loot
        List<ItemStack> loot = new ArrayList<>(event.getDispensedLoot());

        // Normale Trial Vault
        if (lootTable.equals("minecraft:chests/trial_chambers/reward")) {

            addRandomLoot(loot, Material.BLAZE_POWDER, 1, 2, 0.15);
            addRandomLoot(loot, Material.SOUL_SAND, 2, 3, 0.20);

        }

        // Ominous Trial Vault
        else if (lootTable.equals("minecraft:chests/trial_chambers/reward_ominous")) {

            addRandomLoot(loot, Material.BLAZE_ROD, 2, 3, 0.15);
            addRandomLoot(loot, Material.NETHER_WART, 3, 5, 0.25);

        } else {
            return;
        }

        event.setDispensedLoot(loot);
    }

    private void addRandomLoot(
            List<ItemStack> loot,
            Material material,
            int minAmount,
            int maxAmount,
            double chance
    ) {
        if (ThreadLocalRandom.current().nextDouble() < chance) {
            int amount = ThreadLocalRandom.current().nextInt(
                    minAmount, maxAmount + 1
            );

            loot.add(new ItemStack(material, amount));
        }
    }
}
