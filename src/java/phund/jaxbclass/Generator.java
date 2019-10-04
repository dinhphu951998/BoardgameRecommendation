/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.jaxbclass;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;
import org.eclipse.persistence.sessions.Session;
import phund.repository.AccountRepository;
import phund.repository.AccountRepositoryImp;
import phund.service.AccountService;
import phund.service.AccountServiceImp;
import phund.utils.JAXBUtils;
import phund.utils.JPAUtils;

/**
 *
 * @author PhuNDSE63159
 */
public class Generator {

    public static void main(String[] args) {
        try {
            String output = "src/java";
            String packageName = "phund.jaxbclass";
            String schemaFilePath = "web/WEB-INF/schema/boardgame.xsd";
            JAXBUtils.generateJavaClass(output, packageName, schemaFilePath);

//            Account acc = new Account("dinhphu", "123", "admin");
//            JAXBUtils.marshal("web/WEB-INF/schema/account.xml", acc, Account.class);
//            Account acc = (Account) JAXBUtils.unmarshal("web/WEB-INF/schema/account.xml", Account.class);
//            System.out.println(acc);
//            EntityManager em = JPAUtils.getEntityManager();
//            EntityManager em1 = JPAUtils.getEntityManager();
//            System.out.println("em: " + em);
//            System.out.println("em1: " + em1);
//            AccountService service = new AccountServiceImp();
//            AccountRepository repo = new AccountRepositoryImp();
//            Account acc = new Account("admin1", "12345", "admin");
//            repo.update(acc);
        } catch (Exception ex) {
            Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

        }
    }

}
