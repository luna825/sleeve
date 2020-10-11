package com.moonyue.sleeve.api.v1;


import com.moonyue.sleeve.core.LocalUser;
import com.moonyue.sleeve.core.annotations.ScopeLevel;
import com.moonyue.sleeve.dto.OrderDTO;
import com.moonyue.sleeve.logic.OrderChecker;
import com.moonyue.sleeve.service.OrderService;
import com.moonyue.sleeve.vo.OrderIdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/order")
@Validated
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("")
    @ScopeLevel
    public OrderIdVO placeOrder(@RequestBody OrderDTO orderDTO){
        Long uid = LocalUser.getUser().getId();
        OrderChecker orderChecker = orderService.isOk(uid, orderDTO);
        Long oid = this.orderService.placeOrder(uid, orderDTO, orderChecker);
        return new OrderIdVO(oid);
    }
}
