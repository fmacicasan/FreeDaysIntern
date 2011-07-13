package freedays.controller;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import freedays.domain.RegularUser;


@RooWebScaffold(path = "uadmin", formBackingObject = RegularUser.class)
@RequestMapping("/uadmin")
@Controller
public class UAdminController {

//	@RequestMapping(method = RequestMethod.GET)
//    public String search(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
//        if (page != null || size != null) {
//            int sizeNo = size == null ? 10 : size.intValue();
//            uiModel.addAttribute("regularusers", RegularUser.findRegularUserEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
//            float nrOfPages = (float) RegularUser.countRegularUsers() / sizeNo;
//            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
//        } else {
//            uiModel.addAttribute("regularusers", RegularUser.findAllRegularUsers());
//        }
//        addDateTimeFormatPatterns(uiModel);
//        return "uadmin/search";
//    }
}
