package suits;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import model.*;

@RunWith(Suite.class)
@SuiteClasses({ booksDAOTest.class, bookTest.class, catalogTest.class })
public class ModelSuit {
}