package freedays.controller;

import freedays.timesheet.Project;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "projects", formBackingObject = Project.class)
@RequestMapping("/projects")
@Controller
public class ProjectController {
}
