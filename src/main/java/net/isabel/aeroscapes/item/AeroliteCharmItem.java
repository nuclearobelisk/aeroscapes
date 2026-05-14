package net.isabel.aeroscapes.item;

import net.isabel.aeroscapes.registry.AeroscapesSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;

public class AeroliteCharmItem
extends Item {
    public AeroliteCharmItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        user.getItemCooldownManager().set(this, 20);
        Integer newY = getTeleportPos(world, user.getBlockX(), user.getBlockY(), user.getBlockZ());

        if(newY != null) {
            Vec3d pos = new Vec3d(user.getX(), newY, user.getZ());
            int tpDistance = (int)Math.floor((double)newY - user.getY());

            if (!world.isClient) {
                if (user instanceof ServerPlayerEntity) {
                    ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) user;
                    if (serverPlayerEntity.networkHandler.isConnectionOpen() && serverPlayerEntity.getWorld() == world) {
                        user.incrementStat(Stats.USED.getOrCreateStat(this));

                        if (!user.getAbilities().creativeMode)
                            itemStack.damage(1, serverPlayerEntity, LivingEntity.getSlotForHand(hand));

                        if (user.hasVehicle())
                            serverPlayerEntity.requestTeleportAndDismount(pos.getX(), pos.getY(), pos.getZ());
                        else
                            user.requestTeleport(pos.getX(), pos.getY(), pos.getZ());
                    }
                } else if (user != null)
                    user.requestTeleport(pos.getX(), pos.getY(), pos.getZ());
            }

            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), AeroscapesSounds.AEROLITE_CHARM_SUCCESS, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));

            double tpDistanceEffectMultiplier = 0.01 * tpDistance;
            if(user.getRandom().nextDouble() < tpDistanceEffectMultiplier) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, Math.min(200, Math.max((int) (tpDistanceEffectMultiplier * 200), 100)), 0));
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, Math.min(400, Math.max((int) (tpDistanceEffectMultiplier * 200), 600)), 0));
            }

            for (int i = 0; i < 32; ++i) {
                world.addParticle(ParticleTypes.PORTAL, pos.getX(), pos.getY() + user.getRandom().nextDouble() * 2.0, user.getZ(), user.getRandom().nextGaussian(), 0.0, user.getRandom().nextGaussian());
            }

            return TypedActionResult.success(itemStack, world.isClient());
        } else {
            world.playSound(null, user.getX(), user.getY(), user.getZ(), AeroscapesSounds.AEROLITE_CHARM_FAIL, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
            return TypedActionResult.fail(itemStack);
        }
    }

    private Integer getTeleportPos(World world, int x, int y, int z) {
        if (world.getDimension().hasCeiling()) {
            return null;
        }

        BlockPos.Mutable mutable = new BlockPos.Mutable(x, y, z);
        WorldChunk worldChunk = world.getChunk(ChunkSectionPos.getSectionCoord(x), ChunkSectionPos.getSectionCoord(z));

        int top = worldChunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE, x, z);
        boolean blocksMovement = world.getBlockState(mutable).isSolidBlock(world, mutable);

        while (mutable.getY() < top && !blocksMovement) {
            mutable.move(Direction.UP);
            blocksMovement = world.getBlockState(mutable).isSolidBlock(world, mutable);
        }

        if (!blocksMovement)
            return null;

        while (mutable.getY() < top)
            mutable.move(Direction.UP);

        return mutable.up().toImmutable().getY();
    }
}
