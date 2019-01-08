9 - promotion

满减、单品限时、套装、赠品、满赠、定金预购

First look at 单品限时 case

Single Item Limited Time:

Promotion Model:
* ItemId
* Begin Time
* End Time
* Type
* New Price

Promotion Service:
* Find all active promotions at this time
* Allowing only one promotion exist at the same time

Buy Service rely on Promo Service to calculate total discount (expand later)

Order Model add:
* item Price (necessary for totalFee Calculation later)
* item title (redundant for display)
* item imgurl (redundant for display)
* discount fee (total discount split to single suborder)