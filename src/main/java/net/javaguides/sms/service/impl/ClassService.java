package net.javaguides.sms.service.impl;

import net.javaguides.sms.entity.Classes;
import net.javaguides.sms.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClassService {

    @Autowired
    private ClassRepository repo;

    @Transactional
    public Optional<Classes> update(long classesId, Classes source) {
        return repo.findById(classesId).map(target -> {
            target.setName(source.getName());
            target.setClasses_id(source.getClasses_id());

            List<Classes> list = repo.findAll();
            int size = list.size();
            if (size!=0) {
                for (int i = 0; i < size; i++) {
                    if (list.get(i).getName().equals(source.getName())) {
                        break;
                    }
                    if (i == size - 1) {
                        target.setName(source.getName());
                        target.setClasses_id(source.getClasses_id());
                        this.repo.save(source);
                        return target;
                    }
                }
            }
            else {
                target.setName(source.getName());
                target.setClasses_id(source.getClasses_id());
                this.repo.save(source);
                return target;
            }

            return null;

        });
    }
}