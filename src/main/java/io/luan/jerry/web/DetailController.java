package io.luan.jerry.web;

import io.luan.jerry.common.utils.MapUtils;
import io.luan.jerry.detail.dto.ItemSku;
import io.luan.jerry.detail.service.DetailService;
import io.luan.jerry.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DetailController {

    private final DetailService detailService;

    @Autowired
    public DetailController(DetailService detailService) {
        this.detailService = detailService;
    }

    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("itemId") Long itemId,
                               @RequestParam(value = "skuId", required = false) Long skuId,
                               @RequestParam(value = "pv", required = false) String pv) {
        var user = SecurityUtils.getCurrentUser();

        var detailDto = detailService.getDetail(itemId, user.getId());
        var props = detailDto.getProperties();
        ItemSku selectedSku = null;
        if (skuId != null) {
            for (var sku : detailDto.getSkuList()) {
                if (sku.getId().equals(skuId)) {
                    selectedSku = sku;
                }
            }
        }

        for (var prop : props) {
            for (var value : prop.getValues()) {
                var pvMap = MapUtils.decodeLongLong(pv);
                var pvValue = pvMap.get(prop.getPropertyId());

                if (pvValue != null && pvValue.equals(value.getValueId())) {
                    value.setSelected(true);
                }

                pvMap.put(prop.getPropertyId(), value.getValueId());
                value.setPv(MapUtils.encode(pvMap));

                for (var sku : detailDto.getSkuList()) {
                    var skuPvMap = MapUtils.decodeLongLong(sku.getPvMap());
                    if (MapUtils.Equals(pvMap, skuPvMap)) {
                        value.setSkuId(sku.getId());
                    }
                }
            }
        }

        var mav = new ModelAndView("detail");
        mav.addObject("item", detailDto);
        mav.addObject("props", props);
        mav.addObject("skuId", skuId);
        mav.addObject("selectedSku", selectedSku);
        mav.addObject("loggedIn", user != null);
        return mav;
    }

}
