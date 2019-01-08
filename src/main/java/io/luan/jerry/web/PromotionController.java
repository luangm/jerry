package io.luan.jerry.web;

import io.luan.jerry.item.service.ItemService;
import io.luan.jerry.promotion.dto.PublishPromotionDTO;
import io.luan.jerry.promotion.service.PromotionService;
import io.luan.jerry.promotion.vm.PromotionVM;
import io.luan.jerry.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@Controller
public class PromotionController {

    private final PromotionService promotionService;

    private final ItemService itemService;

    @Autowired
    public PromotionController(PromotionService promotionService, ItemService itemService) {
        this.promotionService = promotionService;
        this.itemService = itemService;
    }

    @GetMapping("/promotion")
    public ModelAndView promo(@RequestParam("itemId") Long itemId) {
        var user = SecurityUtils.getCurrentUser();
        var item = itemService.findById(itemId);

        var vm = new PromotionVM();
        vm.setItemId(itemId);
        vm.setStartTime(LocalDateTime.of(2019, 1, 1, 0, 0).toString());
        vm.setEndTime(LocalDateTime.of(2019, 12, 31, 0, 0).toString());
        var mav = new ModelAndView("promotion");
        mav.addObject("item", item);
        mav.addObject("promotion", vm);
        return mav;
    }

    @PostMapping("/promotion")
    public ModelAndView publish(@ModelAttribute PromotionVM request) {
        var user = SecurityUtils.getCurrentUser();

        var mav = new ModelAndView("promotionSuccess");
        var publishDto = new PublishPromotionDTO();
        publishDto.setItemId(request.getItemId());
        publishDto.setNewPrice(request.getNewPrice());
        publishDto.setStartTime(LocalDateTime.parse(request.getStartTime()));
        publishDto.setEndTime(LocalDateTime.parse(request.getEndTime()));

        var result = promotionService.publish(publishDto);
        var resultVM = new PromotionVM(result);

        mav.addObject("promotion", resultVM);
        return mav;
    }

}
