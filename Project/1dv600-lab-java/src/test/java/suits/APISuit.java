package suits;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import api.*;

@RunWith(Suite.class)
@SuiteClasses({ AddBookResourceTest.class, EditBookResourceTest.class, GetBookResourceTest.class,
        GetBooksResourceTest.class, RemoveBookResourceTest.class })
public class APISuit {
}