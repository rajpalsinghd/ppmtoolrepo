package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.ProjectTask;
import com.example.demo.domain.Backlog;
import com.example.demo.repositories.BacklogRepository;
import com.example.demo.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	@Autowired
	private BacklogRepository backlogRepository;
	
	public ProjectTask addProjectTask(String projectIdentifier,ProjectTask projectTask)
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
		backlog.setPTSequence(projectSequence);
		return projectTaskRepository.save(projectTask);
		
	}
}
