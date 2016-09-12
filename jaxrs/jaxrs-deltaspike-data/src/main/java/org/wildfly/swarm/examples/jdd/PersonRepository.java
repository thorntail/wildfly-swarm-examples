package org.wildfly.swarm.examples.jdd;

import org.apache.deltaspike.data.api.EntityPersistenceRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by rafaelszp on 9/8/16.
 */
@Repository
public interface PersonRepository extends EntityPersistenceRepository<Person, Long> {

    Stream<Person> findByNameLikeAndDocumentId(String name, String documentId);
    Stream<Person> findByNameLike(String name);
    Stream<Person> findByDocumentId(String documentId);
    Stream<Person> findTop10OrderByName();
    Optional<Person> findBy(Long id);
}
