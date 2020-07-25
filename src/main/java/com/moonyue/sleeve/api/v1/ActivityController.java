package com.moonyue.sleeve.api.v1;

import com.moonyue.sleeve.core.exception.NotFoundException;
import com.moonyue.sleeve.model.Activity;
import com.moonyue.sleeve.service.ActivityService;
import com.moonyue.sleeve.vo.ActivityCouponVO;
import com.moonyue.sleeve.vo.ActivityPureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/activity")
@Validated
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping("/name/{name}")
    public ActivityPureVO getActivity(@PathVariable String name){
        Activity activity = this.activityService.getByName(name)
                .orElseThrow(()->new NotFoundException(40001));
        return new ActivityPureVO(activity);
    }

    @GetMapping("name/{name}/with_coupon")
    public ActivityCouponVO getActivityWithCoupon(@PathVariable String name){
        Activity activity = this.activityService.getByName(name)
                .orElseThrow(()->new NotFoundException(40001));
        return new ActivityCouponVO(activity);
    }
}
