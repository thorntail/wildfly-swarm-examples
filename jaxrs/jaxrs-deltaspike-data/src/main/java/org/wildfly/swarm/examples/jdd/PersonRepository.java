package org.wildfly.swarm.examples.jdd;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

/**
 * Created by rafaelszp on 9/8/16.
 */
@Repository
public interface PersonRepository extends EntityRepository<Person, Long> {

    List<Person> findByNameLikeAndDocumentId(String name, String documentId);
    List<Person> findByNameLike(String name);
    List<Person> findByDocumentId(String documentId);
}
