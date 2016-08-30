package org.wildfly.swarm.examples.jaxrs.cdi.domain.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Yoshimasa Tanabe
 */
@Data
@AllArgsConstructor
public class Employee implements Serializable {

    private Long id;

    private String name;

}
