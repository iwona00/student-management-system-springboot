package net.javaguides.sms.controller;

import net.javaguides.sms.entity.Classes;
import net.javaguides.sms.repository.ClassRepository;
import net.javaguides.sms.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import net.javaguides.sms.entity.Student;


import java.util.List;

@Controller
public class StudentController {
	
	private final StudentRepository studentRepository;
	private final ClassRepository classRepository;

	public StudentController(StudentRepository studentRepository, ClassRepository classRepository ) {
		this.studentRepository = studentRepository;
		this.classRepository = classRepository;
	}
	

	@GetMapping("/students")
	public String listStudents(Model model) {
		model.addAttribute("students", studentRepository.findAll());
		return "students";
	}


	@GetMapping("/students/new")
	public String createStudentForm(Model model) {
		

		Student student = new Student();
		//student.setClassName("");
		model.addAttribute("student", student);
		//List<Classes> classes = classRepository.findAll();
		//model.addAttribute("cl", classes);
		return "create_student";
		
	}
	
	@PostMapping("/students")
	public String saveStudent(@ModelAttribute("student") Student student) {
		Classes cl = new Classes(student.getClassName());
		List<Classes> list = classRepository.findAll();
		int size = list.size();
		for (int i = 0; i < size; i++) {
			if (list.get(i).getName().equals(student.getClassName())) {
				break;
			}
			if (i == size - 1) {
				this.classRepository.save(cl);
			}
		}
		this.studentRepository.save(student);
		return "redirect:/students";
	}
	
	@GetMapping("/students/edit/{id}")
	public String editStudentForm(@PathVariable Long id, Model model) {
		model.addAttribute("student", studentRepository.getOne(id));
		return "edit_student";
	}

	@PostMapping("/students/{id}")
	public String updateStudent(@PathVariable Long id,
			@ModelAttribute("student") Student student,
			Model model) {


		Student existingStudent = studentRepository.getOne(id);
		existingStudent.setStudent_id(id);
		existingStudent.setFirstName(student.getFirstName());
		existingStudent.setLastName(student.getLastName());
		existingStudent.setPesel(student.getPesel());
		existingStudent.setClassName(student.getClassName());


		Classes cl = new Classes(student.getClassName());
		List<Classes> list = classRepository.findAll();
		int size = list.size();
		for (int i = 0; i < size; i++) {
			if (list.get(i).getName().equals(student.getClassName())) {
				break;
			}
			if (i == size - 1) {
				this.classRepository.save(cl);
			}
		}

		this.studentRepository.save(existingStudent);
		return "redirect:/students";		
	}

	
	@GetMapping("/students/{id}")
	public String deleteStudent(@PathVariable Long id) {
		this.studentRepository.deleteById(id);
		return "redirect:/students";
	}
	
}
