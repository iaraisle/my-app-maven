package ar.com.ada.second.online.maven.model.dao;

import ar.com.ada.second.online.maven.model.dto.UserDTO;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class JpaPostDAO extends JPA implements DAO<PostDAO>{
    private static JpaPostDAO jpaPostDAO;

    private JpaPostDAO(){
    }

    public static JpaPostDAO getInstance() {
        if (jpaPostDAO == null) jpaPostDAO = new JpaPostDAO();
        return jpaPostDAO;
    }

    public Integer getTotalRecords(Integer userId) {
        openConnection();

        Object singleResult = (userId == null)
                ? entityManager.createNativeQuery("SELECT COUNT(*) FROM Post").getSingleResult()
                : entityManager.createNativeQuery("SELECT COUNT(*) FROM Post WHERE User_id=:userId")
                .setParameter("userId", userId)
                .getSingleResult();

        Integer count = singleResult != null ? Integer.parseInt(singleResult.toString()) : 0;

        closeConnection();
        return count;
    }

    @Override
    public void save(PostDAO dao) {
        if (dao.getId() == null)
            executeInsideTransaction(entityManager -> entityManager.persist(dao));
        else
            executeInsideTransaction(entityManager -> entityManager.merge(dao));
    }

    @Override
    public Integer getTotalRecords() {
        return null;
    }

    @Override
    public Optional<PostDAO> findById(Integer id) {
        openConnection();
        PostDAO postDAO = entityManager.find(PostDAO.class, id);
        closeConnection();

        return Optional.ofNullable(postDAO);
    }

    @Override
    public Boolean delete(PostDAO dao) {
        openConnection();
        // sincronizacion
        PostDAO postToDelete = entityManager.merge(dao);
        // borrado
        executeInsideTransaction(entityManager -> entityManager.remove(postToDelete));

        closeConnection();

        Optional<PostDAO> verifyById = findById(dao.getId());

        return !verifyById.isPresent();
    }

   @Override
   public List<PostDAO> findAll(Integer from, Integer limit) {
       openConnection();

       TypedQuery<PostDAO> query = entityManager.createQuery("SELECT p FROM PostDAO AS p", PostDAO.class);
       query.setFirstResult(from);
       query.setMaxResults(limit);
       List<PostDAO> list = query.getResultList();

       closeConnection();
       return list;
   }

    public List<PostDAO> findAllByUser(Integer from, Integer limit, UserDTO authorUser) {
        openConnection();

        TypedQuery<PostDAO> query = entityManager.createQuery("SELECT p FROM PostDAO AS p WHERE User_id=:userId", PostDAO.class);
        query.setFirstResult(from);
        query.setMaxResults(limit);
        query.setParameter("userId", authorUser.getId());
        List<PostDAO> list = query.getResultList();

        closeConnection();
        return list;
    }

    public Optional<PostDAO> findByIdAndAuthor(Integer id, UserDTO authorUser) {
        openConnection();
        String queryString = (authorUser != null)
                ? "SELECT p FROM PostDAO AS p WHERE id=:id AND User_id=:userId"
                : "SELECT p FROM PostDAO AS p WHERE id=:id";

        TypedQuery<PostDAO> query = entityManager
                .createQuery(queryString, PostDAO.class)
                .setParameter("id", id);

        if (authorUser != null)
            query.setParameter("userId", authorUser.getId());

        PostDAO singleResult = null;

        try {
            singleResult = query.getSingleResult();
        } catch (RuntimeException e) {

        }

        closeConnection();

        return Optional.ofNullable(singleResult);
    }
}
