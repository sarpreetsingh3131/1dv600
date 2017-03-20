package suits;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import useCases.*;

@RunWith(Suite.class)
@SuiteClasses({ AddABookTest.class, EditABookTest.class, ViewABookTest.class, ViewAListOfBooksTest.class,
        DeleteABookTest.class, SearchBooksTest.class })
public class UseCasesSuit {
}