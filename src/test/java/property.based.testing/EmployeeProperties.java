package property.based.testing;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

@RunWith(JUnitQuickcheck.class)
public class EmployeeProperties {
    @Property
    public void ageIsLargerThanZero(){

    }
}
