package ar.com.ada.second.online.maven.model.dao;

import jdk.jfr.EventType;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class JpaUserDAO extends JPA implements DAO <UserDAO>{

    private static JpaUserDAO jpaUserDAO;

    private JpaUserDAO() {

    }

    public static JpaUserDAO getInstance() {
        if (jpaUserDAO == null) jpaUserDAO = new JpaUserDAO();
        return jpaUserDAO;
    }

    public void findByEmailOrNickname(String email, String nickname) throws Exception {
        openConnection();
        //SELECT * FROM user WHERE email=? OR nickname=?
        TypedQuery<UserDAO> query = entityManager.createQuery(
                "SELECT u FROM UserDAO u WHERE u.email=:email OR u.nickname=:nickname",
                UserDAO.class);

        query.setParameter("email", email);
        query.setParameter("nickname", nickname);

        Optional<UserDAO> byEmailAndNickname = query.getResultList().stream().findFirst();


        closeConnection();

        if (byEmailAndNickname.isPresent()) {
            throw new Exception("Ya existe un usuario con ese Email y Nickname");
        }
    }

    @Override
    public void save(UserDAO userDAO) {

        if (userDAO.getId() == null)
            executeInsideTransaction(entityManager1 -> entityManager.persist(userDAO));
            /*Consumer<EntityManager> persistUser = entityManager1 -> entityManager.persist(userDAO);
            executeInsideTransaction(persistUser);*/
        else
            executeInsideTransaction(entityManager1 -> entityManager.merge(userDAO));
            //merge actualiza, persist inserta



    }

    @Override
    public Integer getTotalRecords() {
        openConnection();

        Object singleResult = entityManager.createNativeQuery("SELECT COUNT (*) FROM User").getSingleResult();//"native" permite usar la query de MySql
        Integer count = singleResult != null ? Integer.parseInt(singleResult.toString()) : 0;

        closeConnection();
        return count;

    }

    @Override
    public Optional<UserDAO> findById(Integer id) {
        openConnection();

        UserDAO userDAO = entityManager.find(UserDAO.class, id);

        closeConnection();

        return Optional.ofNullable(userDAO);
    }

    @Override
    public Boolean delete(UserDAO dao) {
        openConnection();

        //Sincronización
        UserDAO userToDelete = entityManager.merge(dao);
        //borrado
        executeInsideTransaction(entityManager1 -> entityManager.remove(userToDelete));
        //entityManager.remove(userToDelete);

        closeConnection();

        Optional<UserDAO> verifyById = findById(dao.getId());


        return !verifyById.isPresent();
    }

    public List<UserDAO> findAll(Integer from, Integer limit) {
        openConnection();

        TypedQuery<UserDAO> query = entityManager.createQuery("SELECT COUNT u FROM UserDAO u", UserDAO.class);
        query.setFirstResult(from);
        query.setMaxResults(limit);
        List<UserDAO> list = query.getResultList();

        closeConnection();



        return list;
    }
}
