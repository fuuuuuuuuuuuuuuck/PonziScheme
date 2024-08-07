package moe.feo.ponzischeme.task;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Rewards {

    private List<String> commands;
    private List<ItemStack> items;

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public void setItems(List<ItemStack> items) {
        this.items = items;
    }
}
