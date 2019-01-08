#8 - CartBuy

Modify Order Entity to support multiple line items in an order.
Create SubOrder Entity
Order become the aggregate root, remove itemId, quantity etc. from Order and send to SubOrder
Order for now keeps track of BuyerId

Modify OrderDO to include subOrders (OrderDO[]).
Note this is another case of Object-Relational impedance mismatch.
All Orders and SubOrders are stored in the same table, not two tables.
This is beneficial performance-wise since only one query is required.

Add isMain, isSub and parentId in the data model (Not Entity Model).

Modify Repository to handle save and read

Notice now that OrderMapper is only reading and loading single OrderDO, this could be Order or SubOrder
OrderRepository on the other hand only read and write Order, NOT SubOrder. (Each Repository should only handle aggregate roots)
OrderRepo method should have transaction on. In this case since it's all in the same table, its very easy and guaranteed.

Now Add support for buying multiple items, must be from cart:


Entity (no suffix) vs Data Object (DO) vs Data Transfer Object (DTO) vs View Model (VM):

Entity: Business Data + Logic
DO: Representation for persistence. Repository handles converting between Entity and DO
DTO: For Combining requests and complex data structures, avoiding multiple calls. typically as input to service.
VM: For data binding between controller and view, typically a form-backing bean.

Notice that DO belongs to the persistence (infrastructure) layer only, VM belongs to the controller/view layer only.
DTO is potentially cross-cutting, but only as inputs.

Entity neither have knowledge of the DO nor the VM and DTO.


Cart to ConfirmOrder process:

* Cart is loaded
* Cart convert to List<CartItemVM>
* create a CartOrderVM that wraps List<CartItemVM>
* CartOrderVM is the form-backing bean of /cart html

* After posting, get back CartOrderVM in /confirmOrder as input
* using CartOrderVM, create an CreateOrderDTO
* Call BuyService using CreateOrderDTO, get back an Order Entity
* Convert Order to ConfirmOrderVM
* Display ConfirmOrderVM in /confirmOrder html

