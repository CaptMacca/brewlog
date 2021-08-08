package brews;

import brews.domain.Recipe;

public class MockRecipe {

    public static Recipe getRecipe() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("My Recipe");

        return recipe;
    }
}
