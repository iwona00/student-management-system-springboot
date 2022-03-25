package net.javaguides.sms.entity;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "classes")
public class Classes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long classes_id;

    @Column(name = "name", nullable = false)
    private String name;

    public Classes() {

    }

    public Classes(String name) {
        super();
        this.name = name;
    }

    public Long getClasses_id() {
        return classes_id;
    }

    public void setClasses_id(Long classes_id) {
        this.classes_id = classes_id;
    }

    public String getName() {
        return name;
    }
    public void setName(String firstName) {
        this.name = firstName;
    }


    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classes classes = (Classes) o;
        return Objects.equals(classes_id, classes.classes_id) && Objects.equals(name, classes.name);
    }


}
