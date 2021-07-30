package com.mrbysco.liquidblocks.init.recipes;

import com.mrbysco.liquidblocks.LiquidBlocks;
import com.mrbysco.liquidblocks.init.recipes.ShapedNoRemainderRecipe.SerializerShapedNoRemainderRecipe;
import com.mrbysco.liquidblocks.init.recipes.ShapelessNoRemainderRecipe.SerializerShapelessNoRemainderRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LiquidRecipes {
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, LiquidBlocks.MOD_ID);

	public static final RegistryObject<SerializerShapedNoRemainderRecipe> SHAPED_NO_REMAINDER_SERIALIZER = RECIPE_SERIALIZERS.register("shaped_no_remainder", SerializerShapedNoRemainderRecipe::new);
	public static final RegistryObject<SerializerShapelessNoRemainderRecipe> SHAPELESS_NO_REMAINDER_SERIALIZER = RECIPE_SERIALIZERS.register("shapeless_no_remainder", SerializerShapelessNoRemainderRecipe::new);
}
