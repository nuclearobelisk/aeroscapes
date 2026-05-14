package net.isabel.aeroscapes.registry;

import net.isabel.aeroscapes.Aeroscapes;
import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class AeroscapesSounds {
    public static SoundEvent MUSIC_DISC_YAW = registerSoundEvent("music_disc.yaw");
    public static final RegistryKey<JukeboxSong> MUSIC_DISC_YAW_KEY = RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(Aeroscapes.MOD_ID, "yaw"));

    public static SoundEvent MUSIC_DISC_VIEW = registerSoundEvent("music_disc.view");
    public static final RegistryKey<JukeboxSong> MUSIC_DISC_VIEW_KEY = RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(Aeroscapes.MOD_ID, "view"));

    public static SoundEvent AEROLITE_CHARM_SUCCESS = registerSoundEvent("item.aerolite_charm.success");
    public static SoundEvent AEROLITE_CHARM_FAIL = registerSoundEvent("item.aerolite_charm.fail");

    public static void registerSounds() {
        Aeroscapes.LOGGER.info("Registering Aeroscapes Sounds...");
    }

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(Aeroscapes.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
}
