package com.epam.esm.repository.tag;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.ITagRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class TagRepository implements ITagRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Tag> readAll() {
        return entityManager.createQuery("select tag from Tag tag", Tag.class).getResultList();
    }

    @Override
    public Tag read(final Integer tagId) {
        return entityManager.find(Tag.class, tagId);
    }

    @Override
    public Tag create(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public void delete(final Integer tagId) {
        entityManager.remove(read(tagId));
    }

    @Override
    public Optional<Tag> readTagByName(String tagName) {
        return entityManager.createQuery("select tag From Tag tag where name=:tagName")
                .setParameter("tagName", tagName)
                .getResultStream().findFirst();
    }

}
