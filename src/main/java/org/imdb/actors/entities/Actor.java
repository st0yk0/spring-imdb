package org.imdb.actors.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import static org.imdb.constants.Constants.UUID_SIZE;

@Data
@Entity
@Table(uniqueConstraints= {
        @UniqueConstraint(columnNames = {"first_name", "last_name"})
}, name = "actors")
public class Actor {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = UUID_SIZE)
    private String id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name" ,  nullable = false)
    private String lastName;

}
