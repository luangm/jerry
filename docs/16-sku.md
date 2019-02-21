16 - SKU

SKU = Stock Keeping Unit, meaning SKU is bound with an inventory.

SKUs are sub-items, with predefined Sales PV pairs

SKU have an ID, should be Entity.

Is SKU an Aggregate Root? NO!
SKU only references itemId, image (if available), price, inventory, sales PV pairs

All changes to SKU should be done at the item level.



Order changes:
* Add Attributes
* SKU ID is in attributes (Why? Because it is not necessary to go sku level detail, also ** SKU is not Aggregate Root! **).
