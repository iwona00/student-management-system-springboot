package net.javaguides.sms.controller;

import net.javaguides.sms.entity.Classes;
import net.javaguides.sms.repository.ClassRepository;
import net.javaguides.sms.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import net.javaguides.sms.entity.Student;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ClassController {

    private ClassRepository classRepository;
    private StudentRepository studentRepository;

    public ClassController(ClassRepository classRepository, StudentRepository studentRepository) {
        this.classRepository = classRepository;
        this.studentRepository = studentRepository;
    }


    @GetMapping("/classes")
    public String listClasses(Model model) {
        model.addAttribute("classes", classRepository.findAll());
        return "classes";
    }

    @GetMapping("/classes/studentsList/{id}")
    public String showStudents(@PathVariable Long id, Model model) {
        String name = classRepository.getOne(id).getName();
        List<Student> classNames = new ArrayList<>();
        List<Student> all = studentRepository.findAll();
        for (int i = 0; i < all.size(); i++) {
            if(all.get(i).className.equals(name)) {
                classNames.add(all.get(i));

            }
        }
        model.addAttribute("s", classNames);

        return "showStudents";
    }

    @GetMapping("/classes/new")
    public String createClassesForm(Model model) {


        Classes cl = new Classes();
        model.addAttribute("cl", cl);
        return "create_class";

    }

    @PostMapping("/classes")
    public String saveClass(@ModelAttribute("cl") Classes cl) {
        List<Classes> list = classRepository.findAll();
        int size = list.size();
        if (size!=0) {
            for (int i = 0; i < size; i++) {
                if (list.get(i).getName().equals(cl.getName())) {
                    break;
                }
                if (i == size - 1) {
                    this.classRepository.save(cl);
                }
            }
        }
        else {
            this.classRepository.save(cl);
        }

        return "redirect:/classes";
    }

    @GetMapping("/classes/edit/{id}")
    public String editClassForm(@PathVariable Long id, Model model) {
        model.addAttribute("cl", classRepository.getOne(id));
        return "edit_class";
    }

    @PostMapping("/classes/{id}")
    public String updateClass(@PathVariable Long id,
                                @ModelAttribute("cl") Classes classes,
                                Model model) {


        Classes existingClass = classRepository.getOne(id);
        classRepository.delete(existingClass);
        Classes updatedClass = new Classes(classes.getName());
        updatedClass.setName(classes.getName());

        List<Classes> list = classRepository.findAll();
        int size = list.size();
            for (int i = 0; i < size; i++) {
                if (list.get(i).getName().equals(updatedClass.getName())) {
                    break;
                }
                if (i == size - 1) {
                    this.classRepository.save(updatedClass);
                }
            }

        return "redirect:/classes";
    }


    @GetMapping("/classes/{id}")
    public String deleteClass(@PathVariable Long id) {
        Classes c = classRepository.getOne(id);
        String name = c.getName();
        List<Student> list = studentRepository.findAll();
        for (int i=0; i < list.size(); i++) {
            if (list.get(i).className.equals(name)) {
                list.get(i).setClassName("");
            }
        }

        this.classRepository.deleteById(id);
        return "redirect:/classes";
    }

}
