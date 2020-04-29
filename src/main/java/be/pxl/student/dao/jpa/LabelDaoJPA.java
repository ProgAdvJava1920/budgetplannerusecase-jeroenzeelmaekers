package be.pxl.student.dao.jpa;

import be.pxl.student.dao.interfaces.ILabelDao;
import be.pxl.student.entity.Label;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class LabelDaoJPA implements ILabelDao {

    private EntityManager entityManager;

    public LabelDaoJPA(EntityManagerFactory entityManagerFactory) {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public List<Label> read() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        List<Label> labelList = entityManager.createQuery("SELECT l from Label l").getResultList();
        transaction.commit();
        return labelList;
    }

    @Override
    public boolean createLabel(Label newLabel) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Label label = new Label();
        label.setName(newLabel.getName());
        label.setDescription(newLabel.getDescription());
        entityManager.persist(label);
        transaction.commit();
        return true;
    }

    @Override
    public boolean updateLabel(Label updateLabel) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Label label = entityManager.find(Label.class, updateLabel.getId());
        label.setName(updateLabel.getName());
        label.setDescription(updateLabel.getDescription());
        entityManager.persist(label);
        transaction.commit();
        return true;
    }

    @Override
    public boolean deleteLabel(int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Label label = entityManager.find(Label.class, id);
        if(label != null) {
            entityManager.remove(label);
        }
        return true;
    }

    @Override
    public Label readLabel(int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Label label = entityManager.find(Label.class, id);
        transaction.commit();
        return label;
    }
}
