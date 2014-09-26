package moze_intel.gameObjs.blocks;

import java.util.Random;

import moze_intel.MozeCore;
import moze_intel.gameObjs.ObjHandler;
import moze_intel.gameObjs.tiles.TransmuteTile;
import moze_intel.utils.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TransmutationStone extends Block implements ITileEntityProvider
{
	@SideOnly(Side.CLIENT)
	private IIcon[] icon;
	
	public TransmutationStone() 
	{
		super(Material.rock);
		this.setCreativeTab(ObjHandler.cTab);
		this.setBlockName("transmutation_stone");
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
		this.setHardness(10.0f);
	}
	
	@Override
	public Item getItemDropped(int par1, Random random, int par2)
    {
		return Item.getItemFromBlock(ObjHandler.transmuteStone);
    }
	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2) 
	{
		return new TransmuteTile();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		TransmuteTile tile = (TransmuteTile) world.getTileEntity(x, y, z);
			
		if (!tile.isUsed())
		{
			tile.setPlayer(player);
			
			if (!world.isRemote)
			{
				player.openGui(MozeCore.MODID, Constants.TRANSMUTE_STONE_GUI, world, x, y, z);
			}
		}
		else
		{
			if (!world.isRemote)
			{
				player.addChatComponentMessage(new ChatComponentText("Someone is already using this transmutation stone!"));
			}
		}
		return true;
	}
	
	@Override
	public boolean isOpaqueCube()
    {
        return false;
    }
	
	@Override
	public boolean renderAsNormalBlock()
    {
        return false;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
	{
		if (side < 2)
		{
			return icon[side];
		}
		return icon[2];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
		icon = new IIcon[3];
		icon[0] = register.registerIcon("projecte:transmutation_stone/bottom");
		icon[1] = register.registerIcon("projecte:transmutation_stone/top");
		icon[2] = register.registerIcon("projecte:transmutation_stone/side");
    }

}