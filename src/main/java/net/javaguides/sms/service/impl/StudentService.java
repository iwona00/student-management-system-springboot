package net.javaguides.sms.service.impl;

import net.javaguides.sms.entity.Classes;
import net.javaguides.sms.entity.Student;
import net.javaguides.sms.repository.ClassRepository;
import net.javaguides.sms.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repo;

    @Autowired
    ClassRepository classRepo;


    @Transactional
    public Optional<Student> update(long studentId, Student source) {
        return repo.findById(studentId).map(target -> {
            target.setFirstName(source.getFirstName());
            target.setLastName(source.getLastName());
            target.setEmail(source.getEmail());
            target.setPesel(source.getPesel());
            target.setStudent_id(source.getStudent_id());
            target.setClassName(source.getClassName());

            Classes cl = new Classes(source.getClassName());
            List<Classes> list = classRepo.findAll();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                if (list.get(i).getName().equals(source.getClassName())) {
                    break;
                }
                if (i == size - 1) {
                    this.classRepo.save(cl);
                }
            }
            this.repo.save(source);


            return target;
        });
    }
}