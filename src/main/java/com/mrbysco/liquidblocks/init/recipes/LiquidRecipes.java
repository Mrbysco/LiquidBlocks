package com.mrbysco.liquidblocks.init.recipes;

import com.mrbysco.liquidblocks.LiquidBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class LiquidRecipes {
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, LiquidBlocks.MOD_ID);

	public static final Supplier<ShapedNoRemainderRecipe.Serializer> SHAPED_NO_REMAINDER_SERIALIZER = RECIPE_SERIALIZERS.register("shaped_no_remainder", ShapedNoRemainderRecipe.Serializer::new);
	public static final Supplier<ShapelessNoRemainderRecipe.Serializer> SHAPELESS_NO_REMAINDER_SERIALIZER = RECIPE_SERIALIZERS.register("shapeless_no_remainder", ShapelessNoRemainderRecipe.Serializer::new);
}
