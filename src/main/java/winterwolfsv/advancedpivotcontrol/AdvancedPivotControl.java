package winterwolfsv.advancedpivotcontrol;

import net.fabricmc.api.ModInitializer;
import winterwolfsv.advancedpivotcontrol.client.Commands;


public class AdvancedPivotControl implements ModInitializer {
    @Override
    public void onInitialize() {
        Commands.register();
    }
}
