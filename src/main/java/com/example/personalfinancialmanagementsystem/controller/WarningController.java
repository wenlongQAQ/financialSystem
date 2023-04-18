package com.example.personalfinancialmanagementsystem.controller;

import com.example.personalfinancialmanagementsystem.common.Code;
import com.example.personalfinancialmanagementsystem.common.R;
import com.example.personalfinancialmanagementsystem.service.order.OrderService;
import com.example.personalfinancialmanagementsystem.service.user.UserWarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/warning")
@RestController
public class WarningController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserWarningService userWarningService;

    @GetMapping("/orderWarning")
    public R getOrderWarning(@RequestParam("userId") String userId, HttpServletRequest request){
        String sessionUserId = (String) request.getSession().getAttribute("userId");
        if (userId == null || !userId.equals(sessionUserId))
            return R.sendMessage("","请检查你的登录状态", Code.USER_LOGIN_OUT);
        List<String> warningList = orderService.getWarningList(userId);
        if (warningList.isEmpty()){
            return R.sendMessage("你今年的消费情况良好,请继续保持!","",Code.EDIT_SUCCESS);
        }else{

            return R.sendMessage("你今年在" + warningList.toString() +"这几个月的收入小于支出,请注意哦","",Code.EDIT_ERROR);
        }

    }
}
