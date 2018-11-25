package ua.kanban.ppmtool.web;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.kanban.ppmtool.domain.Project;
import ua.kanban.ppmtool.services.ProjectService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/project")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProjectController {

    private ProjectService projectService;
    private MapValidationErrorService mapValidationErrorService;

    @RequestMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody final Project project, final BindingResult result){
        final ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        final Project project1 = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<>(project1, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId){

        Project project = projectService.findByProjectIdentifier(projectId);

            return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects() {
        return projectService.findAllProjects();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable String id){
        projectService.deleteByIdentifier(id);
        return new ResponseEntity<String>("Project with ID '" + id + "' was deleted", HttpStatus.OK);
    }
}
