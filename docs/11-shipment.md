11 - shipment

shipment encompasses information about a package.

ShipmentStatus:
* Created -> Waiting to ship
* Shipped -> Waiting for delivery
* Received -> Received by user (manual or automatic) (End State - Success)
* Returned -> Material returned (End State - Failure)
* Canceled
* Exception

Shipment Model:
* ID
* Order ID
* ShipmentStatus
* Address
* Method: 平邮(mail)、快递(courier)、物流（freight)、虚拟(virtual)、线下(offline)
* BuyerId
* SellerId

Address as a ValueObject
Key for ValueObject:
* There is No ID
* DB simply store each field