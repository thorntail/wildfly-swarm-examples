package org.wildfly.swarm.examples.jdd;


import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by rafaelszp on 9/8/16.
 */

@XmlRootElement
@Entity
@Table(name="PERSON")
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="PERSON_DOCUMENT_IDENTIFICATION",nullable = false,unique = true,length = 32)
    private String documentId;

    @Column(name="PERSON_NAME",nullable = false,length = 500)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
