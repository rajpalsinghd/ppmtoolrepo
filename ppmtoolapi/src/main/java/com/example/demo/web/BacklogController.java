package com.example.demo.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Project;
import com.example.demo.domain.ProjectTask;
import com.example.demo.service.MapValidationErrorService;
import com.example.demo.service.ProjectTaskService;

@RestController
@RequestMapping("/api/backlog")
public class BacklogController {

	@Autowired
	private ProjectTaskService projectTaskService;
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	@PostMapping("/{backlog_id}")
	public ResponseEntity<?>addPTToBacklog(@Valid @RequestBody ProjectTask projectTask,BindingResult bindingResult, @PathVariable String backlog_id)
	{
		ResponseEntity<?>errorMap=mapValidationErrorService.mapValidationError(bindingResult);
		if(errorMap!=null)return errorMap;
		ProjectTask projectTask1=projectTaskService.addProjectTask(backlog_id, projectTask);
		
	return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);
	}
	
	@GetMapping("/{backlogId}")
	public Iterable<ProjectTask>getProjectBacklog(@PathVariable String backlogId)
	{
		
		return projectTaskService.findBacklogById(backlogId);
	}
	@GetMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<?>getProjectTask(@PathVariable String backlog_id,@PathVariable String pt_id)
	{
		ProjectTask projectTask=projectTaskService.findPTByProjectSequence(backlog_id,pt_id);
		return new ResponseEntity<ProjectTask>(projectTask,HttpStatus.OK);
	}
}
