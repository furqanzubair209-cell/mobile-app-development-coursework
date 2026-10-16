package com.example.foodapp.data;

import com.example.foodapp.R;
import com.example.foodapp.model.FoodItem;
import com.example.foodapp.model.Order;
import com.example.foodapp.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class SampleData {

    private static List<Restaurant> restaurants;
    private static List<Order> orders;

    public static List<Restaurant> getRestaurants() {
        if (restaurants == null) {
            restaurants = new ArrayList<>();

            // Restaurant 1: Burger Barn
            List<FoodItem> r1Foods = new ArrayList<>();
            r1Foods.add(new FoodItem("f101", "r1", "Double Beef Burger", "Juicy double beef patty with melted cheddar cheese, pickles, and signature sauce.", 8.99, R.drawable.food_burger_double, "Burgers"));
            r1Foods.add(new FoodItem("f102", "r1", "Crispy Chicken Burger", "Crispy fried chicken breast, lettuce, and creamy mayo on a toasted bun.", 7.49, R.drawable.food_burger_crispy_chicken, "Burgers"));
            r1Foods.add(new FoodItem("f103", "r1", "Classic Cheeseburger", "Single beef patty with American cheese, lettuce, tomato, and special burger sauce.", 6.99, R.drawable.food_burger_cheeseburger, "Burgers"));
            r1Foods.add(new FoodItem("f104", "r1", "Loaded Golden Fries", "Crispy French fries topped with melted cheese sauce and crispy bacon bits.", 3.99, R.drawable.food_french_fries, "Sides"));
            restaurants.add(new Restaurant("r1", "Burger Barn", "Burgers • Fast Food • American", 4.8, "15-25 min", 1.99, "$$", R.drawable.rest_burger_barn, "Home of the juiciest handcrafted burgers and golden crispy fries.", r1Foods));

            // Restaurant 2: Pizza Palace
            List<FoodItem> r2Foods = new ArrayList<>();
            r2Foods.add(new FoodItem("f201", "r2", "Pepperoni Feast Pizza", "Classic tomato sauce, fresh mozzarella, and generous slices of savory pepperoni.", 12.99, R.drawable.food_pizza_pepperoni, "Pizza"));
            r2Foods.add(new FoodItem("f202", "r2", "Margherita Special", "Fresh basil, vine-ripened tomatoes, extra virgin olive oil, and buffalo mozzarella.", 10.99, R.drawable.food_pizza_margherita, "Pizza"));
            r2Foods.add(new FoodItem("f203", "r2", "BBQ Chicken Pizza", "Grilled chicken, smoky BBQ sauce, red onions, and fresh cilantro.", 13.99, R.drawable.food_pizza_bbq_chicken, "Pizza"));
            r2Foods.add(new FoodItem("f204", "r2", "Garlic Butter Breadsticks", "Warm oven-baked breadsticks brushed with garlic butter and parmesan.", 4.49, R.drawable.food_garlic_bread, "Sides"));
            restaurants.add(new Restaurant("r2", "Pizza Palace", "Italian • Pizza • Pasta", 4.6, "25-35 min", 2.49, "$$", R.drawable.rest_pizza_palace, "Authentic wood-fired pizzas and homemade Italian breadsticks.", r2Foods));

            // Restaurant 3: Tokyo Sushi Bar
            List<FoodItem> r3Foods = new ArrayList<>();
            r3Foods.add(new FoodItem("f301", "r3", "Salmon Avocado Roll", "Fresh Atlantic salmon, creamy avocado, cucumber, and sesame seeds.", 9.99, R.drawable.food_sushi_salmon, "Sushi"));
            r3Foods.add(new FoodItem("f302", "r3", "Dragon Roll Supreme", "Eel, crab stick, cucumber topped with avocado slices and unagi sauce.", 13.50, R.drawable.food_sushi_roll, "Sushi"));
            r3Foods.add(new FoodItem("f303", "r3", "Teriyaki Chicken Roll", "Glazed teriyaki chicken with cucumber and crunchy tempura flakes.", 11.25, R.drawable.food_sushi_teriyaki, "Sushi"));
            r3Foods.add(new FoodItem("f304", "r3", "Traditional Miso Soup", "Warm Japanese soybean broth with tofu, seaweed, and green onions.", 3.99, R.drawable.food_miso_soup, "Starters"));
            restaurants.add(new Restaurant("r3", "Tokyo Sushi Bar", "Japanese • Sushi • Asian", 4.9, "20-30 min", 2.99, "$$$", R.drawable.rest_tokyo_sushi, "Fresh sashimi, handcrafted sushi rolls, and traditional Japanese soup.", r3Foods));

            // Restaurant 4: Noodle House
            List<FoodItem> r4Foods = new ArrayList<>();
            r4Foods.add(new FoodItem("f401", "r4", "Spicy Tonkotsu Ramen", "Rich pork bone broth, tender chashu pork, soft-boiled egg, and handmade ramen noodles.", 11.49, R.drawable.food_noodles_ramen, "Noodles"));
            r4Foods.add(new FoodItem("f402", "r4", "Pad Thai Noodles", "Stir-fried rice noodles with eggs, peanuts, bean sprouts, and fresh lime.", 10.25, R.drawable.food_noodles_pad_thai, "Noodles"));
            r4Foods.add(new FoodItem("f403", "r4", "Special Chow Mein", "Wok-fried noodles with crisp vegetables and savory Asian sauce.", 10.99, R.drawable.food_noodles_chow_mein, "Noodles"));
            r4Foods.add(new FoodItem("f404", "r4", "Crispy Spring Rolls", "Handcrafted vegetable spring rolls with sweet chili dipping sauce.", 5.99, R.drawable.food_spring_rolls, "Appetizers"));
            restaurants.add(new Restaurant("r4", "Noodle House", "Asian • Noodles • Soup", 4.7, "15-30 min", 1.99, "$$", R.drawable.rest_noodle_house, "Hearty noodle bowls, authentic Pad Thai, and crispy spring rolls.", r4Foods));

            // Restaurant 5: Sweet Tooth Desserts
            List<FoodItem> r5Foods = new ArrayList<>();
            r5Foods.add(new FoodItem("f501", "r5", "Strawberry Cheesecake", "New York style cheesecake topped with fresh strawberry glaze and graham crust.", 5.49, R.drawable.food_dessert_cheesecake, "Cakes"));
            r5Foods.add(new FoodItem("f502", "r5", "Belgian Chocolate Cake", "Rich layered chocolate cake with dark chocolate ganache.", 6.99, R.drawable.food_dessert_chocolate_cake, "Cakes"));
            r5Foods.add(new FoodItem("f503", "r5", "Italian Tiramisu", "Classic Italian dessert with espresso-soaked ladyfingers and mascarpone cream.", 7.25, R.drawable.food_dessert_tiramisu, "Dessert"));
            r5Foods.add(new FoodItem("f504", "r5", "Vanilla Gelato Sundae", "Creamy vanilla ice cream topped with chocolate fudge and whipped cream.", 4.99, R.drawable.food_dessert_icecream, "Dessert"));
            restaurants.add(new Restaurant("r5", "Sweet Tooth Desserts", "Desserts • Bakery • Cafe", 4.8, "10-20 min", 1.49, "$", R.drawable.rest_sweet_tooth, "Decadent cakes, authentic tiramisu, and creamy gelato sundaes.", r5Foods));

            // Restaurant 6: Fresh Juice & Drinks Hub
            List<FoodItem> r6Foods = new ArrayList<>();
            r6Foods.add(new FoodItem("f601", "r6", "Iced Caramel Macchiato", "Espresso with cold milk, vanilla syrup, and caramel drizzle over ice.", 4.49, R.drawable.food_drink_iced_coffee, "Drinks"));
            r6Foods.add(new FoodItem("f602", "r6", "Mixed Berry Shake", "Blended fresh strawberries, blueberries, and creamy yogurt.", 5.25, R.drawable.food_drink_berry_shake, "Drinks"));
            r6Foods.add(new FoodItem("f603", "r6", "Tropical Mango Smoothie", "Refreshing tropical mango puree blended with ice and coconut milk.", 4.99, R.drawable.food_drink_mango_smoothie, "Drinks"));
            r6Foods.add(new FoodItem("f604", "r6", "Fresh Orange Juice", "100% freshly squeezed natural California oranges.", 3.99, R.drawable.food_drink_orange_juice, "Drinks"));
            restaurants.add(new Restaurant("r6", "Fresh Juice & Drinks Hub", "Beverages • Smoothies • Cafe", 4.9, "10-15 min", 0.99, "$", R.drawable.rest_fresh_juice, "Refreshing iced coffee, fruit smoothies, and freshly squeezed juices.", r6Foods));
        }
        return restaurants;
    }

    public static Restaurant getRestaurantById(String id) {
        for (Restaurant r : getRestaurants()) {
            if (r.getId().equals(id)) {
                return r;
            }
        }
        return getRestaurants().get(0);
    }

    public static List<FoodItem> getAllFoodItems() {
        List<FoodItem> allFoods = new ArrayList<>();
        for (Restaurant r : getRestaurants()) {
            allFoods.addAll(r.getFoodItems());
        }
        return allFoods;
    }

    public static List<Order> getSampleOrders() {
        if (orders == null) {
            orders = new ArrayList<>();
            orders.add(new Order("ORD-89412", "Burger Barn", "Today, 12:45 PM", "1x Double Beef Burger, 1x Loaded Golden Fries", 12.98, "In Transit"));
            orders.add(new Order("ORD-88231", "Pizza Palace", "Yesterday, 7:15 PM", "1x Pepperoni Feast Pizza, 1x Garlic Breadsticks", 17.48, "Delivered"));
            orders.add(new Order("ORD-87102", "Tokyo Sushi Bar", "12 Oct 2024", "2x Salmon Avocado Roll, 1x Miso Soup", 23.97, "Delivered"));
        }
        return orders;
    }

    public static void addOrder(Order newOrder) {
        getSampleOrders().add(0, newOrder);
    }
}
