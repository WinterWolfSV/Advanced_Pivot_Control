package winterwolfsv.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import winterwolfsv.advancedpivotcontrol.client.AdvancedPivotControlClient;

@me.shedaniel.autoconfig.annotation.Config(name = AdvancedPivotControlClient.MOD_ID)
public
class Config implements ConfigData {
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.BoundedDiscrete(max = 180, min = 1)
    public int yawSteps = 45;
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.BoundedDiscrete(max = 90, min = 1)
    public int pitchSteps = 30;

    @ConfigEntry.Gui.Tooltip()
    public boolean messageFeedback = true;

}
