# 7 - Cart

To support multiple types of items in the same order, we need to create a shopping cartItem

There are 2 primary ways to create a cartItem:
* save in session
* save in db

Session cartItem is for when user is NOT logged in, we want the user to add whatever.

DB cartItem is when user is logged in.

For this chapter, we will look at db cartItem first.

#### Biz Logic
Add to Cart
Update Quantity
Remove From Cart

#### DDD
Cart is Aggregate Root
CartItem is Entity, but NOT independently modifiable. This is because there could be special rules in Cart affecting
multiple cartItems at the same time (such as grouping, promotions etc.)

CartRepository will save modifications to Cart at the same time in a single transaction (although each action could be
affecting only a single item).

Note: Under this design, it may not be very efficient to load all user's cart from db to memory each time. But this
allows stateless design, which is horizontally scalable. Later on cache could be used to reduce db load.

In order to simplify CartRepository.save(Cart), to avoid saving unmodified entities. There need to be a change tracking mechanism.

For now, implement Entity base class, which contains an EntityState.
EntityState keeps track of Entity's state, overriding the property setters, one can trigger Modified entityState.

