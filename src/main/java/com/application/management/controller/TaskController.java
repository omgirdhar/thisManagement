package com.application.management.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.application.management.utils.Enums.TaskType;


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
        Task t1 = new Task(101L, "Design Homepage", "PENDING", LocalDate.now().plusDays(5), alice, project);
        t1.setTaskType(TaskType.TASK);

        Task t2 = new Task(102L, "Setup Backend", "IN_PROGRESS", LocalDate.now().plusDays(10), bob, project);
        t2.setTaskType(TaskType.TASK);

        Task t3 = new Task(103L, "Write Unit Tests", "COMPLETED", LocalDate.now().plusDays(3), charlie, project);
        t3.setTaskType(TaskType.TASK);

        // --- Sub-tasks ---
        Task st1 = new Task(201L, "Create Wireframe", "IN_PROGRESS", LocalDate.now().plusDays(2), bob, project);
        st1.setParentTask(t1);
        st1.setTaskType(TaskType.SUB_TASK);

        Task st2 = new Task(202L, "Pick Color Palette", "PENDING", LocalDate.now().plusDays(3), charlie, project);
        st2.setParentTask(t1);
        st2.setTaskType(TaskType.SUB_TASK);

        List<Task> allTasks = Arrays.asList(t1, t2, t3, st1, st2);

        // --- Build subTasksMap ---
        Map<Long, List<Task>> subTasksMap = new HashMap<>();
        for (Task task : allTasks) {
            if (task.getParentTask() != null) {
                Long parentId = task.getParentTask().getId();
                subTasksMap.computeIfAbsent(parentId, k -> new ArrayList<>()).add(task);
            }
        }
        model.addAttribute("subTasksMap", subTasksMap);

        // --- Flattened ordered list: top-level + their sub-tasks ---
        List<Task> orderedTasks = new ArrayList<>();
        for (Task task : allTasks) {
            if (task.getParentTask() == null) {
                orderedTasks.add(task);
                if (subTasksMap.containsKey(task.getId())) {
                    orderedTasks.addAll(subTasksMap.get(task.getId()));
                }
            }
        }
        model.addAttribute("taskList", orderedTasks);

        // --- New task for creation form ---
        Task newTask = new Task();
        newTask.setTaskType(TaskType.TASK);
        model.addAttribute("newTask", newTask);

        return "usersProjectTasks";
    }
    // Dummy save
    @PostMapping("/save")
    public String saveTask(@PathVariable Long projectId,
                           @RequestParam(required = false) Long parentTaskId,
                           @ModelAttribute Task task) {

        if (parentTaskId != null) {
            Task parent = new Task();
            parent.setId(parentTaskId); // dummy lookup for now
            task.setParentTask(parent);
        }

        System.out.println("====== CREATE TASK ======");
        System.out.println("Title: " + task.getTitle());
        System.out.println("Type: " + task.getTaskType());
        System.out.println("Parent ID: " +
            (task.getParentTask() != null ? task.getParentTask().getId() : null));
        System.out.println("=========================");

        return "redirect:/projects/" + projectId + "/tasks";
    }



    // Dummy update
    @PostMapping("/update")
    public String updateTask(@PathVariable Long projectId,
                             @ModelAttribute Task task) {

        System.out.println("====== UPDATE TASK ======");
        System.out.println("ID: " + task.getId());
        System.out.println("Type: " + task.getTaskType());
        System.out.println("Title: " + task.getTitle());
        System.out.println("=========================");

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
    
    @GetMapping("/details/{taskId}")
    public String taskDetails(@PathVariable Long projectId,
                              @PathVariable Long taskId,
                              Model model) {

        // For now, dummy task
        User alice = new User();
        alice.setId(1L);
        alice.setFirstName("Alice");
        alice.setLastName("Smith");

        Project project = new Project();
        project.setId(projectId);
        project.setName("Project Alpha");

        Task task = new Task(taskId, "Sample Task", TaskType.TASK.name(), 
                             LocalDate.now().plusDays(5), alice, project);

        model.addAttribute("task", task);
        model.addAttribute("projectId", projectId);

        return "taskDetails"; // new Thymeleaf page
    }

    @GetMapping("/createSubTask")
    public String createSubTask(@RequestParam Long parentId,
                                @RequestParam Long projectId,
                                Model model) {

        // Dummy parent task
        Task parent = new Task();
        parent.setId(parentId);
        parent.setTitle("Parent Task Title");

        Task newTask = new Task();
        newTask.setParentTask(parent);
        newTask.setTaskType(TaskType.SUB_TASK); // automatically set

        model.addAttribute("newTask", newTask);

        // Use the same create modal page if you want
        model.addAttribute("projectId", projectId);
        return "usersProjectTasks"; // or redirect to modal
    }

}