package net.isabel.aeroscapes.registry;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.isabel.aeroscapes.Aeroscapes;
import net.isabel.aeroscapes.item.AeroliteCharmItem;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class AeroscapesItems {

    public static final Item AEROLITE_TWIG = registerItem("aerolite_twig",
            new Item(new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(0).saturationModifier(0.1f).alwaysEdible().snack()
                            .statusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 200, 0), 1.0f)
                            .build())));

    public static final Item GOLDEN_AEROLITE_TWIG = registerItem("golden_aerolite_twig",
            new Item(new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(0).saturationModifier(0.5f).alwaysEdible().snack()
                            .statusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 300, 0), 1.0f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 1200, 1), 1.0f)
                            .build())));

    public static final Item MUSIC_DISC_YAW = registerItem("music_disc_yaw",
         new Item(new Item.Settings().jukeboxPlayable(AeroscapesSounds.MUSIC_DISC_YAW_KEY).maxCount(1).rarity(Rarity.RARE)));

    public static final Item MUSIC_DISC_VIEW = registerItem("music_disc_view",
            new Item(new Item.Settings().jukeboxPlayable(AeroscapesSounds.MUSIC_DISC_VIEW_KEY).maxCount(1).rarity(Rarity.RARE)));

    public static final Item AEROLITE_CHARM = registerItem("aerolite_charm",
            new AeroliteCharmItem(new Item.Settings().maxDamage(350)));

    public static final Item CRUSHED_AEROLITE = registerItem("crushed_aerolite",
            new Item(new Item.Settings()));

    public static void registerItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(AEROLITE_TWIG);
            fabricItemGroupEntries.add(GOLDEN_AEROLITE_TWIG);
            fabricItemGroupEntries.add(CRUSHED_AEROLITE);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(MUSIC_DISC_YAW);
            fabricItemGroupEntries.add(MUSIC_DISC_VIEW);
            fabricItemGroupEntries.add(AEROLITE_CHARM);
        });

        Aeroscapes.LOGGER.info("Registering Aeroscapes Items...");
    }

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Aeroscapes.MOD_ID, name), item);
    }
}
