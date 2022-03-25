package net.javaguides.sms.repository;

import net.javaguides.sms.entity.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface ClassRepository extends JpaRepository<Classes, Long>{





}
