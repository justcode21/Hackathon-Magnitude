## Hackathon - Magnitude Software
This project is build as part of a 24 hour hackathon at Magnitude Software, it is build in 4 phases

#### Problem Statement:
MagniFood - Magnitude Software has volunteered to "IT-Enable" the RGA Tech Park Food COurt Operations.
The entire problem statement(all 3 phases) does NOT involve building a UI. You will need to build REST APIs for the requirements. And write tests for the REST APIs.

#### Phase - 1 Requirements
```
1. A user should be able to sign-up on MagniFood

2. A user should be able to sign-in to MagniFood

3. A signed-in user should be able to view the list of Items on the Menu

4. A signed-in user should be able to order one or more items from the list of available items
```
#### Phase - 2 Requirements
```
1. A Cafeteria now consists of a group of Cafés and each Café has its own menu.

2. A Cafeteria User manages the cafeteria
      They can add/remove/edit/view a Café
      They should be able to see all orders per Café with their statutes
      They should be able to see all orders for the entire Cafeteria with their statutes

3. Each Café of the Cafeteria is managed by a Café User
      They should be able to add/remove/edit/view items from the menu of the Café
      They should be able to view the orders of their café with their statuses
```
#### Phase - 3 Requirements
```
1. Every café has a Store House which stores all the ingredients required to prepare the food items

2. Every food item on the menu has fixed number of ingredients that it uses along with the quantity
    E.g. Chicken Egg Wrap uses 100gms Dough, 50 gms Chicken, 1 Egg, 10 ml cooking oil, 1 Packet Secret Sauce
3. When an order comes into a café, they need to update the inventory in the store house

4. Café should be able to automatically mark items as out of stock as soon as the ingredients to make them is not available
```

#### Phase - 4 Requirements
```
1. The store house is now shared between all the cafés in the cafeteria. i.e. 1 store house is present in the cafeteria that is used by   all cafés

2. Ingredients in the store house can be added by the Cafeteria User at any time of the day.When ingredients are added into the Store House, all the Cafés should mark the items as in-stock wherever applicable. E.g. Cafe1 and Cafe2 have marked Chicken Egg Wrap as out of stock because eggs are not available in the Store House. As soon as the cafeteria user adds eggs into the Store House both Cafe1 and Cafe2 should be able to update the menu and mark Chicken Egg Wrap in stock. Do note that Cafe1 might have other items that are out of stock, and they should remain so until all ingredients required to prepare the item is not available in the Store House.

3. Allow for some random time ( < 1 minute or so), for the order to get processed and delivered. Order will now have following statuses    - Placed (~10% of time), Processing (~70% of time), Out for Delivery (~30% of time) and Delivered(finally). Allow for cancellation of    order when in Placed state.

4. Notifications: Implement notification of order status change to customers. Allow for notification of order placement to Cafe owners.    Allow for ingredient out of stock notification to Cafeteria owner.

5. Write the test cases on the lines of attached sheet
```
