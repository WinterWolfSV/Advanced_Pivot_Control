package winterwolfsv.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;

public class ModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {

        // Return the screen here with the one you created from Cloth Config Builder
        return parent -> AutoConfig.getConfigScreen(Config.class, parent).get();

    }
}