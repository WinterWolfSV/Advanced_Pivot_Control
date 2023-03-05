package winterwolfsv.advancedangleassist;

import net.fabricmc.api.ModInitializer;
import winterwolfsv.advancedangleassist.client.Commands;

public class AdvancedAngleAssist implements ModInitializer {
    @Override
    public void onInitialize() {
        Commands.register();
    }
}
