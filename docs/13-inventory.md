13 - inventory

An inventory tracks the inventory of an item/sku at a certain Location or Channel (Not yet defined).

Domain Model:
* ID (InventoryID)
* ItemId
* Available // Current Quantity that can be sold
* Withheld // frozen
* Total // total


Key concepts in inventory management:
* Inventory Adjustments
* Inventory Records
* Freeze / Unfreeze （下单后冻结 / 支付超时后解冻，防止超卖）
* Timing (下单减库存 / 付款减库存（超卖）)


Entity Actions:
* Query // 查询
* Create // 创建
* Allocate // 分配
* Modify // 修改
* Freeze // 冻结：拍减时Freeze，支付后Release并且Reduce
* Release // 释放：释放冻结的
* Reduce // 扣减
* Replenish // 回补


Where are inventory service used?
* Sell: Depend on Category, Item, Inventory
* Buy: Depend on Item, Inventory, Order, User, DeliveryAddress


乐观锁，使用version字段