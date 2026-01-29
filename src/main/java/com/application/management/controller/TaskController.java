package com.application.management.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.management.model.Project;
import com.application.management.model.Task;
import com.application.management.model.User;


@Controller
@RequestMapping("/projects/{projectId}/tasks")
public class TaskController {

    @GetMapping
    public String getTasks(@PathVariable Long projectId, Model model) {

        // Static Project for demo
        Project project = new Project();
        project.setId(projectId);
        project.setName("Project Alpha"); // Replace with actual project lookup if DB ready
        model.addAttribute("project", project);

        // Static Users for demo
        User alice = new User();
        alice.setId(1L);
        alice.setFirstName("Alice");
        alice.setLastName("Smith");

        User bob = new User();
        bob.setId(2L);
        bob.setFirstName("Bob");
        bob.setLastName("Johnson");

        User charlie = new User();
        charlie.setId(3L);
        charlie.setFirstName("Charlie");
        charlie.setLastName("Brown");

        List<User> projectUsers = Arrays.asList(alice, bob, charlie);
        model.addAttribute("projectUsers", projectUsers);

        // Static tasks
        List<Task> taskList = Arrays.asList(
                new Task(101L, "Design Homepage", "PENDING", LocalDate.now().plusDays(5), alice, project),
                new Task(102L, "Setup Backend", "IN_PROGRESS", LocalDate.now().plusDays(10), bob, project),
                new Task(103L, "Write Unit Tests", "COMPLETED", LocalDate.now().plusDays(3), charlie, project)
        );
        model.addAttribute("taskList", taskList);

        model.addAttribute("newTask", new Task()); // for creation form

        return "usersProjectTasks"; // Thymeleaf page tasks.html
    }

    // Dummy save
    @PostMapping("/save")
    public String saveTask(@PathVariable Long projectId, @ModelAttribute Task task) {
        System.out.println("Saved Task: " + task.getTitle() + " for project: " + projectId);
        return "redirect:/projects/" + projectId + "/tasks";
    }

    // Dummy update
    @PostMapping("/update")
    public String updateTask(@PathVariable Long projectId, @ModelAttribute Task task) {
        System.out.println("Updated Task: " + task.getTitle() + " for project: " + projectId);
        return "redirect:/projects/" + projectId + "/tasks";
    }

    // Dummy delete
    @GetMapping("/delete/{taskId}")
    public String deleteTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        System.out.println("Deleted Task: " + taskId + " from project: " + projectId);
        return "redirect:/projects/" + projectId + "/tasks";
    }

    // Dummy fetch for edit modal
    @GetMapping("/taskDetails")
    @ResponseBody
    public Task getTaskDetails(@RequestParam Long taskId) {
        User alice = new User();
        alice.setId(1L);
        alice.setFirstName("Alice");
        alice.setLastName("Smith");

        Project project = new Project();
        project.setId(1L);
        project.setName("Project Alpha");

        return new Task(taskId, "Sample Task", "PENDING", LocalDate.now().plusDays(7), alice, project);
    }
}