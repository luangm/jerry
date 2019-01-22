package io.luan.jerry.order.repository.impl;

import io.luan.jerry.common.domain.EntityState;
import io.luan.jerry.order.data.OrderDO;
import io.luan.jerry.order.data.OrderMapper;
import io.luan.jerry.order.domain.Order;
import io.luan.jerry.order.domain.SubOrder;
import io.luan.jerry.order.factory.OrderFactory;
import io.luan.jerry.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderFactory orderFactory;

    private final OrderMapper orderMapper;

    @Autowired
    public OrderRepositoryImpl(OrderMapper orderMapper, OrderFactory orderFactory) {
        this.orderMapper = orderMapper;
        this.orderFactory = orderFactory;
    }

    @Override
    public boolean delete(Order order) {
        var orderDO = new OrderDO(order);
        if (orderDO.getId() != null) {
            int count = orderMapper.delete(orderDO);
            return count > 0;
        }
        return false;
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        var orderDOs = orderMapper.findAll();

        List<OrderDO> mainOrders = new ArrayList<>();
        Map<Long, OrderDO> mainOrderMap = new HashMap<>();

        for (OrderDO orderDO : orderDOs) {
            if (orderDO.getIsMain()) {
                mainOrders.add(orderDO);
                mainOrderMap.put(orderDO.getId(), orderDO);
            }
        }

        for (OrderDO orderDO: orderDOs) {
            if (orderDO.getIsSub()) {
                var mainOrder = mainOrderMap.get(orderDO.getParentId());
                mainOrder.getSubOrders().add(orderDO);
            }
        }

        for (var orderDO: mainOrders) {
            orders.add(orderFactory.load(orderDO));
        }

        return orders;
    }

    @Override
    public Order findById(Long id) {
        var orderDOList = orderMapper.findAllByParentId(id);
        Optional<OrderDO> first = orderDOList.stream().filter(orderDO -> orderDO.getId().equals(id)).findFirst();
        if (first.isEmpty()) {
            return null;
        }

        OrderDO mainOrderDO = first.get();
        if (orderDOList.size() == 1) {
            mainOrderDO.getSubOrders().add(mainOrderDO);
        } else {
            for (var orderDO : orderDOList) {
                if (!orderDO.getId().equals(id)) {
                    mainOrderDO.getSubOrders().add(orderDO);
                }
            }
        }
        return orderFactory.load(mainOrderDO);

    }

    @Override
    public void save(Order order) {
        switch (order.getState()) {
            case Added:
            case Detached:
                this.insert(order);
                break;
            case Modified:
                this.update(order);
                break;
        }
        order.setState(EntityState.Unchanged);
    }

    private void insert(Order order) {
        var orderDO = new OrderDO(order);

        // insert main order
        orderMapper.insert(orderDO);
        order.setId(orderDO.getId());

        if (order.getSubOrders().size() == 1) {
            var sub = order.getSubOrders().get(0);
            sub.setId(orderDO.getId());
            sub.setParentId(orderDO.getId());
        } else {
            var subOrders = orderDO.getSubOrders();
            for (int i = 0; i < subOrders.size(); i++) {
                OrderDO subOrderDO = subOrders.get(i);
                subOrderDO.setParentId(order.getId());
                orderMapper.insert(subOrderDO);

                SubOrder subOrder = order.getSubOrders().get(i);
                subOrder.setId(subOrderDO.getId());
                subOrder.setParentId(order.getId());
            }
        }

        // update main parentId
        orderDO.setParentId(orderDO.getId());
        orderMapper.update(orderDO);
    }

    private void update(Order order) {
        var orderDO = new OrderDO(order);
        orderMapper.update(orderDO);
    }
}
