4 - sell

Who can sell item? what does an Item belong to? a store? a company? a person?
Is the buyer a company? a person?

B2C and C2C and B2B have different logic, we want to have a flexible model

solution:
* Item belongs to a user, but the user's role can be a single-man shop, a large corp or a sales-account

What if there is complex logic behind item, should this logic belong to item model? 
NO, violates single responsibility principle.
ItemService should handle item-only logic, reaching outside Bounded Context is bad.

Solution:
* Create a new service called "sell", SellService is responsible for the item creation logic. 
ItemService only handles underlying storage

SellService:
* publish (new Item)
* edit (old Item)

publish flow:
1. get user
2. generate id
3. select category
4. render fields that need to be filled.
5. validate rules
6. publish




