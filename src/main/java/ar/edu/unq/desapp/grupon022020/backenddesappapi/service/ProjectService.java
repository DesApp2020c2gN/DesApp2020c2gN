package ar.edu.unq.desapp.grupon022020.backenddesappapi.service;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Project;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository repository;

    @Transactional
    public Project save(Project project) {
        return this.repository.save(project);
    }

    public Project findByID(String name) {
        return this.repository.findById(name).get();
    }

    public List<Project> findAll() {
        return this.repository.findAll();
    }
}
