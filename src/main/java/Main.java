import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        StandardServiceRegistry registry =
                new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<Course> courseQuery = builder.createQuery(Course.class);
        Root<Course> courseRoot = courseQuery.from(Course.class);
        courseQuery.select(courseRoot);
        List<Course> courseList = session.createQuery(courseQuery).getResultList();

        CriteriaQuery<Student> studentQuery = builder.createQuery(Student.class);
        Root<Student> studentRoot = studentQuery.from(Student.class);
        studentQuery.select(studentRoot);
        List<Student> studentList = session.createQuery(studentQuery).getResultList();

        CriteriaQuery<PurchaseList> purchaseListQuery = builder.createQuery(PurchaseList.class);
        Root<PurchaseList> purchaseListRoot = purchaseListQuery.from(PurchaseList.class);
        purchaseListQuery.select(purchaseListRoot);
        List<PurchaseList> purchaseList = session.createQuery(purchaseListQuery).getResultList();

        for(PurchaseList purchase : purchaseList) {

            String courseName = purchase.getId().getCourseName();
            Integer courseId = null;
            for (Course course : courseList) {
                if (course.getName().equals(courseName)) {
                    courseId = course.getId();
                }
            }

            String studentName = purchase.getId().getStudentName();
            Integer studentId = null;
            for (Student student : studentList) {
                if (student.getName().equals(studentName)) {
                    studentId = student.getId();
                }
            }

            LinkedPurchaseListKey linkedPurchaseListKey = new LinkedPurchaseListKey(studentId, courseId);
            LinkedPurchaseList linkedPurchaseList = new LinkedPurchaseList();
            linkedPurchaseList.setId(linkedPurchaseListKey);

            session.save(linkedPurchaseList);
        }

        transaction.commit();
        sessionFactory.close();
    }
}