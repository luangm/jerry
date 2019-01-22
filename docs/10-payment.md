10 - payment

An order typically will have 3 components:
* Material (Items)
* Money
* Logistics

There are many payment processors out there (Such as Cash, Alipay/iPay, PayPal, UnionPay, CreditCard etc.)

The model should not be designed to take any particular form of payment, instead, there need to be a common entity
to handle payment model:


Payment Entity:
* ID (PaymentId)
* Order ID
* Outer ID (Payment ID from outside channel)
* Channel (Cash / Paypal / CC / Alipay etc.) String
* Payer ID
* Payer Name
* Payee ID
* Payee Name



Modifying Order structure:
* Add Seller ID to main order, because a Payment is only possible to a single seller.
* Enforce All suborders in an order to have the same sellerId
* This requires a change in BuyService to split orders.
* At this time, just ensure all suborders have the same sellerId



Order List All:
* SubQuery join with id


Fixed some urls with redirect, notice redirect 307 is for POST to POST
