package fr.isen.attia.androidrestaurant

class FoodModel(val id: Int, val name: String) {
    companion object{
        private var lastItemId = 0

        fun createFoodList(numItems: Int) : ArrayList<FoodModel> {
            val foods = ArrayList<FoodModel>()
            for (i in 1..numItems){
                foods.add(FoodModel(++lastItemId, "Food " + ++lastItemId))
            }
            return foods
        }
    }
}