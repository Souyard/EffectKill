package fr.bukkit.effectkill.utils.inventory;

import org.bukkit.plugin.java.*;
import java.util.function.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import java.util.*;
import org.bukkit.scheduler.*;
import com.google.common.collect.*;

public class CustomInventory implements Listener {
 
    private static Map<String, CustomInventory> customInventories = Maps.newHashMap();
    private Inventory inventory;
    private JavaPlugin javaPlugin;
    private boolean closable = true;
    private String inventoryTitle;
    private Consumer<InventoryClickEvent> clickEvent = event -> {};
 
    public CustomInventory(JavaPlugin javaPlugin, String inventoryName, boolean save, InventoryHolder inventoryHolder, String inventoryTitle, int inventorySize) {
        this(javaPlugin, inventoryName, inventoryTitle);
        this.inventory = Bukkit.createInventory(inventoryHolder, inventorySize, inventoryTitle);
        if (save) customInventories.put(inventoryName, this);
    }
 
    public CustomInventory(JavaPlugin javaPlugin, String inventoryName, boolean save, Inventory inventory) {
        this(javaPlugin, inventoryName, inventory.getTitle());
        this.inventory = inventory;
        if (save) customInventories.put(inventoryName, this);
    }
 
    private CustomInventory(JavaPlugin javaPlugin, String inventoryName, String inventoryTitle) {
        if (customInventories.containsKey(inventoryName))
            throw new IllegalArgumentException(inventoryName + " already exists.");
        this.javaPlugin = javaPlugin;
        this.inventoryTitle = inventoryTitle;
        Bukkit.getPluginManager().registerEvents(this, javaPlugin);
    }
 
    public static CustomInventory getCustomInventory(String inventoryName) {
        if (exists(inventoryName))
            return customInventories.get(inventoryName);
        throw new IllegalArgumentException(inventoryName + " doesn't exists");
    }
 
    public static void removeCustomInventory(String inventoryName) {
        if (exists(inventoryName))
            customInventories.remove(inventoryName);
        else
            throw new IllegalArgumentException(inventoryName + " doesn't exists");
    }
 
    public static boolean exists(String inventoryName) {
        return customInventories.containsKey(inventoryName);
    }
 
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        final Inventory closedInventory = event.getInventory();
        if (closedInventory.getTitle().equalsIgnoreCase(this.inventory.getTitle()))
            if (!this.closable)
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.javaPlugin, () -> event.getPlayer().openInventory(closedInventory), 5);
    }
 
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() != null && event.getWhoClicked() instanceof Player &&
                event.getInventory() != null && event.getInventory().getTitle().equalsIgnoreCase(this.inventoryTitle) &&
                event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
            event.setCancelled(true);
            this.clickEvent.accept(event);
        }
    }
 
    public CustomInventory closable(boolean closable) {
        this.closable = closable;
        return this;
    }
 
    public CustomInventory setClickEvent(Consumer<InventoryClickEvent> clickEvent) {
        this.clickEvent = clickEvent;
        return this;
    }
 
    public CustomInventory fillSlots(ItemStack item, int[] slots) {
        for (int slot : slots)
            addItem(item, slot);
        return this;
    }
 
    public CustomInventory fill(ItemStack item) {
        for (int i = 0; i < inventory.getSize(); i++)
            addItem(item, i);
        return this;
    }
 
    public CustomInventory line(ItemStack item, int line) {
        for (int i = 0; i < 9; i++) {
            inventory.setItem(line == 0 ? 0 + i :
                    line == 1 ? 9 + i :
                            line == 2 ? 18 + i :
                                    line == 3 ? 27 + i :
                                            line == 4 ? 36 + i :
                                                    line == 5 ? 45 + i :
                                                            line == 6 ? 54 + i :
                                                                    0, item);
        }
        return this;
    }
 
    public CustomInventory borders(ItemStack item, int border) {
        return this;
    }
 
    public CustomInventory column(ItemStack item, int column) {
        int lines = inventory.getSize() / 9;
        for (int i = 0; i < lines; i++)
            addItem(item, column + (9 * i));
        return this;
    }
 
    public CustomInventory advManipule(Consumer<CustomInventory> consumer) {
        consumer.accept(this);
        return this;
    }
 
    public CustomInventory addItems(ItemStack... items) {
        inventory.addItem(items);
        return this;
    }
 
    public CustomInventory addItems(Map<ItemStack, Integer> items) {
        for (ItemStack item : items.keySet())
            addItem(item, items.get(item));
        return this;
    }
 
    public CustomInventory addItems(ItemStack[] items, int[] slots) {
        if (items.length != slots.length)
            throw new IllegalArgumentException(items.length + " != " + slots.length);
        for (int i = 0; i < items.length; i++)
            addItem(items[i], slots[i]);
        return this;
    }
 
    public CustomInventory addItem(ItemStack item, int slot) {
        inventory.setItem(slot, item);
        return this;
    }
 
    public CustomInventory update() {
        ItemStack[] content = inventory.getContents();
        inventory.clear();
        inventory.setContents(content);
        return this;
    }
 
    public Inventory build() {
        return inventory;
    }
 
    public void open(Player... players) {
        for (Player player : players)
            player.openInventory(inventory);
    }
 
    public void open(Collection<Player> players) {
        open(players.toArray(new Player[players.size()]));
    }
 
    public void openRefresh(IAction action, int refreshTime, Player... players) {
        open(players);
        new BukkitRunnable() {
            @Override
            public void run() {
                for (@SuppressWarnings("unused") Player player : players) {
                    update();
                    action.action(this);
                }
            }
        }.runTaskTimerAsynchronously(javaPlugin, refreshTime, refreshTime);
    }
 
    public void openRefresh(IAction action, int refreshTime, Collection<Player> players) {
        openRefresh(action, refreshTime, players.toArray(new Player[players.size()]));
    }
 
    public interface IAction {
        void action(BukkitRunnable bukkitRunnable);
    }
 
}