package openmods.world;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DropCapture {

	public class CaptureContext {
		private final AxisAlignedBB aabb;

		private final List<EntityItem> drops = Lists.newArrayList();

		public CaptureContext(AxisAlignedBB aabb) {
			this.aabb = aabb;
		}

		private boolean check(EntityItem item) {
			if (!item.isDead && aabb.intersects(item.getEntityBoundingBox())) {
				drops.add(item);
				return true;
			}

			return false;
		}

		public List<EntityItem> stop() {
			captures.remove(this);
			return drops;
		}
	}

	public static final DropCapture instance = new DropCapture();

	private final List<CaptureContext> captures = Lists.newArrayList();

	public CaptureContext start(AxisAlignedBB aabb) {
		CaptureContext context = new CaptureContext(aabb);
		captures.add(context);
		return context;
	}

	public CaptureContext start(int x, int y, int z) {
		return start(new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1));
	}

	public CaptureContext start(BlockPos pos) {
		return start(pos.getX(), pos.getY(), pos.getZ());
	}

	@SubscribeEvent
	public void onEntityConstruct(EntityJoinWorldEvent evt) {
		final Entity e = evt.getEntity();
		if (e != null
				&& e.getClass() == EntityItem.class
				&& !e.world.isRemote) {
			final EntityItem ei = (EntityItem)e;

			for (CaptureContext c : captures)
				if (c.check(ei)) break;
		}
	}

}
