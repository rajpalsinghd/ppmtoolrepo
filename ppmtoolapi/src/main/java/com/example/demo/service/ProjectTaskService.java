package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Backlog;
import com.example.demo.domain.Project;
import com.example.demo.domain.ProjectTask;
import com.example.demo.exception.ProjectNotFoundException;
import com.example.demo.repositories.BacklogRepository;
import com.example.demo.repositories.ProjectRepository;
import com.example.demo.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	@Autowired
	private BacklogRepository backlogRepository;
	
	public ProjectTask addProjectTask(String projectIdentifier,ProjectTask projectTask)
	{
		try
		{
		Backlog backlog=backlogRepository.findByProjectIdentifier(projectIdentifier);
		projectTask.setBacklog(backlog);
		if(projectTask.getPriority()==null)
		{
			projectTask.setPriority(3);
		}
		if(projectTask.getStatus()=="" || projectTask.getStatus()==null)
		{
			projectTask.setStatus("ToDo");
		}
		projectTask.setProjectIdentifer(projectIdentifier);
		int projectSequence=backlog.getPTSequence();
		++projectSequence;
		projectTask.setProjectSequence(projectIdentifier+"-"+projectSequence);
		ProjectTask projectTask1;
		backlog.setPTSequence(projectSequence);
		projectTask1=projectTaskRepository.save(projectTask);
		return projectTask1;
		}
		catch(Exception exception)
		{
			throw new ProjectNotFoundException("Project Not found");
		}
		}

	public Iterable<ProjectTask>findBacklogById(String id)
	{
		Project project=projectRepository.findByProjectIdentifier(id);
		if(project==null)
		{
			throw new ProjectNotFoundException("Project with id:"+id+" does not exists");
		}
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
	}

	public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id) {
		ProjectTask projectTask=projectTaskRepository.findByProjectSequence(pt_id);
		return projectTask;
	}

}
