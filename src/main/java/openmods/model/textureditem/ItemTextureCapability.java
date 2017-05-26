package openmods.model.textureditem;

import com.google.common.base.Optional;
import java.util.concurrent.Callable;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class ItemTextureCapability {

	@CapabilityInject(IItemTexture.class)
	public static Capability<IItemTexture> CAPABILITY = null;

	private static IItemTexture EMPTY = new IItemTexture() {
		@Override
		public Optional<ResourceLocation> getTexture() {
			return Optional.absent();
		}
	};

	public static void register() {
		CapabilityManager.INSTANCE.register(IItemTexture.class, new Capability.IStorage<IItemTexture>() {
			@Override
			public NBTBase writeNBT(Capability<IItemTexture> capability, IItemTexture instance, EnumFacing side) {
				return null;
			}

			@Override
			public void readNBT(Capability<IItemTexture> capability, IItemTexture instance, EnumFacing side, NBTBase nbt) {}
		}, new Callable<IItemTexture>() {
			@Override
			public IItemTexture call() throws Exception {
				return EMPTY;
			}
		});
	}

}